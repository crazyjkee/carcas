package com.camera.andraft;

import java.util.List;

import android.content.DialogInterface.OnClickListener;
import android.hardware.Camera;
import android.hardware.Camera.FaceDetectionListener;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.Size;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

public class CameraView {
	 private static volatile CameraView instance;
	    public static interface CameraReadyCallback { 
	        public void onCameraReady(); 
	    }  

	    
	    private Camera camera_ = null;
	    private SurfaceHolder surfaceHolder_ = null;
	    CameraReadyCallback cameraReadyCb_ = null;
	 
	    private List<Camera.Size> supportedSizes; 
	    private Camera.Size procSize_;

	    private CameraView(SurfaceHolder holder){

	        surfaceHolder_ = holder;
	        //surfaceHolder_.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	        //surfaceHolder_.addCallback(this); 
	    }
	    public static CameraView getInstance(SurfaceHolder holder) {
	        CameraView localInstance = instance;
	        if (localInstance == null) {
	            synchronized (CameraView.class) {
	                localInstance = instance;
	                if (localInstance == null) {
	                    instance = localInstance = new CameraView(holder);
	                }
	            }
	        }
	        return localInstance;
	    }

	    public List<Camera.Size> getSupportedPreviewSize() {
	        return supportedSizes;
	    }

	    public int Width() {
	        return procSize_.width;
	    }

	    public int Height() {
	        return procSize_.height;
	    }

	    public void setCameraReadyCallback(CameraReadyCallback cb) {
	        cameraReadyCb_ = cb;
	    }

	    public void StartPreview(){
	        if ( camera_ == null)
	            return;
	        camera_.startPreview();
	    }
	    
	    public void StopPreview(){
	        if ( camera_ == null)
	            return;
	        camera_.stopPreview();
	    }

	    public void AutoFocus() {
	        camera_.autoFocus(afcb);
	    }

	    public void Release() {
	        if ( camera_ != null) {
	            camera_.stopPreview();
	            camera_.setPreviewCallback(null);
	            camera_.release();
	            camera_ = null;
	        }
	    }
	    
	    public void setupCamera(int wid, int hei, PreviewCallback cb) {
	        procSize_.width = wid;
	        procSize_.height = hei;
	        
	        Camera.Parameters p = camera_.getParameters();        
	        p.setPreviewSize(procSize_.width, procSize_.height);
	        camera_.setParameters(p);
	        
	        camera_.setPreviewCallback(cb);
	        Log.d("myLogs","setupCamera with arg");
	    }

	    public void setupCamera() {
	    	
	        camera_ = Camera.open();
	        procSize_ = camera_.new Size(0, 0);
	        Camera.Parameters p = camera_.getParameters();        
	       
	        supportedSizes = p.getSupportedPreviewSizes();
	        procSize_ = supportedSizes.get( supportedSizes.size()/2 );
	        p.setPreviewSize(procSize_.width, procSize_.height);
	        
	        camera_.setParameters(p);
	       // camera_.setDisplayOrientation(90);
	        try {
	            camera_.setPreviewDisplay(surfaceHolder_);
	        } catch ( Exception ex) {
	            ex.printStackTrace(); 
	        }
	        camera_.startPreview();  
	        Log.d("myLogs","setupCamera");
	        if ( cameraReadyCb_ != null)
	            cameraReadyCb_.onCameraReady();
	    }  
	    
	    private Camera.AutoFocusCallback afcb = new Camera.AutoFocusCallback() {
	        @Override
	        public void onAutoFocus(boolean success, Camera camera) {
	        }
	    };

	  /*  @Override
	    public void surfaceChanged(SurfaceHolder sh, int format, int w, int h){
	    	 Log.d("myLogs","surfaceChanged surf");
	    }
	    
		@Override
	    public void surfaceCreated(SurfaceHolder sh){ 
	        setupCamera();  
	        Log.d("myLogs","surfaceCreated cam");
	        
	        if ( cameraReadyCb_ != null)
	            cameraReadyCb_.onCameraReady();
	    }
	    
		@Override
	    public void surfaceDestroyed(SurfaceHolder sh){
			 Log.d("myLogs","surfaceDestroyed cam");
	        Release();
	    }*/
	}