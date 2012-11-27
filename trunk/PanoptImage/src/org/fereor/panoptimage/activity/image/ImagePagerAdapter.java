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

import java.util.List;

import org.fereor.panoptimage.exception.PanoptimageException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.service.RepositoryService;
import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;

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
	private List<String> imageList = null;
	/** current visible item */
	private ImageListFragment currentItem;
	/** Memory optimization level */
	PanoptimageMemoryOptimEnum optim;

	public ImagePagerAdapter(RepositoryService<?> repo, FragmentManager fm, PanoptimageMemoryOptimEnum optim) {
		super(fm);
		setData(repo);
		this.optim = (optim != null) ? optim : PanoptimageMemoryOptimEnum.Auto;
	}

	/**
	 * Setter to update data
	 * 
	 * @param data
	 * @throws PanoptimageException
	 */
	public void setData(RepositoryService<?> data) {
		this.repo = data;
	}

	/**
	 * Refresh content of image list
	 * 
	 * @throws PanoptimageFileNotFoundException
	 */
	public void setImageList(List<String> list) {
		imageList = list;
	}

	@Override
	public int getCount() {
		if (repo == null || imageList == null)
			return 0;
		return imageList.size();
	}

	@Override
	public Fragment getItem(int position) {
		if (repo == null)
			return null;
		// create fragment instance
		return ImageListFragment.newInstance(repo, imageList.get(position), optim);
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
