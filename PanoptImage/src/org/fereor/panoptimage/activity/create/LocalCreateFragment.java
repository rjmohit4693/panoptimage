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

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.model.CreateParam;
import org.fereor.panoptimage.model.LocalParam;

import android.app.Activity;
import android.os.Bundle;
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
		return inflater.inflate(R.layout.fragment_local, container, false);
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
		} else {
			nameField.setText("");
			pathField.setText("");
		}
	}

	@Override
	public LocalParam readParam() {
		if (content == null) {
			content = new LocalParam();
		}
		if (nameField.getText().toString().equals(content.getKey())) {
			content.setPath(pathField.getText().toString());
		} else {
			content.setKey(nameField.getText().toString());
			content.setPath(pathField.getText().toString());
		}
		return content;
	}

	@Override
	public String readKey() {
		// do nothing (no data to save in this fragment
		return nameField.getText().toString();
	}

	@Override
	public Class<LocalParam> getParamClass() {
		return LocalParam.class;
	}
}
