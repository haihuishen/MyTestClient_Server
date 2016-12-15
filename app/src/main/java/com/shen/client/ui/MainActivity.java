package com.shen.client.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.shen.client.R;


/**
 * Created by shen on 12/15 0015.
 */
public class MainActivity extends Activity implements View.OnClickListener{

    private Button mBtnLogin;                   // 登录
    private Button mBtnRegister;                // 注册
    private Button mBtnGetUser;                 // 获取用户
    private Button mBtnLoadImaghe;              // 获取图片
    private Button mBtnUpLoad;                  // 上传数据
    private Button mBtnDownload;                // 下载数据

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        initView();
        initListener();
    }

    /**
     * 初始化控件
     */
    private void initView(){

        mBtnLogin = (Button) findViewById(R.id.btn_login);
        mBtnRegister = (Button) findViewById(R.id.btn_register);
        mBtnGetUser = (Button) findViewById(R.id.btn_get_user);
        mBtnLoadImaghe = (Button) findViewById(R.id.btn_load_imgae);
        mBtnUpLoad = (Button) findViewById(R.id.btn_upload);
        mBtnDownload = (Button) findViewById(R.id.btn_download);

    }

    /**
     * 监听控件
     */
    private void initListener(){
        mBtnLogin.setOnClickListener(this);
        mBtnRegister.setOnClickListener(this);
        mBtnGetUser.setOnClickListener(this);
        mBtnLoadImaghe.setOnClickListener(this);
        mBtnUpLoad.setOnClickListener(this);
        mBtnDownload.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.btn_login:
                intent = new Intent(this,LoginActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_register:
                intent = new Intent(this,RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_get_user:
                intent = new Intent(this,StudentActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_load_imgae:
                intent = new Intent(this,ImageViewActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_upload:
                intent = new Intent(this,UploadActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_download:
                intent = new Intent(this,DownloadActivity.class);
                startActivity(intent);
                break;
        }
    }
}
