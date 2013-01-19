// This file is part of panoptimage.
//
// panoptimage is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// panoptimage is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with panoptimage.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.panoptimage.util.network;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import android.os.AsyncTask;

public class DiscoveryTask extends AsyncTask<InetAddress, Void, List<HotSite>> {
	/** timeout for network discovery */
	private static final int TIMEOUT = 1000;
	/** Listener when discover */
	private WeakReference<DiscoveryListener> listener;
	/** List of ports to scan */
	private int[] ports;

	/**
	 * Default constructor to setup all variables
	 * 
	 * @param lsn listener
	 * @param ports list of ports to test
	 */
	public DiscoveryTask(DiscoveryListener lsn, int... ports) {
		this.listener = new WeakReference<DiscoveryListener>(lsn);
		this.ports = ports;
	}

	@Override
	protected List<HotSite> doInBackground(InetAddress... ip) {
		List<HotSite> result = new ArrayList<HotSite>(ports.length);
		// test address
		try {
			if (ip[0].isReachable(TIMEOUT)) {
				for (int port : ports) {
					// try to open a socket to the host on the given port
					Socket so = null;
					try {
						so = new Socket(ip[0], port);
						result.add(new HotSite(ip[0], port));
					} catch (IOException ioe) {
						// port is not responding, not available
					} finally {
						if (so != null) {
							try {
								so.close();
							} catch (IOException e) {
							}
						}
					}
				}
				return result;
			} else {
				// host is not reachable
				return null;
			}
		} catch (IOException e) {
			// Timeout was reached
			return null;
		}
	}

	@Override
	protected void onPostExecute(List<HotSite> result) {
		super.onPostExecute(result);
		if (listener == null || listener.get() == null) {
			return;
		}
		listener.get().addressFound(result);
	}
}
