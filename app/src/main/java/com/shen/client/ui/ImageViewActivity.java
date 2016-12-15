package com.shen.client.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.shen.client.R;
import com.shen.client.service.ServiceRulesException;
import com.shen.client.service.UserService;
import com.shen.client.service.UserServiceImpl;

public class ImageViewActivity extends Activity {

	private ImageView mIvSample;

	private UserService userService = new UserServiceImpl();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.image_view_activity);

		this.mIvSample = (ImageView) this.findViewById(R.id.image_view_sample);

		// 代码指定该控件加载一幅图片源
		// mIvSample.setImageResource(R.drawable.sample);
		// mIvSample.setImageDrawable(this.getResources().getDrawable(R.drawable.sample))

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					final Bitmap bitmap = userService.getImage();           // 从网络获取图片

					if (bitmap != null) {
						runOnUiThread(new Runnable() {                       // runOnUiThread 在主线程中改变UI
							@Override
							public void run() {
								mIvSample.setImageBitmap(bitmap);
							}
						});
					}
				} catch (final ServiceRulesException e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {
						
						@Override
						public void run() {
							Toast.makeText(ImageViewActivity.this,
									e.getMessage(), Toast.LENGTH_LONG).show();

						}
					});
				} catch (Exception e) {
					e.printStackTrace();
					runOnUiThread(new Runnable() {

						@Override
						public void run() {
							Toast.makeText(ImageViewActivity.this, "载入远程图片失败", Toast.LENGTH_LONG).show();
						}
					});
				}
			}
		}).start();

	}

}
