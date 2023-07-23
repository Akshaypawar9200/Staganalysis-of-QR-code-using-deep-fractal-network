package com.documentsharing.services;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.documentsharing.bean.Follower;
import com.documentsharing.bean.Message;
import com.documentsharing.bean.User;
import com.documentsharing.dao.UserDao;
import com.documentsharing.dao.UserDaoImpl;

public class UserServiceImpl implements UserService {
	UserDao userDao =new UserDaoImpl();
	@Override
	public boolean createUser(User user) {
		// TODO Auto-generated method stub		
		return userDao.createUser(user);		
	}

	@Override
	public boolean isAlreadyAvailable(User user) {
		// TODO Auto-generated method stub	
		return userDao.isAlreadyAvailable(user);
	}

	@Override
	public User selectUser(String email, String password) {
		// TODO Auto-generated method stub
		
		return userDao.selectUser(email, password);
	}

	@Override
	public User selectUser(String email) {
		// TODO Auto-generated method stub
		
		return userDao.selectUser(email);
	}

	@Override
	public boolean updateUSer(String oldPassword, String newPassword,
			String email) {
		// TODO Auto-generated method stub
		
		return userDao.updateUser(oldPassword,newPassword,email);
	}

	@Override
	public ResultSet selectUser() {
		// TODO Auto-generated method stub
		return userDao.selectUser();
	}

	@Override
	public boolean updateUser(int userId, String status) {
		// TODO Auto-generated method stub
		return userDao.updateUser(userId, status);
	}

	@Override
	public User selectUser(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectUser(userId);
	}

	@Override
	public ArrayList<User> searchUser(String name, int userId) {
		// TODO Auto-generated method stub
		return userDao.searchUser(name,userId);
	}

	@Override
	public boolean sendRequest(Follower follower) {
		// TODO Auto-generated method stub
		return userDao.sendRequest(follower);
	}

	@Override
	public ArrayList<Follower> selectFollower(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectFollower(userId);
	}

	@Override
	public boolean updateFollower(int userId, int followerId, String status, String newStatus) {
		// TODO Auto-generated method stub
		return userDao.updateFollower(userId,followerId,status,newStatus);
	}

	@Override
	public ArrayList<User> selectFriends(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectFriends(userId);
	}

	@Override
	public boolean postMsg(Message message) {
		// TODO Auto-generated method stub
		return userDao.postMsg(message);
	}

	@Override
	public ResultSet selectMsgFriend(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectMsgFriend(userId);
	}


	@Override
	public boolean postPubMsg(Message message, ArrayList<User> friendList) {
		// TODO Auto-generated method stub
		return userDao.postPubMsg(message, friendList);
	}

	@Override
	public ResultSet selectPubMsgOwn(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectPubMsgOwn(userId);
	}

	@Override
	public ResultSet selectPubMsgUserFriend(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectPubMsgUserFriend(userId);
	}

	@Override
	public boolean postPrivMsg(Message message, ArrayList<User> friendList1) {
		// TODO Auto-generated method stub
		return userDao.postPrivMsg(message, friendList1);
	}

	@Override
	public ResultSet selectPrivMsgOwn(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectPrivMsgOwn(userId);
	}

	@Override
	public ResultSet selectPrivMsgUserFriend(int userId) {
		// TODO Auto-generated method stub
		return userDao.selectPrivMsgUserFriend(userId);
	}

	@Override
	public InputStream selectPubQR(int msgid, String msgType) {
		// TODO Auto-generated method stub
		return userDao.selectPubQR(msgid,msgType);
	}

}
