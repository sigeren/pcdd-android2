package com.df.youle.ui;

import android.os.Bundle;
import android.os.Handler;

import com.df.youle.R;
import com.df.youle.ui.base.BaseFragmentActivity;

public class LoadingActivity extends BaseFragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_loading);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				finish();
			}
		}, 1500);
	}

	@Override
	public void onBackPressed() {
	}
}
