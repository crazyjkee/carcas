package com.camera.andraft;

import java.io.ByteArrayOutputStream;

import android.content.Context;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera.PreviewCallback;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnTouchListener;

import com.draw.android.DrawThread;
import com.ext.android.BaseCust;

public class OverlayView extends SurfaceView implements SurfaceHolder.Callback,CameraView.CameraReadyCallback,
		OnTouchListener {
	public static interface UpdateDoneCallback {
		public void onUpdateDone();
	}

	public static interface onTouchDoneCallback {
		public void onTouchDone(MotionEvent ev);
	}
	private onTouchDoneCallback onTouchCb = null;
	private UpdateDoneCallback updateDoneCb = null;
	private DisplayMetrics dm;
	private DrawThread drawThread;
	private CameraView cameraView_;
	private SurfaceView surfaceView_;
	private SurfaceHolder surfaceHolder_;

	public OverlayView(Context context, DisplayMetrics dm) {
		super(context);
		this.dm = dm;
		surfaceView_ = this;
        surfaceHolder_ = surfaceView_.getHolder();
        surfaceHolder_.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		surfaceHolder_.addCallback(this);
		

	}

	public void DrawResult(DisplayMetrics dm) {
		this.dm = dm;
		// scale = dm.density;

		postInvalidate();
		Log.d("myLogs", "DrawResult");
	}

	public void setUpdateDoneCallback(UpdateDoneCallback cb) {
		updateDoneCb = cb;
	}

	public void setOnTouchDoneCallback(onTouchDoneCallback cb) {
		onTouchCb = cb;
	}

	@Override
	public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {
		Log.d("myLogs", "surfaceChanged");
		//cameraView_.StopPreview();
		//cameraView_.Release();
		//initCamera();

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		initCamera();
		cameraView_.setupCamera();
		drawThread = new DrawThread(surfaceHolder_, dm);
		drawThread.setRunning(true);
	/*	drawThread.start();
		for (BaseCust m : drawThread.mycust)
			this.setOnTouchListener(this);*/
		Log.d("myLogs","surfaceCreated");
	}

	private void initCamera() {
	cameraView_ = CameraView.getInstance(surfaceHolder_);
		cameraView_.setCameraReadyCallback(this);	
		//cameraView_.setupCamera();
	}
	
	@Override
	public void onCameraReady() {

		int wid = cameraView_.Width();
		int hei = cameraView_.Height();
		cameraView_.StopPreview();
		cameraView_.setupCamera(wid, hei, previewCb_);
		cameraView_.StartPreview();
		Log.d("myLogs","onCameraReady");
	}
	private PreviewCallback previewCb_ = new PreviewCallback() {
		private ByteArrayOutputStream bf;

		public void onPreviewFrame(byte[] frame, android.hardware.Camera c) {
			drawThread.setRunning(false);

			int picWidth = cameraView_.Width();
			int picHeight = cameraView_.Height();
			bf = new ByteArrayOutputStream();
			boolean ret;
			try {
				YuvImage image = new YuvImage(frame, ImageFormat.NV21,
						picWidth, picHeight, null);
				ret = image.compressToJpeg(new Rect(0, 0, picWidth, picHeight),
						30, bf);
			} catch (Exception ex) {
				ret = false;
			}
			Log.d("myLogs","frame");
			drawThread.start();
		}
	};
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		Log.d("MyLogs", "Destroyed");
		//cameraView_.StopPreview();
		cameraView_.Release();
		boolean retry = true;
		// завершаем работу потока
		drawThread.setRunning(false);
		while (retry) {
			try {
				drawThread.join();
				retry = false;
			} catch (InterruptedException e) {
				// если не получилось, то будем пытаться еще и еще
			}
		}

	}


	@Override
	public boolean onTouch(View v, MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			for (BaseCust m : drawThread.mycust)
				m.Touch(event);
			// Log.d("myLogs","ACTION_DOWN");
			return true;
		}

		return false;
	}

}
