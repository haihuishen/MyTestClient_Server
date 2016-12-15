package com.maizi.rico.demo.client_server_data_exchange;

import java.lang.ref.WeakReference;
import java.net.SocketTimeoutException;

import org.apache.http.conn.ConnectTimeoutException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class LoginActivity extends Activity {

	private EditText txtLoginName;
	private EditText txtLoginPassword;
	private Button btnLogin;
	private Button btnReset;

	private UserService userService = new UserServiceImpl();

	private static final int FLAG_LOGIN_SUCCESS = 1;

	private static final String MSG_LOGIN_ERROR = "登录出错。";
	private static final String MSG_LOGIN_SUCCESS = "登录成功。";
	public static final String MSG_LOGIN_FAILED = "登录名|登录密码出错。";
	public static final String MSG_SERVER_ERROR = "请求服务器错误。";
	public static final String MSG_REQUEST_TIMEOUT = "请求服务器超时。";
	public static final String MSG_RESPONSE_TIMEOUT = "服务器响应超时。";

	private static ProgressDialog dialog;

	private void init() {
		this.txtLoginName = (EditText) this.findViewById(R.id.txt_login_name);
		this.txtLoginPassword = (EditText) this
				.findViewById(R.id.txt_login_password);
		this.btnLogin = (Button) this.findViewById(R.id.btn_login);
		this.btnReset = (Button) this.findViewById(R.id.btn_reset);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_activity);
		// 初始化控件
		this.init();

		// 点击“登录”
		this.btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				final String loginName = txtLoginName.getText().toString();
				final String loginPassword = txtLoginPassword.getText()
						.toString();

				// Toast.makeText(view.getContext(), "登录名：" + loginName,
				// Toast.LENGTH_SHORT).show();
				// Toast.makeText(view.getContext(), "登录密码：" + loginPassword,
				// Toast.LENGTH_SHORT).show();

				/**
				 * 输入值验证
				 */

				/**
				 * loading....
				 */

				dialog = new ProgressDialog(LoginActivity.this);
				dialog.setTitle("请等待");
				dialog.setMessage("登录中...");
				dialog.setCancelable(false);
				dialog.show();

				/**
				 * 副线程
				 */
				Thread thread = new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							userService.userLogin(loginName, loginPassword);
							handler.sendEmptyMessage(FLAG_LOGIN_SUCCESS);
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
							data.putSerializable("ErrorMsg", MSG_LOGIN_ERROR);
							msg.setData(data);
							handler.sendMessage(msg);
						}

					}
				});
				thread.start();

			}
		});

		// 点击“重置”
		this.btnReset.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				txtLoginName.setText("");
				txtLoginPassword.setText("");

			}
		});
	}

	private void showTip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

	}

	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivity;

		public IHandler(LoginActivity activity) {
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
				((LoginActivity) mActivity.get()).showTip(errorMsg);
				break;
			case FLAG_LOGIN_SUCCESS:
				((LoginActivity) mActivity.get()).showTip(MSG_LOGIN_SUCCESS);
				break;
			default:
				break;
			}
		}

	}

	private IHandler handler = new IHandler(this);

}
