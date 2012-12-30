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

import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class LoadingPagerAdapter extends ImagePagerAdapter {

	public LoadingPagerAdapter(FragmentManager fm, PanoptimageMemoryOptimEnum optim) throws PanoptimageFileNotFoundException {
		super(null, fm, null);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Fragment getItem(int arg0) {
		return new LoadingFragment();
	}

	public void setData(RepositoryLoaderDao<?> data) {
		// do nothing
	}

}
