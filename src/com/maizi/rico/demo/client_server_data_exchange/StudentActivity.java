package com.maizi.rico.demo.client_server_data_exchange;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.maizi.rico.demo.client_server_data_exchange.adapter.StudentAdapter;
import com.maizi.rico.demo.client_server_data_exchange.entity.Student;
import com.maizi.rico.demo.client_server_data_exchange.service.ServiceRulesException;
import com.maizi.rico.demo.client_server_data_exchange.service.UserService;
import com.maizi.rico.demo.client_server_data_exchange.service.UserServiceImpl;

public class StudentActivity extends Activity {

	private ListView listViewStudent;
	private List<Student> studentList;
	private StudentAdapter adapter;

	private static ProgressDialog dialog;
	private UserService userService = new UserServiceImpl();
	
	private static final int FLAG_STUDENTS_SUCCESS = 1;
	public static final String MSG_STUDENT_ERROR = "加载数据错误。";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.student_activity);

		this.listViewStudent = (ListView) this
				.findViewById(R.id.listview_student);

		/**
		 * 构建本地数据源
		 */
		this.studentList = new ArrayList<Student>();

		// this.studentList.add(new Student(100L, "Tom", 20));
		// this.studentList.add(new Student(101L, "Jack", 21));
		// this.studentList.add(new Student(102L, "Lisa", 22));

		
		

		

		dialog = new ProgressDialog(StudentActivity.this);
		dialog.setTitle("请等待");
		dialog.setMessage("加载数据中...");
		dialog.setCancelable(false);
		dialog.show();

		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					studentList = userService.getStudents();
					handler.sendEmptyMessage(FLAG_STUDENTS_SUCCESS);
				} catch (ServiceRulesException e) {
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
					data.putSerializable("ErrorMsg", MSG_STUDENT_ERROR);
					msg.setData(data);
					handler.sendMessage(msg);
				}

			}
		}).start();

	}

	private void showTip(String str) {
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();

	}
	
	private void loadDataListView() {
		
		if(this.adapter == null) {
			this.adapter = new StudentAdapter(this, R.layout.student_item,
				this.studentList);
			this.listViewStudent.setAdapter(this.adapter);
		} else {
			this.adapter.notifyDataSetChanged();
		}
	}

	private static class IHandler extends Handler {

		private final WeakReference<Activity> mActivity;

		public IHandler(StudentActivity activity) {
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
				((StudentActivity) mActivity.get()).showTip(errorMsg);
				break;
			 case FLAG_STUDENTS_SUCCESS:
			 ((StudentActivity)
					 mActivity.get()).loadDataListView();
			 break;
			default:
				break;
			}
		}

	}

	private IHandler handler = new IHandler(this);

}
