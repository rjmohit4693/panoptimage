package org.fereor.panoptimage.util;

import java.io.IOException;
import java.net.InetAddress;

import android.os.AsyncTask;

public class PingTask extends AsyncTask<InetAddress, Void, InetAddress> {
	/** timeout for network discovery */
	public static final int PING_TIMEOUT = 1000;

	@Override
	protected InetAddress doInBackground(InetAddress... ip) {
		// test address
		try {
			if (ip[0].isReachable(PING_TIMEOUT)) {
				return ip[0];
			} else {
				return null;
			}
		} catch (IOException e) {
			// Timeout was reached
			return null;
		}
	}

	@Override
	protected void onPostExecute(InetAddress result) {
		super.onPostExecute(result);
		if(result != null){
			// do something
		}
	}

}
