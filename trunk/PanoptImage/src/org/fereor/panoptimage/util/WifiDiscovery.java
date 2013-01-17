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

package org.fereor.panoptimage.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import android.net.wifi.WifiManager;
import android.os.AsyncTask;

public class WifiDiscovery {
	/** pool size for network discovery */
	public static final int THREAD_POOL_SIZE = 50;
	/** wifi manager */
	private WifiManager wifi;
	/** list of ports to scan */
	private int[] ports;
	/** Executor for all async tasks to handle */
	private ExecutorService executor;

	/**
	 * Constructor
	 * @param wifi wifi manager to gather network information
	 * @param p list of ports to scan
	 */
	public WifiDiscovery(WifiManager wifi, int... p) {
		this.wifi = wifi;
		this.ports = p;
		// Create the thread executor
		executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
	}
	
	public void startDiscovery(){
		
	}
}
