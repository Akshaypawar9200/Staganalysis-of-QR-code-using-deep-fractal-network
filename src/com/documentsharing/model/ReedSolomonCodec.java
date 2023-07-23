package com.documentsharing.model;


import java.util.Random;

/**
 * Partial port of Phil Karn, KA9Q LGPL http://www.ka9q.net/code/fec/ (version fec-3.0.1)
 * Born out of frustration in finding a decent performance GF(2^8) Java implementation...
 * 
 * This port only works for symsize <= 8 bec we ported _char variant from PK's code
 *   And for speed improvement:
 *     1. alpha_to following modnn has been made into a table [~40% improvement in RS(255,191)]
 *     2. we are forcing fcr = 0, prim = 1  [additional ~10% improvement in RS(255,191)]
 *     3. some loop unrolling in decoding loop [additional ~10% in RS(255,191)]
 *           (total = approx 50% improvement than straight port)
 * 
 * Ported by Raymond Lau, Feb, 2015
 * 
 * The original was:
 * 
 *     Copyright 2002 Phil Karn, KA9Q
 *     May be used under the terms of the GNU Lesser General Public License (LGPL)
 *
 * This derivative work shall fall under the same license (LGPL as of 2002)
 * 
 * But please note that this code has only been minimally tested... and only tested on GF(256) with poly 0x11d
 *
 */
public class ReedSolomonCodec {

	// from rs-common.h
	final int mm; // bits per symbol
	final int nn; // symbols per block (= (1<<mm)-1)
	final int[] alpha_to; // log lookup table
	final int[] index_of; // antilog lookup table
	final int[] genpoly; // generator polynomial
	final int nroots; // number of generator roots = number of parity symbols
	
	// optimized supports only fcr=0,prim=1
	//final int fcr; // first consecutive root, index form
	//final int prim; // primitive element, index form
	
	final int iprim; // prim-th root of 1, index form
	final int[] to_modnn;
	final int[] alpha_to_after_modnn;

	// we don't support shortened blocks
	// int pad; // padding bytes in shortened block


	final int A0;

	// from init_rs.h
	public ReedSolomonCodec(int symsize, int genpoly,
				//int fcr,
				//int prim,
				int nroots) {
		int iprim;

		if(symsize > 8)
			throw new IllegalArgumentException("Only symsize <= 8 supported in this port");
		//if(fcr >= (1<<symsize) || fcr < 0)
		//	throw new IllegalArgumentException("Illegal fcr");
		//if(prim <= 0 || prim >= (1<<symsize))
		//	throw new IllegalArgumentException("Illegal prim");
		if(nroots >= (1<<symsize))
			throw new IllegalArgumentException("Can't have more roots than symbol values");
		this.mm = symsize;
		this.nn = (1<<symsize)-1;
		this.alpha_to = new int[this.nn+1];
		this.index_of = new int[this.nn+1];

		// Generate Galois field lookup tables
		this.A0 = this.nn;
		this.index_of[0] = this.A0;  // log(zero) = -inf
		this.alpha_to[A0] = 0;	// alpha**-inf = 0

		int sr = 1;
		for(int i=0;i<this.nn;i++) {
			this.index_of[sr] = i;
			this.alpha_to[i] = sr;
			sr <<= 1;
			if((sr & (1<<symsize)) != 0)
				sr ^= genpoly;
			sr &= this.nn;
		}

		if(sr != 1) {
			throw new IllegalArgumentException("field generator polynomial is not primitive!");
		}

		this.genpoly = new int[nroots+1];
		//this.fcr = fcr;
		//this.prim = prim;
		this.nroots = nroots;

		// Find prim-th root of 1, used in decoding
		//for(iprim=1;(iprim%prim) != 0;iprim += this.nn) {
		//}
		//this.iprim = iprim / prim;
		for(iprim=1;(iprim%1) != 0;iprim += this.nn) {
		}
		this.iprim = iprim / 1;

		// optimizations not from PK's code -- RAM is cheap. Make tables
		// .... max errors is more than upper bounded by nroots
		this.to_modnn = new int[this.nn*this.nroots];
		for(int i=0;i<this.to_modnn.length;i++)
			to_modnn[i] = this.calcmodnn(i);
		this.alpha_to_after_modnn = new int[this.nn*this.nroots];
		for(int i=0;i<this.alpha_to_after_modnn.length;i++)
			alpha_to_after_modnn[i] = this.alpha_to[this.to_modnn[i]];

		this.genpoly[0] = 1;
		//for(int i=0,root=fcr*prim;i<nroots;i++,root+=prim) {
		for(int i=0,root=0;i<nroots;i++,root++) {
			this.genpoly[i+1] = 1;

			//  Multiply rs->genpoly[] by  @**(root + x) 
			for(int j=i;j>0;j--) {
				if(this.genpoly[j] != 0) {
					this.genpoly[j] = this.genpoly[j-1] ^ this.alpha_to_after_modnn[this.index_of[this.genpoly[j]] + root];
				} else {
					this.genpoly[j] = this.genpoly[j-1];
				}
			}
			// rs->genpoly[0] can never be zero
			this.genpoly[0] = alpha_to_after_modnn[this.index_of[this.genpoly[0]] + root];
		}

		// convert rs->genpoly[] to index form for quicker encoding
		for(int i=0;i<=nroots;i++)
			this.genpoly[i] = this.index_of[this.genpoly[i]];
	}

	// from rs-common.h
	private int calcmodnn(int x) {
		while(x>=nn) {
			x -= nn;
			x = (x >> mm) + (x & nn);
		}
		return x;
	}

	int modnn(int x) {
		return this.to_modnn[x];
	}



	// from encode_rs.h
	public void encode(int[] data,int[] parity) {
		if(parity.length != this.nroots)
			throw new IllegalArgumentException("parity is wrong size");
		if(data.length != this.nn-this.nroots)
			throw new IllegalArgumentException("data is wrong size");


		for(int i=0;i<this.nroots;i++)
			parity[i] = 0;
		for(int i=0;i<this.nn-this.nroots;i++) {
			int feedback = this.index_of[data[i] ^ parity[0]];
			if(feedback != this.A0) { //  feedback term is non-zero
				for(int j=1;j<this.nroots;j++)
					parity[j] ^= alpha_to_after_modnn[feedback + this.genpoly[this.nroots-j]];
			}

			// shift
			System.arraycopy(parity, 1, parity, 0, this.nroots-1);
			if(feedback != this.A0)
				parity[this.nroots-1] = alpha_to_after_modnn[feedback + this.genpoly[0]];
			else
				parity[this.nroots-1] = 0;
		}
	}

	// variant where data is really <data><parity>
	public void encode(int[] data) {
		if(data.length != this.nn)
			throw new IllegalArgumentException("data is wrong size");
		int dataLen = this.nn-this.nroots;
		for(int i=0;i<this.nroots;i++)
			data[dataLen+i] = 0;
		for(int i=0;i<dataLen;i++) {
			int feedback = this.index_of[data[i] ^ data[dataLen]];
			if(feedback != this.A0) { //  feedback term is non-zero
				for(int j=1;j<this.nroots;j++)
					data[dataLen+j] ^= alpha_to_after_modnn[feedback + this.genpoly[this.nroots-j]];
			}

			// shift
			System.arraycopy(data, dataLen+1, data, dataLen, this.nroots-1);
			if(feedback != this.A0)
				data[dataLen+this.nroots-1] = alpha_to_after_modnn[feedback + this.genpoly[0]];
			else
				data[dataLen+this.nroots-1] = 0;
		}
	}

	public int decode(int[] data) {
		return decode(data,null);
	}
	
	// from decode_rs
	public int decode(int[] data,int[] eras_pos) {
		if(eras_pos == null)
			eras_pos = new int[0];
		if(data.length != this.nn)
			throw new IllegalArgumentException("data block wrong size");

		int i,j,r,k;
		int[] s = new int[this.nroots]; // syndrome poly
		int[] loc = new int[this.nroots];
		int syn_error,count;

		// form the syndromes; i.e., evaluate data(x) at roots of g(x)
		int tmpData = data[0];
		for(i=0;i<this.nroots;i++)
			s[i] = tmpData;

		for(j=1;j<this.nn;j++) {
			tmpData = data[j]; // incredibly enough, unrolling this is a 15% improvement in RS(255,191)!
			for(i=0;i<this.nroots;i++) {
				if(s[i] == 0)
					s[i] = tmpData;
				else {
					//s[i] = data[j] ^ this.alpha_to_after_modnn[this.index_of[s[i]] + (this.fcr+i)*this.prim];
					s[i] = tmpData ^ this.alpha_to_after_modnn[this.index_of[s[i]] + i];
				}
			}
		}

		// Convert syndromes to index form, checking for nonzero condition 
		syn_error = 0;
		for(i=0;i<this.nroots;i++) {
			syn_error |= s[i];
			s[i] = this.index_of[s[i]];
		}

		if(syn_error == 0) {
			// if syndrome is zero, data[] is a codeword and there are no
			// errors to correct. So return data[] unmodified
			count = 0;
			// goto finish
		} else {
			int deg_lambda, el, deg_omega;
			int u,q,tmp,num1,num2,den,discr_r;
			int[] lambda = new int[this.nroots+1]; // Err+Eras Locator poly
			int[] b = new int[this.nroots+1];
			int[] t = new int[this.nroots+1];
			int[] omega = new int[this.nroots+1];
			int[] root = new int[this.nroots];
			int[] reg = new int[this.nroots+1];
			while(true) {
				for(int ii=1;ii<=this.nroots;ii++)
					lambda[ii] = 0;
				lambda[0] = 1;

				if(eras_pos.length > 0) {
					// Init lambda to be the erasure locator polynomial
					//lambda[1] = this.alpha_to_after_modnn[this.prim*(this.nn-1-eras_pos[0])];
					lambda[1] = this.alpha_to_after_modnn[this.nn-1-eras_pos[0]];
					for(i=1;i<eras_pos.length;i++) {
						u = modnn(this.nn-1-eras_pos[i]);
						for(j=i+1;j>0;j--) {
							tmp = this.index_of[lambda[j-1]];
							if(tmp != this.A0)
								lambda[j] ^= this.alpha_to_after_modnn[u+tmp];
						}
					}
					// #if DEBUG >= 1 omitted
				}

				for(i=0;i<this.nroots+1;i++)
					b[i] = this.index_of[lambda[i]];

				// Begin Berlekamp-Massey algorithm to determine error+erasure
				// locator polynomial
				r = eras_pos.length;
				el = eras_pos.length;
				while(++r <= this.nroots) { // r is the step number
					// Compute discrepancy at the r-th step in poly-form
					discr_r = 0;
					for(i=0;i<r;i++) {
						if((lambda[i] != 0) && (s[r-i-1] != this.A0)) {
							discr_r ^= this.alpha_to_after_modnn[this.index_of[lambda[i]] + s[r-i-1]];
						}
					}
					discr_r = this.index_of[discr_r]; // Index form
					if(discr_r == this.A0) {
						// 2 lines below: B(x) <-- x*B(x)
						System.arraycopy(b, 0, b, 1, this.nroots-1);
						b[0] = this.A0;
					} else {
						// 7 lines below: T(x) <-- lambda(x) - discr_r*x*b(x)
						t[0] = lambda[0];
						for(i=0;i<this.nroots;i++) {
							if(b[i] != this.A0)
								t[i+1] = lambda[i+1] ^ this.alpha_to_after_modnn[discr_r + b[i]];
							else
								t[i+1] = lambda[i+1];
						}
						if(2 * el <= r + eras_pos.length - 1) {
							el = r + eras_pos.length - el;
							// 2 lines below: B(x) <-- inv(discr_r) * lambda(x)
							for(i=0;i<=this.nroots;i++)
								b[i] = (lambda[i] == 0) ? this.A0 : modnn(this.index_of[lambda[i]] - discr_r + this.nn);
						} else {
							// 2 lines below: B(x) <-- x*B(x)
							System.arraycopy(b, 0, b, 1, this.nroots-1);
							b[0] = this.A0;
						}
						for(int ii=0;ii<this.nroots+1;ii++)
							lambda[ii] = t[ii];
					}
				}

				// Convert lambda to index form and compute deg(lambda(x))
				deg_lambda = 0;
				for(i=0;i<this.nroots+1;i++) {
					lambda[i] = this.index_of[lambda[i]];
					if(lambda[i] != this.A0)
						deg_lambda = i;
				}
				// Find roots of the error+erasure locator polynomial by Chien search
				for(int ii=1;ii<=this.nroots;ii++)
					reg[ii] = lambda[ii];
				count = 0; // Number of roots of lambda(x)
				for(i=1,k=this.iprim-1;i<=this.nn;i++,k=modnn(k+this.iprim)) {
					q = 1; //  lambda[0] is always 0
					for(j=deg_lambda;j>0;j--) {
						if(reg[j] != this.A0) {
							reg[j] = modnn(reg[j] + j);
							q ^= this.alpha_to[reg[j]];
						}
					}
					if(q!=0)
						continue; // Not a root
					root[count] = i;
					loc[count] = k;
					// If we've already found max possible roots, abort the search to save time
					if(++count == deg_lambda)
						break;
				}
				if(deg_lambda != count) {
					// deg(lambda) unequal to number of roots => uncorrectable error detected
					count = -1;
					break; // goto finish
				}

				// Compute err+eras evaluator poly omega(x) = s(x)*lambda(x) (modulo
				// x**NROOTS). in index form. Also find deg(omega).
				deg_omega = 0;
				for(i=0;i<this.nroots;i++) {
					tmp = 0;
					j = (deg_lambda<i) ? deg_lambda : i;
					for(;j>=0;j--) {
						if((s[i-j] != this.A0) && (lambda[j] != this.A0))
							tmp ^= this.alpha_to_after_modnn[s[i-j] + lambda[j]];
					}
					if(tmp != 0)
						deg_omega = i;
					omega[i] = this.index_of[tmp];
				}
				omega[this.nroots] = this.A0;

				// Compute error values in poly-form. num1 = omega(inv(X(l))), num2 =
				// inv(X(l))**(FCR-1) and den = lambda_pr(inv(X(l))) all in poly-form
				for(j=count-1;j>=0;j--) {
					num1 = 0;
					for(i=deg_omega;i>=0;i--) {
						if(omega[i] != this.A0)
							num1 ^= this.alpha_to_after_modnn[omega[i]+i*root[j]];
					}
					//num2 = this.alpha_to_after_modnn[root[j] * (fcr-1) + this.nn];
					num2 = this.alpha_to_after_modnn[root[j] * -1 + this.nn];
					den = 0;

					// lambda[i+1] for i even is the formal derivative lambda_pr of lambda[i]
					for(i=Math.min(deg_lambda,this.nn-1) & ~1;i>=0;i-=2) {
						if(lambda[i+1] != this.A0)
							den ^= this.alpha_to_after_modnn[lambda[i+1] + i*root[j]];
					}
					if(den==0) {
						count--;
						break; // goto finish
					}
					//  Apply error to data
					if(num1 != 0) {
						data[loc[j]] ^= this.alpha_to_after_modnn[this.index_of[num1] + this.index_of[num2] + this.nn - this.index_of[den]];
					}
				}

				break;
			}
		}

		// finish:
		if(eras_pos.length != 0) {
			for(i=0;i<count;i++)
				eras_pos[i] = loc[i];
		}
		return count;
	}

	public static void main(String[] args) {
		int symsize = 8;
		int nroots = 64; // # parity 
		int nn = (1<<symsize) - 1;
		int kk = nn - nroots;
		ReedSolomonCodec rs = new ReedSolomonCodec(symsize,0x11d,nroots);

		int[] dataOrig = new int[255];
		int[] data = new int[255];
		Random r = new Random();
		for(int i=0;i<255-nroots;i++)
			dataOrig[i] = r.nextInt(256);

		int count = 0;
		long bytes = 0;
		long start = System.currentTimeMillis();
		while(bytes<1000000) {
			System.arraycopy(dataOrig,0,data,0,kk);
			rs.encode(data);

			data[r.nextInt(nn)] = r.nextInt(nn);
			data[r.nextInt(nn)] = r.nextInt(nn);
			data[r.nextInt(nn)] = r.nextInt(nn);
			
			count = rs.decode(data,null);

			for(int i=0;i<kk;i++)
				if(data[i] != dataOrig[i])
					System.out.println("Mismatch at position " + i);

			bytes += kk;
		}
		long elapsed = System.currentTimeMillis()-start;
		System.out.println("Detected errs in last block = " + count);
		System.out.println("Took " + elapsed + " for " + bytes);
		System.out.println("Done");
	}
}
