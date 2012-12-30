package org.fereor.panoptimage.activity.config;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class MemoryOptimSpinnerAdapter extends ArrayAdapter<PanoptimageMemoryOptimEnum> {

	private Activity container;

	/**
	 * Default constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 */
	public MemoryOptimSpinnerAdapter(Activity context, int textViewResourceId) {
		super(context, textViewResourceId, PanoptimageMemoryOptimEnum.values());
		this.container = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		v.setText(getContext().getString(PanoptimageMemoryOptimEnum.values()[position].text()));
//		v.setCompoundDrawablesWithIntrinsicBounds(getItem(position).ic(), 0, 0, 0);
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
		PanoptimageMemoryOptimEnum item = getItem(position);

		if (item != null) {
			// Parse the data from each object and set it.
			TextView text = (TextView) row.findViewById(R.id.spinner_row_text);
			text.setText(item.text());
			text.setCompoundDrawablesWithIntrinsicBounds(item.ic(), 0, 0, 0);
		}

		return row;
	}

}