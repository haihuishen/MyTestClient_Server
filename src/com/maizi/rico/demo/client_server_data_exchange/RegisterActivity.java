package com.maizi.rico.demo.client_server_data_exchange;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class RegisterActivity extends Activity {
	
	
	private static final int FLAG_REGISTER_SUCCESS = 1;
	
	private static final String MSG_REGISTER_ERROR = "注册出错。";
	private static final String MSG_REGISTER_SUCCESS = "注册成功。";
//	public static final String MSG_REGISTER_FAILED = "登录名|登录密码出错。";
	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	public static final String MSG_REQUEST_TIMEOUT = "请求服务器超时。";
	public static final String MSG_RESPONSE_TIMEOUT = "服务器响应超时。";

	
	private EditText txtLoginName;
	private CheckBox chkGame;
	private CheckBox chkMusic;
	private CheckBox chkSports;
	private Button btnRegister;
	
	private static ProgressDialog dialog;
	private UserService userService = new UserServiceImpl();
	
	private void init() {
		this.txtLoginName = (EditText)this.findViewById(R.id.txt_login_name);
		this.chkGame = (CheckBox)this.findViewById(R.id.chk_game);
		this.chkMusic = (CheckBox)this.findViewById(R.id.chk_musci);
		this.chkSports = (CheckBox)this.findViewById(R.id.chk_sports);
		this.btnRegister = (Button)this.findViewById(R.id.btn_register);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_activity);
		
		this.init();
		
		this.btnRegister.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				
				final String loginName = txtLoginName.getText().toString();
				final List<String> interesting = new ArrayList<String>();
				if(chkGame.isChecked()) {
					interesting.add(chkGame.getText().toString());
				}
				if(chkMusic.isChecked()) {
					interesting.add(chkMusic.getText().toString());
				}
				if(chkSports.isChecked()) {
					interesting.add(chkSports.getText().toString());
				}
				
				
				dialog = new ProgressDialog(RegisterActivity.this);
				dialog.setTitle("请等待");
				dialog.setMessage("登录中...");
				dialog.setCancelable(false);
				dialog.show();
				
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							userService.userRegister(loginName, interesting);
							handler.sendEmptyMessage(FLAG_REGISTER_SUCCESS);
						} catch (ConnectTimeoutException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("ErrorMsg",
									MSG_REQUEST_TIMEOUT);
							msg.setData(data);
							handler.sendMessage(msg);
						} catch (SocketTimeoutException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("ErrorMsg",
									MSG_RESPONSE_TIMEOUT);
							msg.setData(data);
							handler.sendMessage(msg);
						}

						catch (ServiceRulesException e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("ErrorMsg", e.getMessage());
							msg.setData(data);
							handler.sendMessage(msg);
						} catch (Exception e) {
							e.printStackTrace();
							Message msg = new Message();
							Bundle data = new Bundle();
							data.putSerializable("ErrorMsg", MSG_REGISTER_ERROR);
							msg.setData(data);
							handler.sendMessage(msg);
						}

					}
				});
				thread.start();
				
				
				
			}
		});
		
		
	}
	
	private  void showTip(String str) {
		
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

	}
	
	
	
	
	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivity;

		public IHandler(RegisterActivity activity) {
			mActivity = new WeakReference<Activity>(activity);
		}

		@Override
		public void handleMessage(Message msg) {

			if (dialog != null) {
				dialog.dismiss();
			}

			int flag = msg.what;
			switch (flag) {
			case 0:
				String errorMsg = (String) msg.getData().getSerializable(
						"ErrorMsg");
				((RegisterActivity) mActivity.get()).showTip(errorMsg);
				break;
			case FLAG_REGISTER_SUCCESS:
				((RegisterActivity) mActivity.get()).showTip(MSG_REGISTER_SUCCESS);
				break;
			default:
				break;
			}
		}

	}

	private IHandler handler = new IHandler(this);
	
	

}
