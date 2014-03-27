package com.test.android;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.camera.andraft.OverlayView;

public class MainActivity extends Activity implements
		OverlayView.UpdateDoneCallback {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(new OverlayView(this, this.getResources()
				.getDisplayMetrics()));
	}

	@Override
	protected void onDestroy() {
		Log.d("myLogs", "onDestroy");
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		Log.d("myLogs", "onPause");
		super.onPause();
	}

	@Override
	protected void onResume() {
		Log.d("myLogs", "onResume");
		super.onResume();
	}

	@Override
	public void onUpdateDone() {
		Log.d("myLogs", "onUpdateDone");

	}

}
