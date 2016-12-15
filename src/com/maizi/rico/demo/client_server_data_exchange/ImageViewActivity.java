package com.maizi.rico.demo.client_server_data_exchange;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class ImageViewActivity extends Activity {

	private ImageView imageViewSample;

	private UserService userService = new UserServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view_activity);

		this.imageViewSample = (ImageView) this
				.findViewById(R.id.image_view_sample);

		// 代码指定该控件加载一幅图片源
		// this.imageViewSample.setImageResource(R.drawable.sample);
		// this.imageViewSample.setImageDrawable(this.getResources().getDrawable(R.drawable.sample))

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final Bitmap bitmap = userService.getImage();

					if (bitmap != null) {
						runOnUiThread(new Runnable() {

							@Override
							public void run() {
								imageViewSample.setImageBitmap(bitmap);
							}
						});
					}

					//
				} catch (final ServiceRulesException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(ImageViewActivity.this,
									e.getMessage(), Toast.LENGTH_LONG).show();

						}
					});
				}

				catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(ImageViewActivity.this, "载入远程图片失败",
									Toast.LENGTH_LONG).show();

						}
					});
				}

			}
		}).start();

	}

}
