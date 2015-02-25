package com.vistor.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import com.example.vistorproject.R;
import com.vistor.common.AppUtil;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class mainActivity extends Activity{
	
	public static final String TAG = "MainActivity";
	
	protected TextView peopleCountTextView;
	private EditText serverIpEdit,serverportEdit;
	private Button queryBtn;
	private ActionBar mActionBar;
	private LayoutInflater mInflater;

	private String line;


	public static final String SERVER_IP = "121.40.179.150";

	public static final int SERVER_PORT_PEOPLE = 10001;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		init();
	}

	private void init(){
		initActionBar(getString(R.string.main_title),true);
		peopleCountTextView = (TextView)findViewById(R.id.main_people_count);
		serverIpEdit = (EditText)findViewById(R.id.main_server_ip);
		serverportEdit = (EditText)findViewById(R.id.main_server_port);
		queryBtn = (Button)findViewById(R.id.main_find);
		//queryBtn.setOnClickListener(onClickListener);
		queryBtn.setOnClickListener(new ReceiverListener());
	}

	@SuppressLint("NewApi")
	private void initActionBar(String title,boolean isShow){
		mActionBar = getActionBar();
		if(!isShow){
			mActionBar.hide();
			return;
		}
		mActionBar.setDisplayHomeAsUpEnabled(false);
		mActionBar.setDisplayShowCustomEnabled(true);
		mActionBar.setDisplayShowHomeEnabled(false);
		mActionBar.setDisplayShowTitleEnabled(false);
		View view = mInflater.inflate(R.layout.actionbar, null);
		ImageView backView = (ImageView) view.findViewById(R.id.actionbar_back);
		TextView titleView = (TextView) view.findViewById(R.id.actionbar_title);

		OnClickListener clickListener = new OnClickListener(){
			@Override
			public void onClick(View v) {
				finish();
			}};
			backView.setOnClickListener(clickListener);
			titleView.setOnClickListener(clickListener);
			if(AppUtil.isNotEmpty(title)){
				titleView.setText(title);
			}
			mActionBar.setCustomView(view);
	}

	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View arg0) {
			String serverIpStr = serverIpEdit.getText().toString().trim();
			String serverPortStr = serverportEdit.getText().toString().trim();
			if(AppUtil.isEmpty(serverIpStr)){
				AppUtil.showShortMessage(getApplicationContext(), getString(R.string.main_check_server_ip));
			}
			if(AppUtil.isEmpty(serverPortStr)){
				AppUtil.showShortMessage(getApplicationContext(), getString(R.string.main_check_server_port));
			}
		}
	};

	@SuppressLint("HandlerLeak")  
	class ReceiverListener implements OnClickListener {  

		@Override  
		public void onClick(View v) {  
			// TODO Auto-generated method stub  
			new Thread() {  
				@Override  
				public void run() {  
					// 执行完毕后给handler发送一个空消息  
					try {  
						// 实例化Socket  
						Socket socket = new Socket(SERVER_IP, SERVER_PORT_PEOPLE);  
						// 获得输入流  
						BufferedReader br = new BufferedReader(  
								new InputStreamReader(socket.getInputStream()));  
						line = br.readLine();  
						br.close();  
					} catch (UnknownHostException e) {  
						// TODO Auto-generated catch block  
						e.printStackTrace();  
					} catch (IOException e) {  
						// TODO Auto-generated catch block  
						e.printStackTrace();  
					}  
					handler.sendEmptyMessage(0);  
				}  
			}.start();  
		}  
	}  


	private Handler handler = new Handler(){

		public void handleMessage(Message msg){
			super.handleMessage(msg);
			peopleCountTextView.setText(line);
			Log.d(TAG,"----->"+line);
		}
	};
}

