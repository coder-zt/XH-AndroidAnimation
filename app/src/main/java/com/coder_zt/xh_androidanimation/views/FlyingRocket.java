package com.coder_zt.xh_androidanimation.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Shader;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.coder_zt.xh_androidanimation.R;
import com.coder_zt.xh_androidanimation.utils.UIHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RequiresApi(api = Build.VERSION_CODES.O)
public class FlyingRocket extends View {
    private static final String TAG = "FlyingRocket";
    private Bitmap mRocketBitmap;
    private Rect mSrcRect;
    private Rect mDstRect;
    private Rect mRocketWake;
    private Paint mRocketWakePaint;
    private int offset = 0;
    private int maxOffset = UIHelper.dp2px(1.5f);
    private int dir = 1;
    private List<StarTrails> lines = new ArrayList<>();
    private int startColor = Color.parseColor("#0099FF");
    private int endColor = Color.parseColor("#0000ff90");
    private Paint mLinePaint;
    private Paint mLinePain;

    public FlyingRocket(Context context) {
        this(context, null);
    }

    public FlyingRocket(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlyingRocket(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mRocketBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.app_rcoket_img);
        Log.d(TAG, "FlyingRocket: " + mRocketBitmap.getWidth());
        Log.d(TAG, "FlyingRocket: " + mRocketBitmap.getHeight());
        int edgeSize = 55;
        mSrcRect = new Rect(0,0, 235, 235);
        mDstRect = new Rect(0,0, UIHelper.dp2px(edgeSize),UIHelper.dp2px(edgeSize));
        mRocketWake = new Rect(UIHelper.dp2px(edgeSize * 3/8), UIHelper.dp2px(edgeSize),UIHelper.dp2px(edgeSize * 5/8),UIHelper.dp2px(155));
        mRocketWakePaint = new Paint();
        LinearGradient paintGradient = new LinearGradient(0,0,UIHelper.dp2px(edgeSize),UIHelper.dp2px(150), startColor, endColor, Shader.TileMode.CLAMP);
        mRocketWakePaint.setShader(paintGradient);
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(Color.WHITE);
        mLinePaint.setStrokeWidth(5);
        for (int i = 0; i < 30; i++) {
            lines.add(new StarTrails());
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d(TAG, "onTouchEvent: " + event.getAction() + "    " + MotionEvent.ACTION_UP);
//        if (event.getAction() == MotionEvent.ACTION_UP) {
            Log.d(TAG, "onTouchEvent: ");
            dir = -1;
            maxOffset = Integer.MAX_VALUE;
//            return false;
//        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (StarTrails s : lines) {
            s.draw(canvas, getMeasuredHeight());
        }
        canvas.translate(getMeasuredWidth()/2 - mDstRect.width()/2,getMeasuredHeight()/2 - mDstRect.height()/2);
        if(Math.abs(offset) > maxOffset){
            dir = -dir;
        }
        offset += dir * 1;
        mDstRect.top += offset;
        mDstRect.bottom += offset;
        mRocketWake.top += offset;
        mRocketWake.bottom += offset;
        canvas.drawBitmap(mRocketBitmap, mSrcRect, mDstRect, null);
        canvas.drawRect(mRocketWake, mRocketWakePaint);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                invalidate();
            }
        }, 50);
    }

    private class StarTrails{
        private Point startPoint;
        private int len;

        public StarTrails(){
            startPoint = new Point(Math.abs(new Random().nextInt())%1080, Math.abs(new Random().nextInt())%2000);
            len = Math.abs(new Random().nextInt())%260;
        }
        public void draw(Canvas canvas, int maxHeight){
            canvas.drawLine( startPoint.x, startPoint.y,startPoint.x, startPoint.y + len, mLinePaint);
            startPoint.y += 50;
            if(startPoint.y > maxHeight){
                startPoint = new Point(Math.abs(new Random().nextInt())%getMeasuredWidth(), 0);
                len = Math.abs(new Random().nextInt())%300;
            }
        }
    }

}
