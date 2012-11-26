package org.fereor.panoptimage.activity.image;

import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.service.RepositoryService;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

public class LoadingPagerAdapter extends ImagePagerAdapter {

	public LoadingPagerAdapter(FragmentManager fm) throws PanoptimageFileNotFoundException {
		super(null, fm);
	}

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Fragment getItem(int arg0) {
		return new LoadingFragment();
	}

	public void setData(RepositoryService<?> data) {
		// do nothing
	}

}
