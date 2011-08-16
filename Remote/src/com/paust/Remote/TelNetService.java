package com.paust.Remote;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.util.Log;

import com.paust.Telnet.DefaultTelnetTerminalHandler;
import com.paust.Telnet.TelnetConstants;
import com.paust.Telnet.TelnetURLConnection;

public class TelNetService {
	// Debugging
	private static final String TAG = "TelNetService";
	private static final boolean D = true;

	public static final int MESSAGE_UPDATEALL = 1;
	public static final int MESSAGE_UPDATELIST = 2;
	public static final int MESSAGE_UPDATETEXT = 3;
	public static final int MESSAGE_UPDATESTATUS = 3;

	public static final int STATE_NONE = 0; // we're doing nothing
	public static final int STATE_OPEN = 1; // now listening for incoming
											// connections
	public static final int STATE_CONNECTING = 2; // now initiating an outgoing
													// connection
	public static final int STATE_CONNECTED = 3; // now connected to a remote
													// device

	private Context mContext;

	// private TelnetOutputStream mTout;
	private OutputStream mOut;
	private URLConnection mUrlConnection;
	static volatile boolean closed = false;
	public static final String lscmd = "ls -1 -F";
	public static final String CR = "\r\n";

	private volatile String text = "";
	private volatile String text2 = "";
	private volatile String verzeichnis = "";

	private volatile int mStatus = 0;
	private Handler mHandler;

	private static TelNetService instance = null;

	private TelNetService() {
		if (D)
			Log.d(TAG, "TelNetService() ");

		// exists only to defeat instatiation.
	}

	public static TelNetService getInstance() {
		if (D)
			Log.d(TAG, "getInstance()");

		if (instance == null) {
			instance = new TelNetService();

		}
		return instance;
	}

	public void setmHandler(Handler mHandler) {
		if (D)
			Log.d(TAG, "setmHandler) ");

		this.mHandler = mHandler;
	}

	public void start() {
		if (D)
			Log.d(TAG, "start() ");

		text = text + "onResume";
		mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 1).sendToTarget();
		// mTv1.setText(text);

		(new Thread(new Runnable() {
			public void run() {
				try {
					closed = false;

					if (mStatus == STATE_CONNECTED) {
						mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 2)
								.sendToTarget();
						return;
					}
					if (mStatus != STATE_NONE) {
						text = text + " bin grad beim verbinden - kein erneuter versuch " ;
						mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 2)
								.sendToTarget();
						return;
					}
					WifiManager wifi = (WifiManager) mContext
							.getSystemService(Context.WIFI_SERVICE);
					if (wifi.getWifiState() != WifiManager.WIFI_STATE_ENABLED) {
						text = text + " WIFI not enabled";
						mHandler.obtainMessage(MESSAGE_UPDATESTATUS, 1, 1)
								.sendToTarget();
					} else {
						ConnectivityManager cm = (ConnectivityManager) mContext
								.getSystemService(Context.CONNECTIVITY_SERVICE);
						NetworkInfo networkInfo = null;
						if (cm != null) {
							networkInfo = cm
									.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
						}
						if (networkInfo == null)
							text = text + " WIFI enabled but not available";
						else
							text = text + " WIFI is "
									+ networkInfo.isConnected();
					}
					String host = "192.168.178.26";
					int port = 23;
					URL url = new URL("telnet", host, port, "",
							new com.paust.Telnet.URLStreamHandler());
					mStatus = STATE_OPEN;
					text = text + " OPEN";
					mHandler.obtainMessage(MESSAGE_UPDATESTATUS, 1, 1)
							.sendToTarget();
					mUrlConnection = url.openConnection();
					mStatus = STATE_CONNECTING;
					text = text + " CONNECTING";
					mHandler.obtainMessage(MESSAGE_UPDATESTATUS, 1, 1)
							.sendToTarget();
					mUrlConnection.connect();
					if (mUrlConnection instanceof TelnetURLConnection) {
						((TelnetURLConnection) mUrlConnection)
								.setTelnetTerminalHandler(new SimpleTelnetTerminalHandler());
					}
					mOut = mUrlConnection.getOutputStream();
					String hugo = lscmd + "\r\n";
					mOut.write(hugo.getBytes());
					final InputStream in = mUrlConnection.getInputStream();
					mStatus = STATE_CONNECTED;
					text = text + " CONNECTED";
					mHandler.obtainMessage(MESSAGE_UPDATESTATUS, 1, 1)
							.sendToTarget();

					int ch;
					boolean bloeschen = false;
					final String promt = " # ";
					// text = text + "\nthread\n";
					// mHandler.sendMessage(mHandler.obtainMessage());
					do {
						if (closed) {
							text = text + "abgebrochen";
							mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 2)
									.sendToTarget();
							break;
						}
						Integer chi;
						ch = in.read();
						if (ch < 0)
							return;
						chi = ch;
						if (bloeschen == true) {
							bloeschen = false;
							text = text + " löschen ";
							text2 = "";
						}
						text = text + (char) ch;// +"_"+chi.toString() + "_";
						text2 = text2 + (char) ch;
						verzeichnis = verzeichnis + (char) ch; // verzeichnis
																// wird im
																// teminalhandler
																// resetet bei
																// FF und CR
						if (text2.length() >= promt.length()
								&& text2.substring(
										text2.length() - promt.length(),
										text2.length()).compareTo(promt) == 0) {
							bloeschen = true;
							text = text + " gefunden ";
							mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 2)
									.sendToTarget();
						}
						// ch = chi;

					} while (!closed);
					text = text + "und aus";
					mHandler.obtainMessage(MESSAGE_UPDATEALL, 1, 2)
							.sendToTarget();

				} catch (UnknownHostException e) {
					text = text + "ERROR host nicht bekannt";
					closed = true;
					text = text + "closed on ex1";
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3)
							.sendToTarget();
					mStatus = STATE_NONE;
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 2)
							.sendToTarget();
				} catch (IOException e) {
					e.printStackTrace();
					text = text + e.toString();
					closed = true;
					text = text + "closed on ex2";
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3)
							.sendToTarget();
					mStatus = STATE_NONE;
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3)
							.sendToTarget();

				} catch (Exception e) {
					text = text + e.toString();
					closed = true;
					text = text + "closed on ex3";
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3)
							.sendToTarget();
					mStatus = STATE_NONE;
					mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3)
							.sendToTarget();
					mStatus = STATE_NONE;
				} // Catch
			} // run
		})).start(); // runable.start
		// mTout = (TelnetOutputStream) mOut;
		// mTout.sendcmd((byte)DONT, (byte)OUTMRK);
		// String hugo = "ls -1 -F\r\n";
		// try {
		// } catch (Exception e) {
		// if (!closed)
		// e.printStackTrace();
		// } // Catch
		//
	}

	public void stop() {
		if (D)
			Log.d(TAG, "stop() ");

		closed = true;
		text = text + "closed on stop";
		mHandler.obtainMessage(MESSAGE_UPDATETEXT, 1, 3).sendToTarget();
		mStatus = STATE_NONE;
		try {
			if (mUrlConnection != null)
				((TelnetURLConnection) mUrlConnection).disconnect();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public String getText() {
		return text;
	}

	public String getText2() {
		return text2;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setText2(String text) {
		this.text2 = text;
	}

	public void addText(String textx) {
		this.text = this.text + textx;
	}

	public void addText2(String textx) {
		this.text2 = this.text2 + textx;
	}

	public String getVerzeichnis() {
		return verzeichnis;
	}

	public void setVerzeichnis(String verzeichnis) {
		this.verzeichnis = verzeichnis;
	}

	public void write(String a) {
		if (D)
			Log.d(TAG, "write() " + a);
		if (mOut == null) {
			text = text + "kann nicht schreiben!";
			mHandler.obtainMessage(MESSAGE_UPDATETEXT, 2, 4).sendToTarget();
			return;
		}
		try {
			mOut.write(a.getBytes());
			text = text + a;
			mHandler.obtainMessage(MESSAGE_UPDATETEXT, 2, 4).sendToTarget();
		} catch (IOException e) {
			e.printStackTrace();
			mStatus = STATE_NONE;
			text = text + e.toString();
			mHandler.obtainMessage(MESSAGE_UPDATETEXT, 2, 4).sendToTarget();

		}

	}

	class SimpleTelnetTerminalHandler extends DefaultTelnetTerminalHandler
			implements TelnetConstants {

		public SimpleTelnetTerminalHandler() {
			super();
		}

		public void LineFeed() {
			// Tsa.setText(Tsa.getText() + '\n' + "_13_");

			addText("\n");
			addText2("\n");
			setVerzeichnis("");

			// tsaHandler.sendMessage(tsaHandler.obtainMessage());
			// Tsa.TextSchreiben();
			// System.out.print('\n');
			// System.out.flush();
		}

		public void CarriageReturn() {
			// Tsa.setText(Tsa.getText() + '\r'+ "_10_");
			addText("\r");
			addText2("\r");
			setVerzeichnis("");
			// tsaHandler.sendMessage(tsaHandler.obtainMessage());
			// Tsa.TextSchreiben();
			// System.out.print('\r');
			// System.out.flush();
		}

		public void BackSpace() {
			setText(getText() + (char) BS);
			setText2(getText2() + (char) BS);
			// tsaHandler.sendMessage(tsaHandler.obtainMessage());
			// Tsa.TextSchreiben();

			// System.out.print((char) BS);
			// System.out.flush();
		}

		public void HorizontalTab() {
			setText(getText() + (char) HT);
			setText2(getText2() + (char) HT);
			// tsaHandler.sendMessage(tsaHandler.obtainMessage());
			// Tsa.TextSchreiben();
			// System.out.print((char) HT);
			// System.out.flush();
		}
	}

	public void setContext(Context applicationContext) {
		mContext = applicationContext;

	}
}
