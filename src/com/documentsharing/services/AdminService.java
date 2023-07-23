package com.documentsharing.services;

import java.sql.ResultSet;
import java.util.ArrayList;

import com.documentsharing.bean.Admin;


public interface AdminService {
	Admin selectAdmin(String email, String password);
	boolean updateAdmin(String oldPassword, String newPassword,String email);
}
