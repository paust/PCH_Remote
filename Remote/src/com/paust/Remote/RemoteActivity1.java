package com.paust.Remote;

import android.os.Bundle;
import android.util.Log;

public class RemoteActivity1 extends RemoteActivityAll {
	// Debugging
	private static final String TAG = "RemoteActivity";
	private static final boolean D = true;

	/** Called when the activity is first created. */


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.d(TAG, "onCreate() ");
		setContentView(R.layout.remote1);

	}


//	public void buttonListener(View v) {
//		String txt = "";
//		switch (v.getId()) {
//		case R.id.br_power:
//			txt = getString(R.string.btc_power);
//			break;
//		case R.id.br_setup:
//			txt = getString(R.string.btc_setup);
//			break;
//		case R.id.br_pgup:
//			txt = getString(R.string.btc_pgup);
//			break;
//		case R.id.br_pgdn:
//			txt = getString(R.string.btc_pgdn);
//			break;
//		case R.id.br_eject:
//			txt = getString(R.string.btc_eject);
//			break;
//		case R.id.br_tvmode:
//			txt = getString(R.string.btc_tvmode);
//			break;
//		case R.id.br_mute:
//			txt = getString(R.string.btc_mute);
//			break;
//		case R.id.br_1:
//			txt = getString(R.string.btc_1);
//			break;
//		case R.id.br_2:
//			txt = getString(R.string.btc_2);
//			break;
//		case R.id.br_3:
//			txt = getString(R.string.btc_3);
//			break;
//		case R.id.br_4:
//			txt = getString(R.string.btc_4);
//			break;
//		case R.id.br_5:
//			txt = getString(R.string.btc_5);
//			break;
//		case R.id.br_6:
//			txt = getString(R.string.btc_6);
//			break;
//		case R.id.br_7:
//			txt = getString(R.string.btc_7);
//			break;
//		case R.id.br_8:
//			txt = getString(R.string.btc_8);
//			break;
//		case R.id.br_9:
//			txt = getString(R.string.btc_9);
//			break;
//		case R.id.br_0:
//			txt = getString(R.string.btc_0);
//			break;
//		case R.id.br_delete:
//			txt = getString(R.string.btc_delete);
//			break;
//		case R.id.br_caps:
//			txt = getString(R.string.btc_caps);
//			break;
//		case R.id.br_up:
//			txt = getString(R.string.btc_up);
//			break;
//		case R.id.br_down:
//			txt = getString(R.string.btc_down);
//			break;
//		case R.id.br_left:
//			txt = getString(R.string.btc_left);
//			break;
//		case R.id.br_right:
//			txt = getString(R.string.btc_right);
//			break;
//		case R.id.br_home:
//			txt = getString(R.string.btc_home);
//			break;
//		case R.id.br_return:
//			txt = getString(R.string.btc_return);
//			break;
//		case R.id.br_source:
//			txt = getString(R.string.btc_source);
//			break;
//		case R.id.br_info:
//			txt = getString(R.string.btc_info);
//			break;
//		case R.id.br_ok:
//			txt = getString(R.string.btc_ok);
//			break;
//		case R.id.br_menu:
//			txt = getString(R.string.btc_menu);
//			break;
//		case R.id.br_prev:
//			txt = getString(R.string.btc_prev);
//			break;
//		case R.id.br_play:
//			txt = getString(R.string.btc_play);
//			break;
//		case R.id.br_next:
//			txt = getString(R.string.btc_next);
//			break;
//		case R.id.br_title:
//			txt = getString(R.string.btc_title);
//			break;
//		case R.id.br_rev:
//			txt = getString(R.string.btc_rev);
//			break;
//		case R.id.br_stop:
//			txt = getString(R.string.btc_stop);
//			break;
//		case R.id.br_fwd:
//			txt = getString(R.string.btc_fwd);
//			break;
//		case R.id.br_repeat:
//			txt = getString(R.string.btc_repeat);
//			break;
//		case R.id.br_angle:
//			txt = getString(R.string.btc_angle);
//			break;
//		case R.id.br_pause:
//			txt = getString(R.string.btc_pause);
//			break;
//		case R.id.br_slow:
//			txt = getString(R.string.btc_slow);
//			break;
//		case R.id.br_seek:
//			txt = getString(R.string.btc_seek);
//			break;
//		case R.id.br_audio:
//			txt = getString(R.string.btc_audio);
//			break;
//		case R.id.br_subtitle:
//			txt = getString(R.string.btc_subtitle);
//			break;
//		case R.id.br_zoom:
//			txt = getString(R.string.btc_zoom);
//			break;
//		case R.id.br_red:
//			txt = getString(R.string.btc_red);
//			break;
//		case R.id.br_green:
//			txt = getString(R.string.btc_green);
//			break;
//		case R.id.br_yellow:
//			txt = getString(R.string.btc_yellow);
//			break;
//		case R.id.br_blue:
//			txt = getString(R.string.btc_blue);
//			break;
//		}
//		Toast toast = Toast.makeText(this, txt, Toast.LENGTH_SHORT);
//		toast.show();
//		mTS.write("echo " + txt + " > /tmp/irkey" + "\r\n" +  TelNetService.lscmd + "\r\n" );
//	}
}
