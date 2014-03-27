package com.draw.android;

import java.util.HashSet;
import java.util.Set;

import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;

import com.camera.andraft.MyCustomView;
import com.ext.android.BaseCust;

public class DrawThread extends Thread{
    private boolean runFlag = false;
    private SurfaceHolder surfaceHolder;
    private int k = 2;
	private float scale;
	private int button_height = 15;
	private int button_width = 50;
    public Set<MyCustomView> mycust = new HashSet<MyCustomView>();
	private int y;
	private int x;
	private int heigth;
	private int width;
    
    
    
    public DrawThread(SurfaceHolder surfaceHolder,DisplayMetrics dm){
        this.surfaceHolder = surfaceHolder; 
        this.surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_HARDWARE);
        x=(dm.widthPixels/2)-button_width*2;
        y=(dm.heightPixels / 2) - button_height * 2;
        heigth = (dm.widthPixels + button_width * 2 * k) / 2;
        width = (dm.heightPixels + button_height * 2 * k) / 2;
        
        mycust.add(new MyCustomView(x,y,heigth,width,"SHARED")); //float left, float top, float right, float bottom
        mycust.add(new MyCustomView(x,width+(width-y)/2,heigth,width+(width-y)/2+(width-y),"START"));                  //x,y, heigth, width
				/*mycust.get(0).getWidth()+(mycust.get(0).getWidth()-mycust.get(0).getY())/2,
				mycust.get(0).getHeigth(),
				mycust.get(0).getWidth()+(mycust.get(0).getWidth()-mycust.get(0).getY())/2+(mycust.get(0).getWidth()-mycust.get(0).getY()), "START"));*/
	    
        // загружаем картинку, которую будем отрисовывать
        
    }

    public void setRunning(boolean run) {
        runFlag = run;
    }

    @Override
    public void run() {
        Canvas canvas;
        while (runFlag) {
            canvas = null;
            try {
                // получаем объект Canvas и выполняем отрисовку
                canvas = surfaceHolder.lockCanvas();
                synchronized (surfaceHolder) {
                    for(BaseCust m: mycust){
                    		m.Draw(canvas);
                    	}
                    }          
                    
                } 
            finally {
                if (canvas != null) {
                    // отрисовка выполнена. выводим результат на экран
                    surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }
}