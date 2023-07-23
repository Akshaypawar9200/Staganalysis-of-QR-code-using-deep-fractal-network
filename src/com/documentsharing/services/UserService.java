package com.documentsharing.services;

import java.io.InputStream;
import java.sql.ResultSet;
import java.util.ArrayList;

import com.documentsharing.bean.Follower;
import com.documentsharing.bean.Message;
import com.documentsharing.bean.User;


public interface UserService {
	boolean createUser(User user);
	boolean isAlreadyAvailable(User user);
	boolean updateUSer(String oldPassword,String newPassword,String email);
	User selectUser(String email, String Password);
	User selectUser(String email);
	ResultSet selectUser();
	boolean updateUser(int userId, String status);
	User selectUser(int userId);
	ArrayList<User> searchUser(String name, int userId);
	boolean sendRequest(Follower follower);
	ArrayList<Follower> selectFollower(int userId);
	boolean updateFollower(int userId, int followerId, String status, String newStatus);
	ArrayList<User> selectFriends(int userId);
	boolean postMsg(Message message);
	ResultSet selectMsgFriend(int userId);
	
	boolean postPubMsg(Message message, ArrayList<User> friendList);
	ResultSet selectPubMsgOwn(int userId);
	ResultSet selectPubMsgUserFriend(int userId);
	boolean postPrivMsg(Message message, ArrayList<User> friendList1);
	ResultSet selectPrivMsgOwn(int userId);
	ResultSet selectPrivMsgUserFriend(int userId);
	InputStream selectPubQR(int msgid, String msgType);

	
	
}
