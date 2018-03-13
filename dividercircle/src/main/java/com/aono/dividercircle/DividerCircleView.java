package com.aono.dividercircle;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Aono on 2018/3/12.
 */

public class DividerCircleView extends View {

	private int dividers;
	private int defaultSize = 100;

	public DividerCircleView(Context context) {
		super(context);
		initView(context, null);
	}

	public DividerCircleView(Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
		initView(context, attrs);
	}


	public DividerCircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
		initView(context, attrs);
	}

	private void initView(Context context, AttributeSet attrs) {
		if (attrs != null){
			TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DividerCircleView);
			try {
				dividers = typedArray.getInteger(R.styleable.DividerCircleView_dividers, 1);
			}finally {
				typedArray.recycle();
			}
		} else {
			dividers = 1;
		}
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		super.onSizeChanged(w, h, oldw, oldh);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		int mySize;
		int mode = MeasureSpec.getMode(widthMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		if (mode == MeasureSpec.UNSPECIFIED){
			mySize = defaultSize;
		}else {
			mySize = width > height ? width : height;
		}
		setMeasuredDimension(mySize, mySize);
	}


	@Override
	protected void onDraw(Canvas canvas) {
		int r = getMeasuredHeight() / 2;
		int centerX = getLeft() + r;
		int centerY = getTop() + r;
		Paint paint = new Paint();
		paint.setColor(Color.DKGRAY);
		canvas.drawCircle(centerX, centerY, r, paint);
//		drawText(canvas, "888888", centerX, centerY, paint, 0f);

		RectF rectF = new RectF(getLeft(),getTop(),getRight(),getBottom());
		paint.setColor(Color.RED);
		canvas.drawArc(rectF, 0, 45,true , paint);
	}

	void drawText(Canvas canvas ,String text , float x ,float y, Paint paint ,float angle){
		if(angle != 0){
			canvas.rotate(angle, x, y);
		}
		paint.setColor(Color.YELLOW);
		paint.setTextSize(200);
		canvas.drawText(text, 0, 6, x, y, paint);
		if(angle != 0){
			canvas.rotate(-angle, x, y);
		}
	}
}
