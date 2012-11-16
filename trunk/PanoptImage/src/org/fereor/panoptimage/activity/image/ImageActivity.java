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

package org.fereor.panoptimage.activity.image;

import java.util.ArrayList;
import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.exception.PanoptesException;
import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.service.HomePagerParam;
import org.fereor.panoptimage.service.RepositoryService;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ImageActivity extends PanoptesActivity implements OnItemClickListener {
	private RepositoryService<?> repoBrowser;
	private List<String> directories;
	private ImagePagerAdapter myAdapter;
	private ViewPager myPager;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// set layout
		setContentView(R.layout.activity_image);
		// Get the message from the intent
		Intent intent = getIntent();
		HomePagerParam param = (HomePagerParam) intent.getParcelableExtra(PanoptesConstants.MSG_HOME);
		try {
			// Retrieve content
			repoBrowser = RepositoryService.createInstance(param);
			// set adapter
			myAdapter = new ImagePagerAdapter(repoBrowser, getSupportFragmentManager());
			myPager = (ViewPager) findViewById(R.id.imagepager);
			myPager.setAdapter(myAdapter);
			// hide panel
			hideBrowserPanel();
		} catch (PanoptesUnknownParamException e) {
			showErrorMsg(getString(R.string.error_title), getString(R.string.error_unknown_param));
		} catch (PanoptesException e) {
			showErrorMsg(getString(R.string.error_title), getString(R.string.error_unknown_param));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_image, menu);
		return true;
	}

	// -------------------------------------------------------------------------
	// Methods for Action buttons
	// -------------------------------------------------------------------------
	/**
	 * Called when the image is clicked
	 * 
	 * @param view image clicked
	 * @return
	 */
	public void onImageClicked(View view) {
		// TODO : go to the zoomed image
	}

	/**
	 * Back button clicked
	 * 
	 * @param view
	 */
	public void doBack(View view) {
		finish();
	}

	/**
	 * Back button clicked
	 * 
	 * @param view
	 */
	public void doBrowse(View view) {
		try {
			// scan content of current directory (include ..)
			String[] rawdir = repoBrowser.dir(PanoptesHelper.REGEXP_DIRECTORY);
			directories = new ArrayList<String>(rawdir.length + 1);
			directories.add(PanoptesHelper.DDOT);
			for (String it : rawdir) {
				directories.add(it);
			}

			// get content for the list
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
					directories);

			ListView lv = (ListView) findViewById(R.id.image_browse_list);
			lv.setAdapter(adapter);
			lv.setOnItemClickListener(this);
			// show panel
			showBrowserPanel();

		} catch (PanoptesException e) {
			showErrorMsg(e);
		}
	}

	/**
	 * Back button clicked
	 * 
	 * @param view
	 */
	public void doHidePanel(View view) {
		// hide panel
		hideBrowserPanel();
	}

	@Override
	public void onItemClick(AdapterView<?> aView, View v, int position, long id) {
		// TODO do something
		if (directories == null) {
			return;
		}
		Toast.makeText(this, directories.get(position), Toast.LENGTH_LONG).show();
		// change to directory selected
		try {
			repoBrowser.cd(directories.get(position));
			myAdapter.setData(repoBrowser);
			myPager.setAdapter(myAdapter);
			// hide panel
			hideBrowserPanel();
		} catch (PanoptesException e) {
			showErrorMsg(e);
		}
	}

	/**
	 * Rotate clockwise button clicked
	 * 
	 * @param view
	 */
	public void doRotateClockwise(View view) {
		// get current fragment displayed
		ImageListFragment fmt = myAdapter.getCurrentItem();
		if (fmt != null)
			fmt.rotateClockwise();
	}

	/**
	 * Rotate counterclockwise button clicked
	 * 
	 * @param view
	 */
	public void doRotateCounterClockwise(View view) {
		// get current fragment displayed
		ImageListFragment fmt = myAdapter.getCurrentItem();
		if (fmt != null)
			fmt.rotateCounterClockwise();
	}

	/**
	 * Hide the browser fragment
	 */
	private void hideBrowserPanel() {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.browser_fragment);
		getSupportFragmentManager().beginTransaction().hide(browseFragment).commit();
	}

	/**
	 * Show the browser fragment
	 */
	private void showBrowserPanel() {
		Fragment browseFragment = getSupportFragmentManager().findFragmentById(R.id.browser_fragment);
		getSupportFragmentManager().beginTransaction().show(browseFragment).commit();
	}

}
