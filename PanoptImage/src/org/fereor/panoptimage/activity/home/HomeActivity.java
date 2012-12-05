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

package org.fereor.panoptimage.activity.home;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.activity.about.AboutActivity;
import org.fereor.panoptimage.activity.config.ConfigActivity;
import org.fereor.panoptimage.activity.create.CreateActivity;
import org.fereor.panoptimage.activity.help.HelpActivity;
import org.fereor.panoptimage.activity.image.ImageActivity;
import org.fereor.panoptimage.model.EmptyParam;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesTypeEnum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;

public class HomeActivity extends PanoptesActivity {
	private static final String SAVESTATE_CURRENTITEM = "org.fereor.panoptimage.activity.home.HomeActivity.currentItem";
	/** Adapter for the pager */
	private HomePagerAdapter adapter;
	/** Pager for the List */
	private ViewPager pager;

	/** List of available configurations */
	private List<HomePagerParamService> content;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.d(PanoptesConstants.TAGNAME, "HomeActivity:onCreate");
		// go on
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		try {
			adapter = new HomePagerAdapter(getSupportFragmentManager());
			// load content
			content = loadPagerContent();
			adapter.setData(content);
			pager = (ViewPager) findViewById(R.id.pager);
			pager.setAdapter(adapter);
			// restore state if needed
			if (savedInstanceState != null) {
				pager.setCurrentItem(savedInstanceState.getInt(SAVESTATE_CURRENTITEM));
				adapter.notifyDataSetChanged();
			} else {
			}
		} catch (SQLException e) {
			showErrorMsg(e);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		Log.d(PanoptesConstants.TAGNAME, "HomeActivity:onSaveInstanceState");
		outState.putInt(SAVESTATE_CURRENTITEM, pager.getCurrentItem());
	}

	@Override
	protected void onResume() {
		Log.d(PanoptesConstants.TAGNAME, "HomeActivity:onResume");
		// If database is updated, refresh content
		if (!isLocalParamUptodate() || !isWebdavParamUptodate()) {
			try {
				// load content
				content = loadPagerContent();
				// refresh adapter
				adapter.setData(content);
				pager.setAdapter(adapter);
				adapter.notifyDataSetChanged();
			} catch (SQLException e) {
				showErrorMsg(e);
			}
		}
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_home, menu);
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
		int location = pager.getCurrentItem();
		if (location >= content.size()) {
			showErrorMsg(getString(R.string.error_unknown));
			return;
		}

		// get data corresponding to item clicked
		HomePagerParamService curparam = content.get(location);
		if (!curparam.getParam().hasData()) {
			// This parameter has no data to display : return
			return;
		}
		// check network availability
		if (curparam.getParam().needNetwork() && !isNetworkAvailable()) {
			// Network is not available. Inform and return
			showErrorMsg(getString(R.string.error_nonetwork));
			return;
		}

		// pass information to the image page
		Intent intent = new Intent(this, ImageActivity.class);
		intent.putExtra(PanoptesConstants.MSG_HOME, curparam);
		startActivity(intent);
	}

	/** Called when the user clicks the Create button */
	public void showCreate(View view) {
		Log.i(PanoptesConstants.TAGNAME, "HomeActivity:showCreate");
		Intent intent = new Intent(this, CreateActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the Help button */
	public void showHelp(View view) {
		Log.i(PanoptesConstants.TAGNAME, "HomeActivity:showHelp");
		Intent intent = new Intent(this, HelpActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the Config button */
	public void showConfig(View view) {
		Log.i(PanoptesConstants.TAGNAME, "HomeActivity:showConfig");
		Intent intent = new Intent(this, ConfigActivity.class);
		startActivity(intent);
	}

	/** Called when the user clicks the About button */
	public void showAbout(View view) {
		Log.i(PanoptesConstants.TAGNAME, "HomeActivity:showAbout");
		Intent intent = new Intent(this, AboutActivity.class);
		startActivity(intent);
	}

	// -------------------------------------------------------------------------
	// private Methods
	// -------------------------------------------------------------------------
	/**
	 * Fill the scroll view with the default strings and the list of already defined content
	 * 
	 * @throws SQLException
	 */
	private List<HomePagerParamService> loadPagerContent() throws SQLException {
		// populate the spinner with default values
		List<HomePagerParamService> data = new ArrayList<HomePagerParamService>();
		// Add the default view (home page)
		data.add(new HomePagerParamService(new EmptyParam(), getString(R.string.message), PanoptesTypeEnum.EMPTY.icon()));

		// populate the scroll with local values
		for (LocalParam local : getHelper().getLocalParamDao().queryForAll()) {
			if (local.getKey() != null) {
				data.add(new HomePagerParamService(local, local.getKey(), PanoptesTypeEnum.LOCAL.icon()));
			}
		}
		markLocalRead();
		// populate the scroll with webdav values
		for (WebdavParam webdav : getHelper().getWebdavParamDao().queryForAll()) {
			if (webdav.getKey() != null) {
				data.add(new HomePagerParamService(webdav, webdav.getKey(), PanoptesTypeEnum.WEBDAV.icon()));
			}
		}
		markWebdavRead();
		return data;
	}

}
