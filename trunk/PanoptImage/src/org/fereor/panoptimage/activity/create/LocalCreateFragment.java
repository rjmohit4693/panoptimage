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

import java.io.File;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.model.CreateParam;
import org.fereor.panoptimage.model.LocalParam;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class LocalCreateFragment extends CreateFragment<LocalParam> {
	private LocalParam content;
	private EditText nameField;
	private EditText pathField;
	private boolean viewCreated = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_create_local, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// Get fields
		nameField = (EditText) getView().findViewById(R.id.create_local_name_value);
		pathField = (EditText) getView().findViewById(R.id.create_local_path_value);
		// Mark that the view has been created
		viewCreated = true;
		onRefresh();
		displayTooltips();
	}

	@Override
	public void setParam(CreateParam data) {
		// Get data to display
		content = (LocalParam) data;
	}

	@Override
	public void onRefresh() {
		// if view is not created, return
		if (!viewCreated) {
			return;
		}
		// refresh content
		if (content != null) {
			nameField.setText(content.getKey());
			pathField.setText(content.getPath());
			setKeyEditable(false);
		} else {
			File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			nameField.setText("");
			pathField.setText(path == null ? "" : path.getAbsolutePath());
			setKeyEditable(true);
		}
	}

	@Override
	protected void displayTooltips() {
		showTooltip(R.id.tooltip_browse, R.id.create_local_browse);
	}

	@Override
	public LocalParam readParam() {
		if (content == null) {
			content = new LocalParam();
		}
		StringBuilder path = new StringBuilder(pathField.getText().toString());
		// read content
		if (nameField.getText().toString().equals(content.getKey())) {
			content.setPath(path.toString());
		} else {
			content.setKey(nameField.getText().toString());
			content.setPath(path.toString());
		}
		return content;
	}

	@Override
	public String readKey() {
		// do nothing (no data to save in this fragment
		return nameField.getText().toString();
	}

	@Override
	public void setPath(String path) {
		pathField.setText(path);
	}

	@Override
	public Class<LocalParam> getParamClass() {
		return LocalParam.class;
	}

	@Override
	public void setKeyEditable(boolean status) {
		if (nameField != null) {
			nameField.setClickable(status);
			nameField.setCursorVisible(status);
			nameField.setFocusable(status);
			nameField.setFocusableInTouchMode(status);
			nameField.setEnabled(status);
		}
	}
}
