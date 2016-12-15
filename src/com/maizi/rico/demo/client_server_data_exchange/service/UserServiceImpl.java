package com.maizi.rico.demo.client_server_data_exchange.service;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.maizi.rico.demo.client_server_data_exchange.LoginActivity;
import com.maizi.rico.demo.client_server_data_exchange.RegisterActivity;
import com.maizi.rico.demo.client_server_data_exchange.entity.Student;

public class UserServiceImpl implements UserService {

	private static final String TAG = "UserServiceImpl";

	@Override
	public void userLogin(String loginName, String loginPassword)
			throws Exception {

		Log.d(TAG, loginName);
		Log.d(TAG, loginPassword);

		// // 创建HttpClient对象
		// HttpClient client = new DefaultHttpClient();
		// /**
		// * uri : URL 地址 http://localhost:8080/maizi/login.do
		// *
		// * GET 传值（传参） 实质： URL传参 ?参数名=参数值&参数名=参数值.......
		// *
		// * IP地址
		// * 真机和Servlet在同一一个局域网中
		// *
		// * Servlet ： 192.168.1.104
		// * 真机wifi ： 192.168.1.100
		// */
		// String uri = "http://192.168.1.104:8080/maizi/login.do?LoginName="
		// + loginName + "&LoginPassword=" + loginPassword;
		// HttpGet get = new HttpGet(uri);
		//
		// // 响应
		// HttpResponse response = client.execute(get);

		/**
		 * NameValuePair ---> List<NameValuePair> ---> HttpEntity ---> HttpPost
		 * ---> HttpClient
		 */
		HttpParams params = new BasicHttpParams();
		// 通过params设置请求的字符集
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		// 设置客户端和服务器连接的超时时间 －－－》 ConnectionTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// 设置服务器响应的超时时间 －－－》 SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);

		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory
				.getSocketFactory(), 433));
		ClientConnectionManager conman = new ThreadSafeClientConnManager(
				params, schreg);

		HttpClient client = new DefaultHttpClient(conman, params);
		String uri = "http://192.168.1.5:8080/maizi/login.do";
		HttpPost post = new HttpPost(uri);
		NameValuePair paramloginName = new BasicNameValuePair("LoginName",
				loginName);
		NameValuePair parmaLoginPassword = new BasicNameValuePair(
				"LoginPassword", loginPassword);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(paramloginName);
		parameters.add(parmaLoginPassword);
		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));
		HttpResponse response = client.execute(post);

		/**
		 * 200 404 500 406
		 */
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		if (result.equals("success")) {

		} else {
			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_FAILED);
		}

		// HttpClient c = CustomerHttpClient.getHttpClient();

	}

	@Override
	public void userRegister(String loginName, List<String> interesting)
			throws Exception {

		// Thread.sleep(3000);

		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.118:8080/maizi/register.do";
		HttpPost post = new HttpPost(uri);

		/**
		 * JSON数据的封装 **********************************************
		 */
		JSONObject object = new JSONObject();
		object.put("LoginName", loginName);
		JSONArray array = new JSONArray();
		if (interesting != null) {
			for (String string : interesting) {
				array.put(string);
			}
		}
		object.put("Interesting", array);
		/******************************************************/

		NameValuePair parameter = new BasicNameValuePair("Data",
				object.toString());
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

		post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
		}

		/**
		 * 从响应中取得服务器的返回结果
		 */
		String results = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		// 解析JSON

		JSONObject jsonResults = new JSONObject(results);

		String result = jsonResults.getString("result");

		if (result.equals("success")) {
			// 注册success
		} else {
			// 注册failed
			String errorMsg = jsonResults.getString("errorMsg");
			throw new ServiceRulesException(errorMsg);
		}

	}

	@Override
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<Student>();

		HttpClient client = new DefaultHttpClient();
		String uri = "http://192.168.1.118:8080/maizi/getStudents.do";
		HttpGet get = new HttpGet(uri);

		HttpResponse response = client.execute(get);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
		}

		String results = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		/*
		 * 解析JSON数据（JSONArray）
		 */
		JSONArray array = new JSONArray(results);

		for (int i = 0; i < array.length(); i++) {
			JSONObject jsonStudent = array.getJSONObject(i);
			Long id = Long.parseLong(jsonStudent.getString("id"));
			String name = jsonStudent.getString("name");
			int age = jsonStudent.getInt("age");

			students.add(new Student(id, name, age));
		}

		return students;
	}

	/**
	 * 构造POST参数传递
	 * 
	 * @param params
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static StringBuffer setPostPassParams(Map<String, String> params)
			throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer();
		/**
		 * k1=v1&k2=v2....
		 */
		for (Map.Entry<String, String> entry : params.entrySet()) {
			stringBuffer.append(entry.getKey()).append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
					.append("&");
		}

		// 把最后&去掉
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		return stringBuffer;
	}

	@Override
	public Bitmap getImage() throws Exception {
		Bitmap bitmap = null;
		InputStream in = null;
		URL url = null;
		HttpURLConnection urlConnection = null;
		OutputStream out = null;
		byte[] data = null;
		try {
			Map<String, String> params = new HashMap<String, String>();
			params.put("id", "1");
			// 封装参数
			data = setPostPassParams(params).toString().getBytes();

			/*** post ***/
			// url = new URL("http://192.168.1.118:8080/maizi/getImage.jpeg");
			// url = new URL("http://192.168.1.3:8080/maizi/getImage.jpeg");
			// urlConnection = (HttpURLConnection) url.openConnection();
			// // 设置请求的超时时间
			// urlConnection.setConnectTimeout(3000);
			// // 设置相应的超时时间
			// urlConnection.setReadTimeout(3000);
			// // 读数据
			// urlConnection.setDoInput(true);
			// // 写数据
			// urlConnection.setDoOutput(true);
			// // 设置请求方法是POST请求
			// urlConnection.setRequestMethod("POST");
			// // 取消缓存
			// urlConnection.setUseCaches(false);
			//
			// /**
			// * HTTP协议
			// */
			// // urlConnection.setRequestProperty("Content-Type", newValue);
			// // urlConnection.setRequestProperty("Content-Length", newValue);
			//
			// urlConnection.connect();
			// // 输出流，客户端向服务器发送数据
			// out = urlConnection.getOutputStream();
			// // 向服务器写数据的关键代码
			// out.write(data);
			// // 刷新
			// out.flush();
			//
			// // 响应状态码
			// int responseCode = urlConnection.getResponseCode();
			// if (responseCode != HttpURLConnection.HTTP_OK) {
			// throw new ServiceRulesException("请求服务器出错。");
			// }
			//
			// in = new BufferedInputStream(urlConnection.getInputStream());
			//
			// Log.d("TAG", "文件大小：" + in.available());
			// if (in != null) {
			// // 转换类型 InputStream -- > Bitmap
			// /**
			// * InputStream Bitmap Drawable byte[]
			// */
			// bitmap = BitmapFactory.decodeStream(in);
			// }

			/*** get ****/
			url = new
			 URL("http://192.168.1.118:8080/maizi/getImage.jpeg?id=2");
//			URL("http://192.168.1.3:8080/maizi/getImage.jpeg?id=2");
			urlConnection = (HttpURLConnection) url.openConnection();
			// 设置可以读取,读取服务器传给我们的图片流
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			// 连接
			urlConnection.connect();
			// 响应状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new ServiceRulesException("请求服务器出错。");
			}
			
			int length = urlConnection.getContentLength();

			in = urlConnection.getInputStream();
			
			SAXParserFactory
			
			
			Log.d("TAG", "文件大小：" + length);
			if (in != null) {
				// 转换类型 InputStream -- > Bitmap
				/**
				 * InputStream Bitmap Drawable byte[]
				 */
				bitmap = BitmapFactory.decodeStream(in);
			}
		} finally {
			if (in != null) {
				in.close();
			}
			// 断开连接
			if (urlConnection != null) {
				urlConnection.disconnect();
			}

		}

		return bitmap;
	}

	@Override
	public String userUpload(InputStream in, Map<String, String> data)
			throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(
				"http://192.168.1.118:8080/maizi/upload.do");

		// 要把数据封装到post里面去
		/**
		 * Httpmine v 4.2.3 jar v 4.3.3 jar --> httpcore v4.3.2 MultipartEntity
		 * ----------------------------------- 以上两个版本的写法也是不一样
		 * 
		 */

		MultipartEntity entity = new MultipartEntity();
		// “普通”字符串数据封装
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			entity.addPart(key, new StringBody(value, Charset.forName("UTF-8")));
		}
		// “二进制的流文件”数据封装
		entity.addPart("file", new InputStreamBody(in, "multipart/form-data",
				"test.jpg"));

		// 继续加....

		// 数据放到post
		post.setEntity(entity);

		HttpResponse response = client.execute(post);

		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
		}

		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		return result;
	}

	@Override
	public void userDownload() throws Exception {
		InputStream in = null;
		OutputStream out = null;
		URL url = null;
		HttpURLConnection urlConnection = null;

		try {
			url = new URL("http://192.168.1.118:8080/maizi/download.do");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(20000);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new ServiceRulesException("Server Error");
			}

			/**
			 * 
			 */
			// HttpClient client = null;
			// HttpResponse response =null;
			// response.getEntity().getContent();

			in = new BufferedInputStream(urlConnection.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(new File(
					Environment.getExternalStorageDirectory()
							+ "/Struts in Action.pdf")));

			byte[] buffer = new byte[20480];
			int read = 0;
			while ((read = in.read(buffer)) != -1) {
				out.write(buffer, 0, read);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				in.close();
			}

			if (out != null) {
				out.close();
			}
		}

	}

}
