package org.fereor.panoptimage.activity.config;

import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckedTextView;
import android.widget.TextView;

public class MemoryOptimSpinnerAdapter extends ArrayAdapter<PanoptimageMemoryOptimEnum> {

	/**
	 * Default constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 */
	public MemoryOptimSpinnerAdapter(Context context, int textViewResourceId) {
		super(context, textViewResourceId, PanoptimageMemoryOptimEnum.values());
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		v.setText(getContext().getString(PanoptimageMemoryOptimEnum.values()[position].text()));
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		CheckedTextView v = (CheckedTextView) super.getDropDownView(position, convertView, parent);
		v.setText(getContext().getString(PanoptimageMemoryOptimEnum.values()[position].text()));
		return v;
	}

}
