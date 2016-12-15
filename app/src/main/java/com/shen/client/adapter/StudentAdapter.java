package com.shen.client.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shen.client.R;
import com.shen.client.entity.Student;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student>{
	
	
	private LayoutInflater mInflater;
	private int mResource;

	/**
	 * 
	 * @param context 上下文
	 * @param resource student_item(item布局)
	 * @param objects List<Student>
	 */
	public StudentAdapter(Context context, int resource, List<Student> objects) {
		super(context, resource, objects);
		this.mInflater = LayoutInflater.from(context);
		this.mResource = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout view = null;
		
		if(convertView == null) {
			view = (LinearLayout)mInflater.inflate(mResource, null);       // 布局填充成View
		} else {
			view = (LinearLayout)convertView;
		}
		
		// 获得数据绑定到一个Item上的对象
		Student student = getItem(position);     // 在构造函数中，super(context, resource, objects);已经赋值了
		
		/**
		 * 获得Item中的TextView控件
		 */
		TextView txtId = (TextView)view.findViewById(R.id.txt_id);
		TextView txtName = (TextView)view.findViewById(R.id.txt_name);
		TextView txtAge = (TextView)view.findViewById(R.id.txt_age);
		
		
		/**
		 * 设置值
		 */
		txtId.setText(student.getId().toString());
		txtName.setText(student.getName());
		txtAge.setText(String.valueOf(student.getAge()));
		return view;
	}
	
	

}
