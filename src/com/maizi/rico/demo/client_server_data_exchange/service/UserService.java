package com.maizi.rico.demo.client_server_data_exchange.service;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import android.graphics.Bitmap;

import com.maizi.rico.demo.client_server_data_exchange.entity.Student;


public interface UserService {
	
	public void userLogin(String loginName, String loginPassword) throws Exception;
	
	public void userRegister(String loginName, List<String> interesting) throws Exception;
	
	public List<Student> getStudents() throws Exception;
	
	public Bitmap getImage() throws Exception;
	
	public String userUpload(InputStream in, Map<String, String> data) throws Exception;
	
	public void userDownload() throws Exception;
	
	

}
