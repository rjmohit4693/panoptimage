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
import org.fereor.panoptimage.model.EmptyParam;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EmptyCreateFragment extends CreateFragment<EmptyParam> {
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_create_empty, container, false);
	}

	@Override
	public EmptyParam readParam() {
		// do nothing
		return null;
	}

	@Override
	public String readKey() {
		// do nothing
		return null;
	}

	@Override
	public Class<EmptyParam> getParamClass() {
		return EmptyParam.class;
	}

	@Override
	public void onRefresh() {
		// do nothing
		return;
	}

	@Override
	public void setParam(CreateParam displayParam) {
		// do nothing
		return;

	}
}
