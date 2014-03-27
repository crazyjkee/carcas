package com.camera.andraft;

import java.util.ArrayList;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.MotionEvent;

import com.ext.android.BaseCust;

public class MyCustomView extends BaseCust {
	private enum sostoyanie {
		shared, start, privat
	}
	private Bitmap bm;
	private Rect rect;
	private boolean down = false;
	private boolean start = false;
	private int k = 1;
	private ArrayList<Rect> arRect = new ArrayList();
	private sostoyanie sost = sostoyanie.shared;

	public MyCustomView(float x, float y, float heigth, float width, String name) {
		super(x, y, heigth, width, name);

		rect = new Rect((int) x, (int) y, (int) heigth, (int) width);
		arRect.add(rect);
	}

	public void Draw(Canvas canvas) {
		Paint p = new Paint();
		Paint pt = new Paint();
		switch (sost) {
		case shared:
			p.setShader(new LinearGradient(0, 0, 0, 15,
					Color.parseColor("#759d0a"), Color.parseColor("#90a94c"),
					Shader.TileMode.MIRROR));
			
			pt.setAntiAlias(true);
			pt.setColor(Color.WHITE);
			pt.setTextAlign(Align.CENTER);
			pt.setTextSize(30.0f);
			pt.setStrokeWidth(1.0f);
			pt.setStyle(Paint.Style.STROKE);
			//canvas.drawRect(getX(), getY(), getHeigth(), getWidth(), p);
			canvas.drawRoundRect(new RectF(rect), 20, 20, p);
			canvas.drawText(getName(), rect.centerX(), rect.centerY() + 10, pt);
			break;
		case privat:
			p.setColor(Color.BLUE);
			p.setShader(new LinearGradient(0, 0, 0, 15,
					Color.parseColor("#b51212"), Color.parseColor("#bb7f7f"),
					Shader.TileMode.MIRROR));
			pt.setAntiAlias(true);
			pt.setColor(Color.WHITE);
			pt.setTextAlign(Align.CENTER);
			pt.setTextSize(30.0f);
			pt.setStrokeWidth(1.0f);
			pt.setStyle(Paint.Style.STROKE);
			// pt.setShadowLayer(5.0f, 10.0f, 10.0f, Color.BLACK);
			//canvas.drawRect(getX(), getY(), getHeigth(), getWidth(), p);
			canvas.drawRoundRect(new RectF(rect), 20, 20, p);
			canvas.drawText(getName(), rect.centerX(), rect.centerY() + 10, pt);
			break;
		case start:			
			Log.d("myLogs","start");
			break;
		default:
			break;
		}
		
		
	}

	public Bitmap getBm() {
		return bm;
	}

	public void setBm(Bitmap bm) {
		this.bm = bm;
	}

	public boolean isDown() {
		return down;
	}

	@Override
	public void Touch(MotionEvent point) {
		if (point.getAction() == MotionEvent.ACTION_DOWN
				&& getName().equals("START")) {
			if (rect.contains((int) point.getX(), (int) point.getY())) {
				sost = sostoyanie.start;
				Log.d("myLogs", "touch START");
			}
		}
		if (point.getAction() == MotionEvent.ACTION_DOWN
				&& (getName().equals("SHARED")||getName().equals("PRIVATE"))) {
			if (rect.contains((int) point.getX(), (int) point.getY())) {
				k++;
				if (k % 2 == 0) {
					this.setName("PRIVATE");
					sost =sostoyanie.privat;
				} else {
					this.setName("SHARED");
					sost = sostoyanie.shared;
				}
				Log.d("myLogs", "touch SHARED DOWN: " + down);
			}
		}

	}

	
}


