package org.fereor.panoptimage.activity.create;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.repository.WebdavRepositoryDao;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ProtocolSpinnerAdapter extends ArrayAdapter<WebdavRepositoryDao.Protocols> {
	
	/** parent activity */
	private Activity container;

	/**
	 * Default constructor
	 * 
	 * @param context
	 * @param textViewResourceId
	 * @param
	 */
	public ProtocolSpinnerAdapter(Activity context,
			int textViewResourceId, WebdavRepositoryDao.Protocols[] data) {
		super(context, textViewResourceId, data);
		this.container = context;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		TextView v = (TextView) super.getView(position, convertView, parent);
		WebdavRepositoryDao.Protocols item = getItem(position);
		v.setText(item.toString());
		v.setCompoundDrawablesWithIntrinsicBounds(item.icon(), 0, 0, 0);
		return v;
	}

	@Override
	public View getDropDownView(int position, View convertView, ViewGroup parent) {
		View row = convertView;
		if (row == null) {
            LayoutInflater inflater = container.getLayoutInflater();
            row = inflater.inflate(R.layout.spinner_protocol, parent, false);
		}

		// get item to display
		WebdavRepositoryDao.Protocols item = getItem(position);

		if (item != null) {
			// Parse the data from each object and set it.
			TextView text = (TextView) row.findViewById(R.id.spinner_row_text);
			text.setText(item.toString());
			text.setCompoundDrawablesWithIntrinsicBounds(item.icon(), 0, 0, 0);
		}

		return row;
	}

}
