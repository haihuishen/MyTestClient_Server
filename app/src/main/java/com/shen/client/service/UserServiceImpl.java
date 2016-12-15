package com.shen.client.service;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.shen.client.entity.Student;
import com.shen.client.ui.LoginActivity;
import com.shen.client.ui.RegisterActivity;

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

public class UserServiceImpl implements UserService {

	private static final String TAG = "UserServiceImpl";

    private static final String IP = "http://192.168.23.1:8080/MyTestServer";


    /***************************************** 登录 ***********************************************/
	@Override
	public void userLogin(String loginName, String loginPassword)
			throws Exception {

		Log.d(TAG, loginName);
		Log.d(TAG, loginPassword);

         // 创建HttpClient对象
         //HttpClient client = new DefaultHttpClient();
         /**
         * uri : URL 地址 http://localhost:8080/MyTestServer/login.do
         * GET 传值（传参） 实质： URL传参 ?参数名=参数值&参数名=参数值.......
         */
        // String uri = "http://192.168.1.104:8080/MyTestServer/login.do?LoginName=" + loginName + "&LoginPassword=" + loginPassword;
        // HttpGet get = new HttpGet(uri);
        // HttpResponse response = client.execute(get);     // 响应

		/**
		 * NameValuePair ---> List<NameValuePair> ---> HttpEntity ---> HttpPost ---> HttpClient
		 */
		HttpParams params = new BasicHttpParams();
		// 通过params设置请求的字符集
		HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);
		// 设置客户端和服务器连接的超时时间 －－－》 ConnectionTimeoutException
		HttpConnectionParams.setConnectionTimeout(params, 3000);
		// 设置服务器响应的超时时间 －－－》 SocketTimeoutException
		HttpConnectionParams.setSoTimeout(params, 3000);

        // 通过 80 或 433 端口也可以访问
		SchemeRegistry schreg = new SchemeRegistry();
		schreg.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		schreg.register(new Scheme("https", PlainSocketFactory.getSocketFactory(), 433));
		ClientConnectionManager conman = new ThreadSafeClientConnManager(params, schreg);

		HttpClient client = new DefaultHttpClient(conman, params);
		String uri = IP + "/login.do";
		HttpPost post = new HttpPost(uri);                                      // Post
        // 参数内容
		NameValuePair paramloginName = new BasicNameValuePair("LoginName", loginName);
		NameValuePair parmaLoginPassword = new BasicNameValuePair("LoginPassword", loginPassword);
		List<NameValuePair> parameters = new ArrayList<NameValuePair>();
		parameters.add(paramloginName);
		parameters.add(parmaLoginPassword);

		post.setEntity(new UrlEncodedFormEntity(parameters, HTTP.UTF_8));       // 在"post"添加"参数"
		HttpResponse response = client.execute(post);                            // 响应

		/**
		 * 200 404 500 406 <p>
         * 从"响应(response)"拿到"响应码"
		 */
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {       // 200  不是200进入
			throw new ServiceRulesException(LoginActivity.MSG_SERVER_ERROR);    // 抛出:请求服务器错误
		}

        // EntityUtils 实体工具:
        // 将从"响应"拿到的"数据"设为"HTTP.UTF_8"编码;  转换成"String"
		String result = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		if (result.equals("success")) {

		} else {
			throw new ServiceRulesException(LoginActivity.MSG_LOGIN_FAILED);
		}

		// HttpClient c = CustomerHttpClient.getHttpClient();

	}

    /***************************************** 注册 ***********************************************/
	@Override
	public void userRegister(String loginName, List<String> interesting)
			throws Exception {

		// Thread.sleep(3000);

		HttpClient client = new DefaultHttpClient();
		String uri = IP + "/register.do";
		HttpPost post = new HttpPost(uri);

		/**
		 * JSON数据的封装 **********************************************
         *
         * 将json数据封装，放到 pose的参数中，用于给"服务器"
		 */
		JSONObject object = new JSONObject();
		object.put("LoginName", loginName);
		JSONArray array = new JSONArray();
		if (interesting != null) {
			for (String string : interesting) {         // 兴趣数组
				array.put(string);
			}
		}
		object.put("Interesting", array);
		/******************************************************/

		NameValuePair parameter = new BasicNameValuePair("Data", object.toString());    // 参数名是 "Data"
		List<NameValuePair> params = new ArrayList<NameValuePair>();
		params.add(parameter);

        post.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));       // 在"post"添加"参数"
        HttpResponse response = client.execute(post);                            // 响应

        /**
         * 200 404 500 406 <p>
         * 从"响应(response)"拿到"响应码"
         */
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
		}

		// 从响应中取得服务器的返回结果
		String results = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		// 解析JSON
		JSONObject jsonResults = new JSONObject(results);
		String result = jsonResults.getString("result");        // 拿到"result"，对应的"值"

		if (result.equals("success")) {
			// 注册success
		} else {
			// 注册failed
			String errorMsg = jsonResults.getString("errorMsg");
			throw new ServiceRulesException(errorMsg);
		}

	}


    /********************************************************* 获取(用户列表) ******************************************************/
	@Override
	public List<Student> getStudents() throws Exception {
		List<Student> students = new ArrayList<Student>();

		HttpClient client = new DefaultHttpClient();
		String uri = IP + "/getStudents.do";
		HttpGet get = new HttpGet(uri);

		HttpResponse response = client.execute(get);

        /**
         * 200 404 500 406 <p>
         * 从"响应(response)"拿到"响应码"
         */
		int statusCode = response.getStatusLine().getStatusCode();
		if (statusCode != HttpStatus.SC_OK) {
			throw new ServiceRulesException(RegisterActivity.MSG_SERVER_ERROR);
		}

        // 从响应中取得服务器的返回结果
		String results = EntityUtils.toString(response.getEntity(), HTTP.UTF_8);

		// 解析JSON数据（JSONArray）
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

    /*************************************  构造POST参数传递(封装参数) *********************************************/
	/**
	 * 构造POST参数传递(封装参数)
	 * 
	 * @param params   Map键值对--"键=>参数"   "值=>参数值"
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	private static StringBuffer setPostPassParams(Map<String, String> params) throws UnsupportedEncodingException {
		StringBuffer stringBuffer = new StringBuffer();
		/**
		 * k1=v1&k2=v2....
		 */
		for (Map.Entry<String, String> entry : params.entrySet()) {
            // 组合将  键=值&键=值&键=值&
			stringBuffer.append(entry.getKey()).append("=")
					.append(URLEncoder.encode(entry.getValue(), "UTF-8"))
					.append("&");
		}

		// 把最后"&"去掉
		stringBuffer.deleteCharAt(stringBuffer.length() - 1);

		return stringBuffer;
	}


    /********************************************************** 获取图片 *******************************************************/
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
			// url = new URL("http://192.168.23.1:8080/MyTestServer/getImage.jpeg");
			// urlConnection = (HttpURLConnection) url.openConnection();
			// urlConnection.setConnectTimeout(3000);       // 设置请求的超时时间
			// urlConnection.setReadTimeout(3000);          // 设置响应的超时时间
			// urlConnection.setDoInput(true);              // 读数据
			// urlConnection.setDoOutput(true);             // 写数据
			// urlConnection.setRequestMethod("POST");      // 设置请求方法是POST请求
			// urlConnection.setUseCaches(false);           // 取消缓存
			 /*** HTTP协议 ***/
			 // urlConnection.setRequestProperty("Content-Type", newValue);     // 传输的类型
			 // urlConnection.setRequestProperty("Content-Length", newValue);   // 传输的长度
            /*** 向服务器写数据 ***/
			// urlConnection.connect();
			// out = urlConnection.getOutputStream();       // 输出流，客户端向服务器发送数据
			// out.write(data);                             // 向服务器写数据的关键代码  // 这里就是获取，1.jpg图片
			// out.flush();                                 // 刷新

            /**
             * 响应状态码
             */
			// int responseCode = urlConnection.getResponseCode();
			// if (responseCode != HttpURLConnection.HTTP_OK) {
			//      throw new ServiceRulesException("请求服务器出错。");
			// }
			// in = new BufferedInputStream(urlConnection.getInputStream());
			// Log.d("TAG", "文件大小：" + in.available());
			// if (in != null) {
			//      // 转换类型 InputStream -- > Bitmap
			//      bitmap = BitmapFactory.decodeStream(in);
			// }

			/*** get ****/
			url = new URL(IP + "/getImage.jpeg?id=2");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setDoInput(true);                         // 设置可以读取,读取服务器传给我们的图片流
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();                                // 连接
			// 响应状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new ServiceRulesException("请求服务器出错。");
			}
			
			int length = urlConnection.getContentLength();

			in = urlConnection.getInputStream();
			
			//SAXParserFactory
			Log.d("TAG", "文件大小：" + length);
			if (in != null) {
				// 转换类型 InputStream -- > Bitmap
				bitmap = BitmapFactory.decodeStream(in);
			}
		} finally {                                              // 结束断开连接
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


    /********************************************************** 上传数据 *******************************************************/
	@Override
	public String userUpload(InputStream in, Map<String, String> data)
			throws Exception {

		HttpClient client = new DefaultHttpClient();
		HttpPost post = new HttpPost(IP + "/upload.do");

		// 要把数据封装到post里面去
		/**
		 * Httpmine
         * v 4.2.3 jar
         * v 4.3.3 jar ---还要-> httpcore v4.3.2 MultipartEntity
		 * -----------------------------------
         * 以上两个版本的写法也是不一样
		 */

		MultipartEntity entity = new MultipartEntity();                 // post 存放上传"数据"的"实体"
		// “普通”字符串数据封装
		for (Map.Entry<String, String> entry : data.entrySet()) {
			String key = entry.getKey();                                // Map的"键"
			String value = entry.getValue();                            // Map的"值"
			entity.addPart(key, new StringBody(value, Charset.forName("UTF-8")));               // 实体填充数据
		}

		// “二进制的流文件”数据封装
        // multipart/form-data ==> 告诉服务器是"复杂数据"
        // 这里复杂数据是一个图片: 其实我们这个"方法"可以传多一个"值---上传文件，保存到服务器的"图片名!!!""
		entity.addPart("file", new InputStreamBody(in, "multipart/form-data", "test.jpg")); // 实体填充数据

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

    /********************************************************** 下载东西 *******************************************************/
	@Override
	public void userDownload() throws Exception {
		InputStream in = null;
		OutputStream out = null;
		URL url = null;
		HttpURLConnection urlConnection = null;

		try {
			url = new URL(IP + "/download.do");
			urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setConnectTimeout(10000);
			urlConnection.setReadTimeout(20000);
			urlConnection.setDoInput(true);
			urlConnection.setRequestMethod("GET");
			urlConnection.connect();

            // 响应状态码
			int responseCode = urlConnection.getResponseCode();
			if (responseCode != HttpURLConnection.HTTP_OK) {
				throw new ServiceRulesException("Server Error");
			}

			// HttpClient client = null;
			// HttpResponse response =null;
			// response.getEntity().getContent();

			in = new BufferedInputStream(urlConnection.getInputStream());
			out = new BufferedOutputStream(new FileOutputStream(new File(
					Environment.getExternalStorageDirectory() + "/联系方式.jpg")));

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
