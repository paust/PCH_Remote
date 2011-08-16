package com.paust.Remote;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TelnetActivity extends AllActivity// implements TelnetConstants
{
	// Debugging
	private static final String TAG = "TelNetActivity";
	private static final boolean D = true;

	/** Called when the activity is first created. */
	private EditText mEditText;
	private TextView mTv1;
	private ListView mLv;
	private ArrayAdapter<String> mLa;
	private Button mButton;
	private Button mButton2;
	private ArrayList<String> mAlist;
	//private final String lscmd = "ls -1 -F";
	
	@Override
	protected void updateText() {
		if (D)
			Log.d(TAG, "updateText() ");

		mTv1.setText(mTS.getText());
	}

	@Override
	protected void updateList() {
		if (D)
			Log.d(TAG, "updateList() ");

		String[] a = mTS.getText2().split(TelNetService.CR);
		mAlist.clear();
		mAlist.add("..");
		for (int i = 0; i < (a.length - 1); i++) {
			if (!a[i].startsWith(TelNetService.lscmd))
				mAlist.add(a[i]);
		}
		mAlist.add(mTS.getVerzeichnis());
		mLa.notifyDataSetChanged();
		Toast.makeText(getApplicationContext(), "drinn en", Toast.LENGTH_SHORT)
				.show();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (D)
			Log.d(TAG, "onCreate() ");
		setContentView(R.layout.main);
		mEditText = (EditText) findViewById(R.id.editText1);
		mTv1 = (TextView) findViewById(R.id.tv1);
		mLv = (ListView) findViewById(R.id.ListView01);
		mLv.setTextFilterEnabled(true);
		mLv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				String command = "";
				String filename;
				String fullfilename;
				String verzeichnis ;
				filename = new String(mAlist.get(arg2));
				final String promt = " # ";
				verzeichnis = new String(mTS.getVerzeichnis().replaceFirst(promt, ""));
				fullfilename = mTS.getVerzeichnis().replaceFirst(promt, "")
						+ "/" + filename;
			if (filename.endsWith("VIDEO_TS/") ) {
				command = "busybox26 sh /opt/sybhttpd/localhost.drives/HARD_DISK/a_start_dvd.sh" + verzeichnis
						+ TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
			} else if (filename.endsWith("/")) {
					filename = filename.replaceFirst("/", "");
					command = "cd \"" + filename + "\"" + TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				} else if (filename.endsWith("@")) {
					filename = filename.replaceFirst("@", "");
					command = "cd \"" + filename + "\"" + TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				} else if (filename.endsWith(".iso")) {
					command = "busybox26 sh /opt/sybhttpd/localhost.drives/HARD_DISK/a_start_iso.sh" + fullfilename
							+ TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				} else if (filename.endsWith(".avi") || filename.endsWith(".mpg") || filename.endsWith(".mpeg")) {
					command = "busybox26 sh /opt/sybhttpd/localhost.drives/HARD_DISK/a_start_video.sh" + fullfilename
							+ TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				} else if (filename.endsWith(".mp3") ) {
					command = "busybox26 sh /opt/sybhttpd/localhost.drives/HARD_DISK/a_start_mp3.sh" + fullfilename
							+ TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				} else

					command = "cd \"" + filename + "\"" + TelNetService.CR + TelNetService.lscmd + TelNetService.CR;
				// blub = "cd \"" + mAlist.get(arg2)+ "\TelNetService.CR + lcmd +"TelNetService.CR;
				// +"ls -1\r\n";
				// mEditText.setText(blub);
				mTS.write(command);
				Toast.makeText(getApplicationContext(), command + fullfilename,
						Toast.LENGTH_SHORT).show();
			}
		});
		mAlist = new ArrayList<String>();
		mAlist.add("..");
		// mAlist.add("seppl");
		// String [] a = new String [] {"hugo", "seppl"};
		mLa = new ArrayAdapter<String>(this, R.layout.filelist_row,
				R.id.filelist_row_TextView01, mAlist);
		mLv.setAdapter(mLa);

		mTS.addText("Start");
		updateText();
		mEditText.setText("ls -l");
		mButton2 = (Button) findViewById(R.id.button2);
		mButton2.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {

				updateText();
				updateList();
				mTS.setText("");
			} // onclick
		}); // onclicklistener

		mButton = (Button) findViewById(R.id.button1);
		mButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				mTS.addText("\nbutton");
				updateText();
				mTS.write(mEditText.getText() + TelNetService.CR);

				// Perform action on click
			} // onclick
		}); // onclicklistener

	} // onCreate

} // TestSMSActicvity