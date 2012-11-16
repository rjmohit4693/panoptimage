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
import org.fereor.panoptimage.model.WebdavParam;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class WebdavCreateFragment extends CreateFragment<WebdavParam> {
	private WebdavParam content;
	private EditText nameField;
	private EditText protocolField;
	private EditText serverField;
	private EditText portField;
	private EditText baseField;
	private EditText pathField;
	private boolean viewCreated = false;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_webdav, container, false);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// Get fields to update
		nameField = (EditText) getView().findViewById(R.id.create_webdav_name_value);
		protocolField = (EditText) getView().findViewById(R.id.create_webdav_protocol_value);
		serverField = (EditText) getView().findViewById(R.id.create_webdav_ip_value);
		portField = (EditText) getView().findViewById(R.id.create_webdav_port_value);
		baseField = (EditText) getView().findViewById(R.id.create_webdav_base_value);
		pathField = (EditText) getView().findViewById(R.id.create_webdav_path_value);
		// Mark that the view has been created
		viewCreated = true;
		onRefresh();
	}

	@Override
	public void setParam(CreateParam data) {
		// Get data to display
		content = (WebdavParam) data;
	}

	@Override
	public void onRefresh() {
		// if view is not created, return
		if (!viewCreated) {
			return;
		}
		// refresh content
		if (content != null) {
			// fill fields
			nameField.setText(content.getKey());
			protocolField.setText(content.getProtocol());
			serverField.setText(content.getServer());
			portField.setText(content.getPort());
			baseField.setText(content.getBase());
			pathField.setText(content.getPath());
		} else {
			// reset fields
			nameField.setText("");
			protocolField.setText("");
			serverField.setText("");
			portField.setText("");
			baseField.setText("");
			pathField.setText("");
		}
	}

	@Override
	public WebdavParam readParam() {
		// create content if null
		if (content == null) {
			content = new WebdavParam();
		}
		// et key if not set
		if (!nameField.getText().toString().equals(content.getKey())) {
			content.setKey(nameField.getText().toString());
		}
		// update values
		content.setProtocol(protocolField.getText().toString());
		content.setServer(serverField.getText().toString());
		content.setPort(portField.getText().toString());
		content.setBase(baseField.getText().toString());
		content.setPath(pathField.getText().toString());
		return content;
	}

	@Override
	public String readKey() {
		// do nothing (no data to save in this fragment
		return null;
	}

	@Override
	public Class<WebdavParam> getParamClass() {
		return WebdavParam.class;
	}

}
