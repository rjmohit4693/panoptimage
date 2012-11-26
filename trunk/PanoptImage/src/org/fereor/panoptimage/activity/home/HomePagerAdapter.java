// This file is part of panoptes-viewer.
//
// panoptes-viewer is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// panoptes-viewer is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with panoptes-viewer.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.panoptimage.activity.home;

import java.util.List;

import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.util.PanoptesConstants;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;

/**
 * Adapter class to populate the Fragments
 * 
 * @author "arnaud.p.fereor"
 */
public class HomePagerAdapter extends FragmentStatePagerAdapter {

	/** data to display */
	private List<HomePagerParamService> data;

	/**
	 * Constructor
	 * 
	 * @param data data for this adapter
	 * @param fm FragmentManager to use
	 */
	public HomePagerAdapter(FragmentManager fm) {
		super(fm);
	}

	/**
	 * Setter to update data
	 * 
	 * @param data
	 */
	public void setData(List<HomePagerParamService> data) {
		this.data = data;
	}

	@Override
	public int getCount() {
		if (data == null)
			return 0;
		return data.size();
	}

	@Override
	public Fragment getItem(int position) {
		Log.d(PanoptesConstants.TAGNAME, "HomePagerAdapter:getItem:" + position);
		if (data == null)
			return null;
		return HomeListFragment.newInstance(data.get(position));
	}
}
