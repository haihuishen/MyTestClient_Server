package com.maizi.rico.demo.client_server_data_exchange;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class DownloadActivity extends Activity {

	private Button btnDownload;

	private UserService userService = new UserServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.download_activity);

		this.btnDownload = (Button) this.findViewById(R.id.btn_download);

		this.btnDownload.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {

				new Thread(new Runnable() {

					@Override
					public void run() {

						try {
							userService.userDownload();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(DownloadActivity.this,
											"下载完毕", Toast.LENGTH_SHORT).show();

								}
							});
						} catch (final ServiceRulesException e) {
							e.printStackTrace();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(DownloadActivity.this,
											e.getMessage(), Toast.LENGTH_SHORT)
											.show();

								}
							});
						}

						catch (Exception e) {
							e.printStackTrace();
							runOnUiThread(new Runnable() {

								@Override
								public void run() {
									Toast.makeText(DownloadActivity.this,
											"下载错误", Toast.LENGTH_SHORT).show();

								}
							});
						}

					}
				}).start();

			}
		});

	}

}
