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

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.util.PanoptimageTypeEnum;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Class to build the fragment
 * 
 * @author "arnaud.p.fereor"
 */
public class HomeListFragment extends Fragment {
	private static final String BUNDLE_IMGID = "imgid";
	private static final String BUNDLE_MSG = "msg";

	/** Message to display */
	private String msg;
	/** Icon to display */
	private int imgid;

	/**
	 * Create a new instance of HomeListFragment
	 */
	public static HomeListFragment newInstance(HomePagerParamService homePagerParam) {
		HomeListFragment f = new HomeListFragment();

		// Supply input arguments.
		Bundle args = new Bundle();
		args.putInt(BUNDLE_IMGID, homePagerParam.getImgid());
		args.putString(BUNDLE_MSG, homePagerParam.getMessage());
		f.setArguments(args);

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		imgid = getArguments() != null ? getArguments().getInt(BUNDLE_IMGID) : PanoptimageTypeEnum.EMPTY.icon();
		msg = getArguments() != null ? getArguments().getString(BUNDLE_MSG) : "";
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_home_list, container, false);
		// set image
		ImageView iv = (ImageView) v.findViewById(R.id.homeImage);
		iv.setImageResource(imgid);
		// set text
		TextView tv = (TextView) v.findViewById(R.id.homeShortname);
		tv.setText(msg);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
