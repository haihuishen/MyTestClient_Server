package com.shen.client.service;

import android.graphics.Bitmap;

import com.shen.client.entity.Student;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * interface UserService
 */
public interface UserService {

    /**
     * 用户登录
     * @param loginName             登录名
     * @param loginPassword         登录密码
     * @throws Exception
     */
	public void userLogin(String loginName, String loginPassword) throws Exception;

    /**
     * 用户注册
     * @param loginName             登录名(注册名)
     * @param interesting           用户兴趣(用户的信息List)
     * @throws Exception
     */
	public void userRegister(String loginName, List<String> interesting) throws Exception;

    /**
     * 获得学生(用户)列表
     * @return
     * @throws Exception
     */
	public List<Student> getStudents() throws Exception;

    /**
     * 获取图片
     * @return
     * @throws Exception
     */
	public Bitmap getImage() throws Exception;

    /**
     * 上传数据
     * @param in        要上传的数据(复杂数据)
     * @param data      要上传的数据(简单数据-String) Map---键==>参数   值==>参数值
     * @return
     * @throws Exception
     */
	public String userUpload(InputStream in, Map<String, String> data) throws Exception;

    /**
     * 下载数据
     * @throws Exception
     */
	public void userDownload() throws Exception;

}
