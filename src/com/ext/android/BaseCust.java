package com.ext.android;

import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.MotionEvent;

public abstract class BaseCust  {
	private float x,y,width,heigth;
	private String name;
	private Rect rect;
	
	public BaseCust(float x, float y, float heigth, float width, String name) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.heigth = heigth;
		this.name = name;
	}
	
	public abstract void Draw(Canvas canvas);
	public abstract void Touch(MotionEvent point);

	public float getX() {
		return x;
	}

	public void setX(float x) {
		this.x = x;
	}

	public float getY() {
		return y;
	}

	public void setY(float y) {
		this.y = y;
	}

	public float getWidth() {
		return width;
	}

	public void setWidth(float width) {
		this.width = width;
	}

	public float getHeigth() {
		return heigth;
	}

	public void setHeigth(float heigth) {
		this.heigth = heigth;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Rect getRect() {
		return rect;
	}

	public void setRect(Rect rect) {
		this.rect = rect;
	}


	
		
		
	
	
	
	

}
