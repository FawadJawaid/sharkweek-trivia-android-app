package com.tenpearls.sharkweektrivia;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class ImagecreditsActivity extends FragmentActivity {

	ImageButton crossmenuBtn;
	private GestureDetector gestureDetector;
	TextView heading, content;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
		setContentView(R.layout.activity_imagecredits);
		crossmenuBtn = (ImageButton) findViewById(R.id.activity_main_content_button_menu3);

		gestureDetector = new GestureDetector(new SwipeGestureDetector());

		crossmenuBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				finish();
				overridePendingTransition(R.anim.slide_in_left,
						R.anim.slide_out_right);
			}
		});

		settingFonts();
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (gestureDetector.onTouchEvent(event)) {
			return true;
		}
		return super.onTouchEvent(event);
	}

	private void onLeftSwipe() {
		// Do something
	}

	private void onRightSwipe() {
		// Do something
		finish();
		overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
	}

	// Private class for gestures
	private class SwipeGestureDetector extends SimpleOnGestureListener {
		// Swipe properties, you can change it to make the swipe
		// longer or shorter and speed
		private static final int SWIPE_MIN_DISTANCE = 120;
		private static final int SWIPE_MAX_OFF_PATH = 200;
		private static final int SWIPE_THRESHOLD_VELOCITY = 200;

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			try {
				float diffAbs = Math.abs(e1.getY() - e2.getY());
				float diff = e1.getX() - e2.getX();

				if (diffAbs > SWIPE_MAX_OFF_PATH)
					return false;

				// Left swipe
				if (diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					ImagecreditsActivity.this.onLeftSwipe();

					// Right swipe
				} else if (-diff > SWIPE_MIN_DISTANCE
						&& Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
					ImagecreditsActivity.this.onRightSwipe();
				}
			} catch (Exception e) {
				Log.e("ImagecreditsActivity", "Error on gestures");
			}
			return false;
		}
	}

	void settingFonts() {
		heading = (TextView) findViewById(R.id.imagecreditsHeader);
		content = (TextView) findViewById(R.id.imagecreditsContent);

		Typeface typeFace =Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/MondaRegular.ttf");
		Typeface typeFaceBold =Typeface.createFromAsset(getApplicationContext().getAssets(),"fonts/MondaBold.ttf");
		content.setTypeface(typeFace);
		heading.setTypeface(typeFaceBold);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.imagecredits, menu);
		return true;
	}

}
