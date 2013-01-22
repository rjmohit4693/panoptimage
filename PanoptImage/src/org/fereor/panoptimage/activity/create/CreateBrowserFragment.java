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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.async.RepositoryDirAsync;
import org.fereor.panoptimage.dao.async.RepositoryDirListener;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderFactory;
import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.model.CreateParam;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.fereor.panoptimage.util.PanoptesTypeEnum;
import org.fereor.panoptimage.util.PanoptimageMsg;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Fragment class for the Browse panel
 * 
 * @author "arnaud.p.fereor"
 */
public class CreateBrowserFragment extends Fragment implements OnItemClickListener,
		RepositoryDirListener<Long, List<String>> {
	/** Repository browser */
	RepositoryLoaderDao<?> repoBrowser;
	/** mark reference of list view to update it */
	private ListView lv = null;
	/** mark reference for loading message */
	private TextView msg = null;

	/**
	 * The Fragment's UI is a
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_create_browse, container, false);
		lv = (ListView) v.findViewById(R.id.create_browse_list);
		msg = (TextView) v.findViewById(R.id.create_browse_message);
		if (getArguments() != null) {
			CreateParam param = (CreateParam) getArguments().getSerializable(PanoptesConstants.CREATE_BUNDLE_PARAM);
			try {
				setRepository(param);
			} catch (PanoptesUnknownParamException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (PanoptimageNoNetworkException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// lauch async task
		lauchDir();
		return v;
	}

	@Override
	public void onPreDir() {
		lv.setOnItemClickListener(null);
		msg.setText(R.string.loading);
	}

	@Override
	public void onPostDir(List<String> rawdir) {
		// Check if fragment is still active
		if (getActivity() != null) {
			// Manage null value (Not Found)
			if (rawdir == null) {
				msg.setText(getActivity().getString(R.string.error_filenotfound, repoBrowser.getformatedPath()));
				repoBrowser.cd(PanoptesHelper.DDOT);
			} else {
				// Include .. to the list
				ArrayList<String> directories = new ArrayList<String>();
				if (!repoBrowser.isRoot()) {
					directories.add(PanoptesHelper.DDOT);
				}
				if (rawdir != null) {
					// sort rawdir
					Collections.sort(rawdir);
					for (String it : rawdir) {
						if (!it.isEmpty()) {
							directories.add(it);
						}
					}
				}
				// get content for the list
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.listrow_browse,
						R.id.create_row_message, directories);
				lv.setAdapter(adapter);
				// Change message
				msg.setText(repoBrowser.getformatedPath());
			}

			// set listener
			lv.setOnItemClickListener(this);
		}
	}

	@Override
	public void onDirProgressUpdate(Long... values) {
		// do nothing
	}

	@Override
	public void onOEM(Throwable t) {
		PanoptimageMsg.showErrorMsg(getActivity(), R.string.error_outofmemory);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
		// long click
		Object item = parent.getAdapter().getItem(position);
		// change to directory selected
		repoBrowser.cd(item.toString());
		lauchDir();
	}

	/**
	 * Returns the result selected
	 * 
	 * @return path selected (formatted)
	 */
	public String getSelectedPath() {
		return repoBrowser.getformatedPath();
	}

	/**
	 * Sets the repository of the fragment
	 * 
	 * @param param parameters to prepare the repository
	 * @throws PanoptesUnknownParamException
	 * @throws PanoptimageNoNetworkException
	 */
	private void setRepository(CreateParam param) throws PanoptesUnknownParamException, PanoptimageNoNetworkException {
		// get the path of the current repository and reset it
		String basepath = param.getPath();
		if (basepath.isEmpty()) {
			basepath = PanoptesHelper.SLASH;
		}
		param.setPath("");
		// create repo browser
		HomePagerParamService service = new HomePagerParamService(param, getString(R.string.message),
				PanoptesTypeEnum.EMPTY.icon());
		repoBrowser = RepositoryLoaderFactory.createInstance(service, getActivity().getFilesDir());
		// put the content to the repository
		repoBrowser.cd(basepath);
	}

	/**
	 * Lauch the async task for directory browsing
	 */
	private void lauchDir() {
		RepositoryDirAsync task = new RepositoryDirAsync(this, PanoptesHelper.REGEXP_DIRECTORY);
		task.execute(repoBrowser);
	}
}
