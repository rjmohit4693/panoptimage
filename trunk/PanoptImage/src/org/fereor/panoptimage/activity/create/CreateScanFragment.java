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

package org.fereor.panoptimage.activity.create;

import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.util.PanoptimageConstants;
import org.fereor.panoptimage.util.network.HotSite;
import org.fereor.panoptimage.util.network.ScanListener;
import org.fereor.panoptimage.util.network.WifiDiscovery;

import android.content.Context;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Fragment class for the Browse panel
 * 
 * @author "arnaud.p.fereor"
 */
public class CreateScanFragment extends Fragment implements ScanListener, OnItemClickListener {
	/** mark reference of list view to update it */
	private ListView lv = null;
	/** mark reference for loading message */
	private TextView msg = null;
	/** mark reference for loading image */
	private ImageView img;
	/** Handler to refresh the UI thread */
	private Handler handler = new Handler();
	/** set to true when scan is done */
	private boolean done = false;
	/** list of result */
	private HotSite selection = null;

	/**
	 * The Fragment's UI is a
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_create_scan, container, false);
		lv = (ListView) v.findViewById(R.id.create_scan_list);
		img = (ImageView) v.findViewById(R.id.create_scan_image);
		msg = (TextView) v.findViewById(R.id.create_scan_text);
		lv.setOnItemClickListener(this);
		// lauch async scan
		lauchScan();
		return v;
	}

	/**
	 * Lauch the async task for directory browsing
	 */
	private void lauchScan() {
		// try to get wifi manager
		try {
			WifiManager wifi = (WifiManager) getActivity().getSystemService(Context.WIFI_SERVICE);
			WifiDiscovery task = new WifiDiscovery(this, wifi, PanoptimageConstants.THREAD_POOL_SIZE,
					WebdavProtocolIcon.WEBDAV_PORTS);
			task.execute();
		} catch (Exception e) {

		}
	}

	/**
	 * Returns the result selected
	 * 
	 * @return path selected (formatted)
	 */
	public HotSite getSelectedHotSite() {
		lv.setOnItemClickListener(null);
		return selection;
	}

	/**
	 * Inform that search scanning is finished
	 * 
	 * @return
	 */
	public boolean isDone() {
		return done;
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// extract selected item
		selection = (HotSite) parent.getAdapter().getItem(position);
	}

	// -------------------------------------------------------------------------
	// Methods of interface ScanListener
	// -------------------------------------------------------------------------
	@Override
	public void onScanProgress(float max, float progress) {
		final int splashRes;
		float val = progress / max * PanoptimageConstants.ONPROGRESS_STEPS;
		// identify image to draw
		if (val < 1.0f) {
			splashRes = R.drawable.splash_0;
		} else if (val < 2.0f) {
			splashRes = R.drawable.splash_1;
		} else if (val < 3.0f) {
			splashRes = R.drawable.splash_2;
		} else if (val < 4.0f) {
			splashRes = R.drawable.splash_3;
		} else {
			splashRes = R.drawable.splash_4;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (getActivity() != null) {
					// This gets executed on the UI thread so it can safely modify Views
					img.setImageResource(splashRes);
				}
			}
		});
	}

	@Override
	public void onScanFinished(List<HotSite> result) {
		// Check validity of fragment
		if (getActivity() != null) {
			// set result in header
			img.setImageResource(R.drawable.splash_4);
			msg.setText(getActivity().getString(R.string.create_webdav_foundsites, result.size()));
			// fill list with results
			WebdavScanAdapter adapter = new WebdavScanAdapter(getActivity(), R.layout.listrow_scan,
					R.id.create_row_message, result);
			lv.setAdapter(adapter);
		}
		this.done = true;
	}
}
