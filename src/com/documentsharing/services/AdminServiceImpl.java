package com.documentsharing.services;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.documentsharing.bean.Admin;
import com.documentsharing.dao.AdminDao;
import com.documentsharing.dao.AdminDaoImpl;

public class AdminServiceImpl implements AdminService{
	
	AdminDao adminDao = new AdminDaoImpl();
	
	@Override
	public Admin selectAdmin(String email, String password) {
		// TODO Auto-generated method stub
		
		return adminDao.selectAdmin(email, password);
	}

	@Override
	public boolean updateAdmin(String oldPassword, String newPassword,String email) {
		// TODO Auto-generated method stub
		
		return adminDao.updateAdmin(oldPassword, newPassword, email);
	}
	
}
