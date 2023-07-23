package com.documentsharing.dao;

import java.io.InputStream;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;

import com.documentsharing.bean.Admin;
import com.documentsharing.bean.Follower;
import com.documentsharing.bean.Message;
import com.documentsharing.bean.User;
import com.documentsharing.connection.ConnectionUtils;

public class UserDaoImpl implements UserDao{
	
	@Override
	public boolean createUser(User user) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql = "Insert into tblUser values(?,?,?,?,?,?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setString(2, user.getFname());
			pstmt.setString(3, user.getLname());
			pstmt.setString(4, user.getEmail());
			pstmt.setString(5, user.getPassword());
			pstmt.setDate(6, new Date(user.getDob().getTime()));
			pstmt.setString(7, user.getGender());
			pstmt.setString(8, user.getAddress());
			pstmt.setString(9, user.getContact());
			pstmt.setBlob(10, user.getProfilePic());
			pstmt.setString(11, user.getProfileName());
			pstmt.setString(12, user.getStatus());
			int index = pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public boolean isAlreadyAvailable(User user) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql = "Select * from tblUser where emailid ='"+user.getEmail()+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public User selectUser(String email, String password) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "Select * from tblUser where emailid ='"+email.toLowerCase()+"' and password='"+password+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setProfilePic(rs.getBinaryStream(10));
				user.setProfileName(rs.getString(11));
				user.setStatus(rs.getString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public User selectUser(String email) {
		// TODO Auto-generated method stub
		User user = new User();
		String sql = "Select * from tblUser where emailid ='"+email.toLowerCase()+"'";
		try {
			Statement stmt = ConnectionUtils.getConnection().createStatement();
			ResultSet rs = stmt.executeQuery(sql);
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setProfilePic(rs.getBinaryStream(10));
				user.setProfileName(rs.getString(11));
				user.setStatus(rs.getString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return user;
	}

	@Override
	public boolean updateUser(String oldPassword, String newPassword, String email) {
		// TODO Auto-generated method stub
		boolean flag=false;
		
		String sql = "Update tblUser set password=? where password=? and emailid=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, newPassword);
			pstmt.setString(2, oldPassword);
			pstmt.setString(3, email);
			int index = pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ResultSet selectUser() {
		// TODO Auto-generated method stub
		ResultSet rs= null;
		String sql ="Select * from tblUser";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
		
	}

	@Override
	public boolean updateUser(int userId, String status) {
		// TODO Auto-generated method stub
		boolean flag = false;
		if(status.equalsIgnoreCase("Inactive"))
			status="Active";
		else
			status = "Inactive";
		
		String sql = "Update tblUser Set status=? Where userid=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, status);
			pstmt.setInt(2, userId);
			int index=pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public User selectUser(int userId) {
		// TODO Auto-generated method stub
		String sql ="Select * from tblUser Where userid=?";
		User user = new User();
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setPassword(rs.getString(5));
				user.setDob(rs.getDate(6));
				user.setGender(rs.getString(7));
				user.setAddress(rs.getString(8));
				user.setContact(rs.getString(9));
				user.setProfilePic(rs.getBinaryStream(10));
				user.setProfileName(rs.getString(11));
				user.setStatus(rs.getString(12));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return user;
	}

	@Override
	public ArrayList<User> searchUser(String name, int userId) {
		// TODO Auto-generated method stub		
		ArrayList<User> listUser = new ArrayList<User>();
		String sql="Select userid, firstname, lastname, profilepic, address From tblUser Where userid <> ? AND firstname like ? or lastname like ?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setString(2, name +"%");
			pstmt.setString(3, name +"%");
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setProfilePic(rs.getBinaryStream(4));
				user.setAddress(rs.getString(5));
				listUser.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listUser;
	}

	@Override
	public boolean sendRequest(Follower follower) {
		// TODO Auto-generated method stub
		boolean flag = false;
		String sql = "Insert Into tblFollower Values(?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, follower.getSender().getUserId());
			pstmt.setInt(3, follower.getReceiver().getUserId());
			pstmt.setString(4, follower.getStatus());
			pstmt.setTimestamp(5, new Timestamp(follower.getRequestDate().getTime()));
			pstmt.setDate(6, new Date(follower.getResponseDate().getTime()));
			int index = pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public ArrayList<Follower> selectFollower(int userId) {
		// TODO Auto-generated method stub
		ArrayList<Follower> listFollower = new ArrayList<Follower>();
		String sql="SELECT t.userid, t.firstname,t.lastname, t.emailid, t.dob, t.address, t.contact, t.profilepic, u.userid,"
				+" f.status,f.requestdate FROM tbluser t Inner Join tblfollower f on f.userid=t.userid "
				+" Inner Join tbluser u on u.userid=f.followerid Where u.userid=? and f.status='Waiting'";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				User user1 = new User();
				user1.setUserId(userId);
				
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDob(rs.getDate(5));
				user.setAddress(rs.getString(6));
				user.setContact(rs.getString(7));
				user.setProfilePic(rs.getBinaryStream(8));
				
				Follower follower = new Follower();
				follower.setSender(user1);
				follower.setReceiver(user);
				follower.setStatus(rs.getString(10));
				follower.setRequestDate(rs.getTimestamp(11));
				
				listFollower.add(follower);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listFollower;
	}

	@Override
	public boolean updateFollower(int userId, int followerId, String status,String newStatus) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql="Update tblFollower Set status=? Where userid=? AND followerid=? And status=?";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setString(1, newStatus);
			pstmt.setInt(2, followerId);
			pstmt.setInt(3, userId);
			pstmt.setString(4, status);
			int index = pstmt.executeUpdate();
			if(index>0){
				flag = true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return flag;
	}

	@Override
	public ArrayList<User> selectFriends(int userId) {
		// TODO Auto-generated method stub
		ArrayList<User> friendList = new ArrayList<User>();
		/*String sql = "SELECT u.userid,u.firstname,u.lastname,u.emailid,u.dob,u.contact,u.address,u.profilepic "
				+ " FROM tblfollower f INNER JOIN tbluser u ON f.followerid=u.userid WHERE f.userid=? OR f.followerid=?";*/
		String sql = "SELECT u.userid,u.firstname,u.lastname,u.emailid,u.dob,u.contact,u.address,u.profilepic "
				+ " FROM ( SELECT userid FROM tblfollower WHERE followerid=? and status='Accepted'"
		        + " UNION SELECT followerid FROM tblfollower WHERE userid=? and status='Accepted'"
		        + " ) f INNER JOIN tbluser u USING (userid)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			pstmt.setInt(2, userId);
			ResultSet rs = pstmt.executeQuery();
			while(rs.next()){
				User user = new User();
				user.setUserId(rs.getInt(1));
				user.setFname(rs.getString(2));
				user.setLname(rs.getString(3));
				user.setEmail(rs.getString(4));
				user.setDob(rs.getDate(5));
				user.setContact(rs.getString(6));
				user.setAddress(rs.getString(7));
				user.setProfilePic(rs.getBinaryStream(8));
				friendList.add(user);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return friendList;
	}


	@Override
	public boolean postMsg(Message message) {
		// TODO Auto-generated method stub
		boolean flag=false;
		String sql="Insert Into tblMessage Values(?,?,?,?,?,?,?)";
		try {
			PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
			/*pstmt.setInt(1, 0);
			pstmt.setString(2, message.getMsg());
			pstmt.setBinaryStream(3, message.getUploadImg());
			pstmt.setString(4, message.getUploadName());
			pstmt.setTimestamp(5, (Timestamp)message.getMsgDate());
			pstmt.setInt(6, message.getSender().getUserId());
			pstmt.setString(7, message.getType());*/
			int index=pstmt.executeUpdate();
			if(index>0){
				flag=true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return flag;
	}

	@Override
	public ResultSet selectMsgFriend(int userId) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		String sql = "SELECT t.*,t1.firstname,t1.lastname FROM tblmessage t Inner Join "
				+" tbluser t1 on t1.userid=t.senderid;";
		
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rs;
	}



	@Override
	public boolean postPubMsg(Message message, ArrayList<User> friendList) {
		// TODO Auto-generated method stub
		boolean flag =false;
		boolean f1=false, f2=false;
		String sql = "Insert into tblMessage Values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, message.getSender().getUserId());
			pstmt.setString(3, message.getPubMsg());
			pstmt.setBlob(4, message.getQR());
			pstmt.setString(5, message.getQR_path());
			pstmt.setString(6, message.getPrivMsg());
			pstmt.setString(7, message.getMsg_type());
			pstmt.setTimestamp(8, new Timestamp(message.getMsgDate().getTime()));
			pstmt.setBlob(9, message.getPubFile());
			pstmt.setString(10, message.getPubFilePath());
			pstmt.setString(11, message.getPubLongUrl());
			pstmt.setString(12, message.getPubShortUrl());
			pstmt.setInt(13, message.getTotalParts());
			pstmt.setInt(14, message.getRequiredParts());
			pstmt.setBlob(15, message.getSecret1());
			pstmt.setString(16, message.getSecretPath1());
			pstmt.setBlob(17, message.getSecret2());
			pstmt.setString(18, message.getSecretPath2());
			pstmt.setBlob(19, message.getSecret3());
			pstmt.setString(20, message.getSecretPath3());
			pstmt.setBlob(21, message.getSecret4());
			pstmt.setString(22, message.getSecretPath4());
			pstmt.setBlob(23, message.getSecret5());
			pstmt.setString(24, message.getSecretPath5());
			pstmt.setBlob(25, message.getSecret6());
			pstmt.setString(26, message.getSecretPath6());
			pstmt.setBlob(27, message.getSecret7());
			pstmt.setString(28, message.getSecretPath7());
			pstmt.setBlob(29, message.getSecret8());
			pstmt.setString(30, message.getSecretPath8());
			pstmt.setBlob(31, message.getSecret9());
			pstmt.setString(32, message.getSecretPath9());
			pstmt.setBlob(33, message.getSecret10());
			pstmt.setString(34, message.getSecretPath10());
			int index=pstmt.executeUpdate();
			if(index>0){
				f1=true;
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql ="Select max(messageid) from tblMessage";
		int msgId =0;
		try {
			PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				msgId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		if(friendList!=null){
			for(int i=0;i<friendList.size();i++){
				String sql1="Insert Into tblUserFriend values(?,?,?,?)";
				try {
					PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql1);
					pstmt.setInt(1, 0);
					pstmt.setInt(2, msgId);
					pstmt.setInt(3, message.getSender().getUserId());
					pstmt.setInt(4, friendList.get(i).getUserId());
					int index = pstmt.executeUpdate();
					if(index>0){
						count++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(count==friendList.size()){
			f2=true;
		}
		if(f1&&f2){
			flag=true;
		}
		return flag;
	}

	@Override
	public ResultSet selectPubMsgOwn(int userId) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		String sql = "SELECT t.*,t1.firstname,t1.lastname FROM tblmessage t Inner Join "
				 +" tbluser t1 on t1.userid=t.senderid Inner Join tbluserfriend f "
				+" on t.messageid=f.messageid where t.senderid=? and t.msgtype='Public' group by t.messageid order by t.msgdate desc";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ResultSet selectPubMsgUserFriend(int userId) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		String sql = "SELECT t.*,t1.firstname,t1.lastname FROM tblmessage t Inner Join "
				 +" tbluser t1 on t1.userid=t.senderid Inner Join tbluserfriend f "
				+" on t.messageid=f.messageid where f.receiverid=? and t.msgtype='Public' order by t.msgdate desc";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public boolean postPrivMsg(Message message, ArrayList<User> friendList) {
		// TODO Auto-generated method stub
		boolean flag =false;
		boolean f1=false, f2=false;
		String sql = "Insert into tblMessage Values(?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?,?, ?,?,?,?)";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, 0);
			pstmt.setInt(2, message.getSender().getUserId());
			pstmt.setString(3, message.getPubMsg());
			pstmt.setBlob(4, message.getQR());
			pstmt.setString(5, message.getQR_path());
			pstmt.setString(6, message.getPrivMsg());
			pstmt.setString(7, message.getMsg_type());
			pstmt.setTimestamp(8, new Timestamp(message.getMsgDate().getTime()));
			pstmt.setBlob(9, message.getPubFile());
			pstmt.setString(10, message.getPubFilePath());
			pstmt.setString(11, message.getPubLongUrl());
			pstmt.setString(12, message.getPubShortUrl());
			pstmt.setInt(13, message.getTotalParts());
			pstmt.setInt(14, message.getRequiredParts());
			pstmt.setBlob(15, message.getSecret1());
			pstmt.setString(16, message.getSecretPath1());
			pstmt.setBlob(17, message.getSecret2());
			pstmt.setString(18, message.getSecretPath2());
			pstmt.setBlob(19, message.getSecret3());
			pstmt.setString(20, message.getSecretPath3());
			pstmt.setBlob(21, message.getSecret4());
			pstmt.setString(22, message.getSecretPath4());
			pstmt.setBlob(23, message.getSecret5());
			pstmt.setString(24, message.getSecretPath5());
			pstmt.setBlob(25, message.getSecret6());
			pstmt.setString(26, message.getSecretPath6());
			pstmt.setBlob(27, message.getSecret7());
			pstmt.setString(28, message.getSecretPath7());
			pstmt.setBlob(29, message.getSecret8());
			pstmt.setString(30, message.getSecretPath8());
			pstmt.setBlob(31, message.getSecret9());
			pstmt.setString(32, message.getSecretPath9());
			pstmt.setBlob(33, message.getSecret10());
			pstmt.setString(34, message.getSecretPath10());
			int index=pstmt.executeUpdate();
			if(index>0){
				f1=true;
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		sql ="Select max(messageid) from tblMessage";
		int msgId =0;
		try {
			PreparedStatement pstmt=ConnectionUtils.getConnection().prepareStatement(sql);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				msgId = rs.getInt(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int count=0;
		if(friendList!=null){
			for(int i=0;i<friendList.size();i++){
				String sql1="Insert Into tblUserFriend values(?,?,?,?)";
				try {
					PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql1);
					pstmt.setInt(1, 0);
					pstmt.setInt(2, msgId);
					pstmt.setInt(3, message.getSender().getUserId());
					pstmt.setInt(4, friendList.get(i).getUserId());
					int index = pstmt.executeUpdate();
					if(index>0){
						count++;
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		if(count==friendList.size()){
			f2=true;
		}
		if(f1&&f2){
			flag=true;
		}
		return flag;
	}

	@Override
	public ResultSet selectPrivMsgOwn(int userId) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		String sql = "SELECT t.*,t1.firstname,t1.lastname FROM tblmessage t Inner Join "
				 +" tbluser t1 on t1.userid=t.senderid Inner Join tbluserfriend f "
				+" on t.messageid=f.messageid where t.senderid=? and t.msgtype='Private' group by t.messageid order by t.msgdate desc";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public ResultSet selectPrivMsgUserFriend(int userId) {
		// TODO Auto-generated method stub
		ResultSet rs=null;
		String sql = "SELECT t.*,t1.firstname,t1.lastname FROM tblmessage t Inner Join "
				 +" tbluser t1 on t1.userid=t.senderid Inner Join tbluserfriend f "
				+" on t.messageid=f.messageid where f.receiverid=? and t.msgtype='Private' order by t.msgdate desc";
		try {
			PreparedStatement pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, userId);
			rs = pstmt.executeQuery();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public InputStream selectPubQR(int msgid, String msgType) {
		// TODO Auto-generated method stub
		InputStream isQR = null;
		String sql = "SELECT QR FROM tblmessage where messageid=? and msgtype=?";
		PreparedStatement pstmt;
		try {
			pstmt = ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, msgid);
			pstmt.setString(2, msgType);
			ResultSet rs = pstmt.executeQuery();
			if(rs.next()){
				 isQR = rs.getBinaryStream(1);
			}
			pstmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return isQR;
	}

	@Override
	public InputStream selectSecret(int privId, String str) {
		// TODO Auto-generated method stub
		InputStream is=null;
		
		String sql="Select "+str+" From tblMessage where messageid=?";
		try {
			PreparedStatement pstmt =ConnectionUtils.getConnection().prepareStatement(sql);
			pstmt.setInt(1, privId);
			ResultSet rs =pstmt.executeQuery();
			if(rs.next()){
				is=rs.getBinaryStream(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return is;
	}

}
