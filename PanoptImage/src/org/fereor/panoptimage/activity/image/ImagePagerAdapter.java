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

import org.fereor.panoptimage.exception.PanoptesException;
import org.fereor.panoptimage.service.RepositoryService;
import org.fereor.panoptimage.util.PanoptesHelper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

/**
 * Pager adapter for the image activity
 * 
 * @author "arnaud.p.fereor"
 */
public class ImagePagerAdapter extends FragmentStatePagerAdapter {

	/** data to display */
	private RepositoryService<?> repo = null;
	/** current directory content */
	private String[] imageList = null;
	/** current visible item */
	private ImageListFragment currentItem;

	public ImagePagerAdapter(RepositoryService<?> repo, FragmentManager fm) throws PanoptesException {
		super(fm);
		this.repo = repo;
		if (this.repo != null) {
			imageList = this.repo.dir(PanoptesHelper.REGEXP_ALLIMAGES);
		}
	}

	/**
	 * Setter to update data
	 * 
	 * @param data
	 */
	public void setData(RepositoryService<?> data) {
		this.repo = data;
	}

	@Override
	public int getCount() {
		if (repo == null || imageList == null)
			return 0;
		return imageList.length;
	}

	@Override
	public Fragment getItem(int position) {
		if (repo == null)
			return null;
		// get image content
		byte[] bindata;
		try {
			bindata = repo.get(imageList[position]);
			return ImageListFragment.newInstance(bindata);
		} catch (PanoptesException e) {
			return null;
		}
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		setCurrentItem((ImageListFragment) object);
	}

	/**
	 * Getter for current item
	 * 
	 * @return
	 */
	public ImageListFragment getCurrentItem() {
		return currentItem;
	}

	/**
	 * Setter for current item
	 * 
	 * @param currentItem sets the current item
	 */
	private void setCurrentItem(ImageListFragment currentItem) {
		this.currentItem = currentItem;
	}
}
