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

import org.fereor.panoptimage.util.network.HotSite;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class WebdavScanAdapter extends ArrayAdapter<HotSite> {
	/**
	 * Default constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param
	 */
	public WebdavScanAdapter(Activity context, int resource, int textViewResourceId, List<HotSite> data) {
		super(context, resource, textViewResourceId, data);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		HotSite site = getItem(position);
		// find icon to display
		WebdavProtocolIcon iconobj = WebdavProtocolIcon.findIcon(site.getPort());
		// draw line with text and icon
		v.setText(site.getHost().getHostAddress()+ ":" + site.getPort());
		v.setCompoundDrawablesWithIntrinsicBounds(iconobj.icon(), 0, 0, 0);
		return v;
	}
}
