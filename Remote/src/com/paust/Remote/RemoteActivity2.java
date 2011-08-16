package com.paust.Remote;

import android.os.Bundle;
import android.util.Log;

public class RemoteActivity2 extends RemoteActivityAll {
	// Debugging
	private static final String TAG = "RemoteActivity2";
	private static final boolean D = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		if (D)
			Log.d(TAG, "onCreate() ");
		setContentView(R.layout.remote2);

	}

}
