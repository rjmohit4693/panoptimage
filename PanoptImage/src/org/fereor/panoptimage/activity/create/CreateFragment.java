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

import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.model.CreateParam;

import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.TextView;

public abstract class CreateFragment<T extends CreateParam> extends Fragment {
	/**
	 * Read the information of the fragment into a param
	 * 
	 * @return param filled with data
	 */
	public abstract T readParam();

	/**
	 * Get the class implemented for this fragment
	 * 
	 * @return
	 */
	public abstract Class<T> getParamClass();

	/**
	 * Read the key of the fragment
	 * 
	 * @return key of the fragment
	 */
	public abstract String readKey();

	/**
	 * Sets the path selected
	 * 
	 * @param path path to set
	 */
	public abstract void setPath(String path);

	/**
	 * Called to set the parameter for the fragment
	 * 
	 * @param displayParam
	 */
	public abstract void setParam(CreateParam displayParam);

	/**
	 * Called to refresh the fragment content
	 */
	public abstract void onRefresh();

	/**
	 * Set the status of the key field
	 * 
	 * @param status
	 */
	public abstract void setKeyEditable(boolean status);
	
	/**
	 * Display tooltips for the panel
	 * @param visible
	 */
	protected abstract void displayTooltips();
	
	/**
	 * Show tooltips displayed
	 */
	protected void showTooltip(int tid) {
		PanoptesActivity a = (PanoptesActivity) getActivity();
		if (a == null)
			return;
		TextView txt;
		// Identify texts to display
		txt = (TextView) a.findViewById(tid);
		txt.setTypeface(a.getTooltipFont());
		txt.setVisibility(a.isTooltipVisible() ? View.VISIBLE : View.INVISIBLE);
	}

}
