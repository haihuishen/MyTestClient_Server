package com.maizi.rico.demo.client_server_data_exchange;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class UploadActivity extends Activity {

	private Button btnSelect;

	private static final int FLAG_LOAD_IMAGE = 1;
	private String pathName;

	private UserService userService = new UserServiceImpl();

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == FLAG_LOAD_IMAGE) {
			if (data == null) {
				Toast.makeText(this, "你没有选择任何图片。", Toast.LENGTH_LONG).show();
			} else {
				Uri uri = data.getData();
				if (uri == null) {
					Toast.makeText(this, "你没有选择任何图片。", Toast.LENGTH_LONG)
							.show();
				} else {
					String path = null;
					String[] pojo = { MediaStore.Images.Media.DATA };
					Cursor cursor = getContentResolver().query(uri, pojo, null,
							null, null);
					if (cursor != null) {
						int columnIndex = cursor.getColumnIndexOrThrow(pojo[0]);
						cursor.moveToFirst();
						path = cursor.getString(columnIndex);
						cursor.close();

					}

					if (path == null) {
						Toast.makeText(this, "未能获得图片的物理路径。", Toast.LENGTH_LONG)
								.show();
					} else {
						Toast.makeText(this, "图片的物理路径是：" + path,
								Toast.LENGTH_LONG).show();
						pathName = path;

						new AlertDialog.Builder(this)
								.setTitle("提示")
								.setMessage("你要上传选择的图片吗？")
								.setPositiveButton("确定",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												doUpload();

											}
										})
								.setNegativeButton("取消",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface arg0,
													int arg1) {
												// TODO Auto-generated method
												// stub

											}
										}).create().show();
					}
				}
			}
		}
	}

	private void doUpload() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					// 文件二进制流数据
					InputStream in = new FileInputStream(new File(pathName));
					// 普通字符串数据
					Map<String, String> data = new HashMap<String, String>();
					data.put("Name", "RICO");
					data.put("Gender", "男");

					final String result = userService.userUpload(in, data);
					runOnUiThread(new Runnable() {

						@Override
						public void run() {

							Toast.makeText(UploadActivity.this, result,
									Toast.LENGTH_LONG).show();
						}
					});
				} catch (final ServiceRulesException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(UploadActivity.this, e.getMessage(),
									Toast.LENGTH_LONG).show();

						}
					});
				}

				catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(UploadActivity.this, "上传出错。",
									Toast.LENGTH_LONG).show();

						}
					});
				}

			}
		}).start();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.upload_activity);

		this.btnSelect = (Button) this.findViewById(R.id.btn_select);

		this.btnSelect.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View view) {
				Intent intent = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
				startActivityForResult(intent, FLAG_LOAD_IMAGE);

			}
		});

	}

}
