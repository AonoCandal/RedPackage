package com.aono.redpackage;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.aono.redpackage.redpackage.IRedPackageController;
import com.aono.redpackage.redpackage.RedPackageCallBack;
import com.aono.redpackage.redpackage.RedPackageController;

public class MainActivity extends AppCompatActivity {


	private IRedPackageController controller;
	private RelativeLayout mRootView;
	private Button mBtnShowPkg;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initParams();
		initView();
	}

	private void initView() {
		mRootView = (RelativeLayout) findViewById(R.id.rootView);
		mBtnShowPkg = (Button) findViewById(R.id.btn_show_pkg);
		controller.initRedPkg(this, mRootView);



		mBtnShowPkg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				controller.showRedPkg();
			}
		});

	}

	private void initParams() {
		controller = new RedPackageController(new RedPackageCallBack() {
			@Override
			public void clickOpenRedPkg(final ObjectAnimator animator) {
				new Handler(){
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 102){
							if (animator.getCurrentPlayTime() < 5000){
								animator.setRepeatCount(6);
							}else {
								animator.cancel();
								controller.openRedPkg();
							}
						}
					}
				}.sendEmptyMessageDelayed(102, 10);
			}

			@Override
			public void clickOpenRedPkgNoDelay(final ObjectAnimator animator) {
				new Handler(){
					@Override
					public void handleMessage(Message msg) {
						if (msg.what == 102){
							animator.end();
							controller.openRedPkg();
							Toast.makeText(getApplicationContext(), "打开红包", Toast.LENGTH_SHORT).show();
						}
					}
				}.sendEmptyMessageDelayed(102, 10);
			}
		});
	}
}
