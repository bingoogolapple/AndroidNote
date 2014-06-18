package com.bingoogol.multouch;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private static final String TAG = "MainActivity";
	private FrameLayout root;
	private ImageView iv_icon;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		root = (FrameLayout) findViewById(R.id.container);
		iv_icon = (ImageView) findViewById(R.id.iv_icon);
		root.setOnTouchListener(new OnTouchListener() {

			double currentDistance;
			double lastDistance = -1;

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				switch (event.getAction()) {
				case MotionEvent.ACTION_DOWN:
					Log.i(TAG, "ACTION_DOWN");
					break;
				case MotionEvent.ACTION_MOVE:
					// LayoutParams lp = (LayoutParams) iv_icon.getLayoutParams();
					// lp.leftMargin = (int) event.getX();
					// lp.topMargin = (int) event.getY();
					// iv_icon.setLayoutParams(lp);
					// Log.i(TAG, "ACTION_MOVE  " + String.format("x:%f,y:%f", event.getX(), event.getY()));
					if (event.getPointerCount() == 2) {

						float offsetX = event.getX(0) - event.getX(1);
						float offsetY = event.getY(0) - event.getY(1);
						currentDistance = Math.sqrt(Math.pow(offsetX, 2) + Math.pow(offsetY, 2));
						if (lastDistance < 0) {
							lastDistance = currentDistance;
						} else {
							if (currentDistance - lastDistance > 5) {
								lastDistance = currentDistance;
								Log.i(TAG, "ACTION_MOVIE  放大");

								LayoutParams lp = (LayoutParams) iv_icon.getLayoutParams();
								lp.width = (int) (1.1 * iv_icon.getWidth());
								lp.height = (int) (1.1 * iv_icon.getHeight());
								iv_icon.setLayoutParams(lp);
							} else if (lastDistance - currentDistance > 5) {
								lastDistance = currentDistance;
								Log.i(TAG, "ACTION_MOVIE  缩小");
								LayoutParams lp = (LayoutParams) iv_icon.getLayoutParams();
								lp.width = (int) (0.9 * iv_icon.getWidth());
								lp.height = (int) (0.9 * iv_icon.getHeight());
								iv_icon.setLayoutParams(lp);
							}
						}
					}

					break;
				case MotionEvent.ACTION_UP:
					Log.i(TAG, "ACTION_UP");
					break;
				}

				// 三个阶段有依赖关系，如果没有指定第一个阶段触发成功，则后续事件不会触发
				return true;
			}
		});
	}
}
