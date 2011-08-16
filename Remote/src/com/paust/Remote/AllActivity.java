package com.paust.Remote;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.Toast;

import com.paust.Remote.SimpleGestureFilter.SimpleGestureListener;

public class AllActivity extends Activity implements SimpleGestureListener {
	// Debugging
	private static final String TAG = "AllActivity";
	private static final boolean D = true;
	protected SimpleGestureFilter detector;
	protected TelNetService mTS = null;
	protected Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (D)
				Log.d(TAG, "handleMessage " + "what" + msg.what + "arg1"
						+ msg.arg1 + "arg2" + msg.arg2);

			switch (msg.what) {
			case TelNetService.MESSAGE_UPDATETEXT:
				updateText();
				break;
			case TelNetService.MESSAGE_UPDATELIST:
				updateList();
				break;
			case TelNetService.MESSAGE_UPDATEALL:
				updateText();
				updateList();
			}
			// mLv.
			// mLv.;
			// mMainLinearLayout.invalidate();
		}

	};

	protected void updateText() {
		// TODO Auto-generated method stub

	}

	protected void updateList() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.d(TAG, "onCreate() ");
		mTS = TelNetService.getInstance();
		detector = new SimpleGestureFilter(this, this);
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent me) {
		this.detector.onTouchEvent(me);
		return super.dispatchTouchEvent(me);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		// return super.onCreateOptionsMenu(menu);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_start_stop, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		switch (item.getItemId()) {
		case R.id.menui_start:
			mTS.start();
			Toast.makeText(getApplicationContext(),
					"Verbindungsversuch gestartet", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.menui_stop:
			mTS.stop();
			Toast.makeText(getApplicationContext(), "Verbindung gestoppt",
					Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		if (D)
			Log.d(TAG, "onStop() ");
		mTS.stop();
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (D)
			Log.d(TAG, "onPause() ");

	}

	@Override
	protected void onResume() {
		super.onResume();
		if (D)
			Log.d(TAG, "onResume() ");
		mTS.setmHandler(mHandler);
		mTS.setContext(getApplicationContext());
		mTS.start();
		// weiter mit inputstream

	} // onResume

	@Override
	public void onSwipe(int direction) {
		String str = "";
		TabWidget1 tw1;
		tw1 = (TabWidget1) this.getParent();
		switch (direction) {

		case SimpleGestureFilter.SWIPE_RIGHT:
			str = "Swipe Right";
			tw1.nexttab();
			break;
		case SimpleGestureFilter.SWIPE_LEFT:
			str = "Swipe Left";
			tw1.prevtab();
			break;
		case SimpleGestureFilter.SWIPE_DOWN:
			str = "Swipe Down";
			break;
		case SimpleGestureFilter.SWIPE_UP:
			str = "Swipe Up";
			break;

		}
		Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDoubleTap() {
		Toast.makeText(this, "Double Tap", Toast.LENGTH_SHORT).show();
	}

}
