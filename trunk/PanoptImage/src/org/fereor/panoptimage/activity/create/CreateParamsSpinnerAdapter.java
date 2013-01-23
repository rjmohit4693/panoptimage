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
import org.fereor.panoptimage.util.PanoptimageTypeEnum;

import android.app.Activity;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CreateParamsSpinnerAdapter extends ArrayAdapter<Pair<CharSequence, PanoptimageTypeEnum>> {
	
	/** parent activity */
	private Activity container;

	/**
	 * Default constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param
	 */
	public CreateParamsSpinnerAdapter(Activity context, List<Pair<CharSequence, PanoptimageTypeEnum>> data,
			int textViewResourceId) {
		super(context, textViewResourceId, data);
		this.container = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		v.setText(getItem(position).first);
		v.setCompoundDrawablesWithIntrinsicBounds(getItem(position).second.iconxs(), 0, 0, 0);
		v.setTextSize(container.getResources().getDimension(R.dimen.spinnersize));
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
            LayoutInflater inflater = container.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_createparam, parent, false);
		}

		// get item to display
		Pair<CharSequence, PanoptimageTypeEnum> item = getItem(position);

		if (item != null) {
			// Parse the data from each object and set it.
			TextView text = (TextView) row.findViewById(R.id.spinner_row_text);
			text.setText(item.first);
			text.setCompoundDrawablesWithIntrinsicBounds(item.second.iconxs(), 0, 0, 0);
			text.setTextSize(container.getResources().getDimension(R.dimen.spinnersize));
		}

		return row;
	}

}
