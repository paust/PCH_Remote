package com.paust.Remote;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class TextActivity extends AllActivity {
	// Debugging
	private static final String TAG = "TextActivity";
	private static final boolean D = true;

	/** Called when the activity is first created. */
	private TextView textview;

	@Override
		protected void updateText() {
			// TODO Auto-generated method stub
			textview.setText(mTS.getText());
		}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (D)
			Log.d(TAG, "onCreate() ");
		setContentView(R.layout.text);
		textview = (TextView) findViewById(R.id.tv2);
	}

}
