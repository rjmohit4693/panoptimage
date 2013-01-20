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

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.dao.async.RepositoryGetAsync;
import org.fereor.panoptimage.dao.async.RepositoryGetListener;
import org.fereor.panoptimage.dao.repository.RepositoryContent;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptimageMemoryOptimEnum;
import org.fereor.panoptimage.util.PanoptimageMsg;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * Class to build the fragment
 * 
 * @author "arnaud.p.fereor"
 */
public class ImageListFragment extends Fragment implements RepositoryGetListener<Long, RepositoryContent> {
	private static final String BUNDLE_IMGDATA = "org.fereor.panoptimage.activity.image.ImageListFragment.imgdata";
	private static final String BUNDLE_OPTIM = "org.fereor.panoptimage.activity.image.ImageListFragment.optim";

	/** image displayed */
	private Bitmap image;
	/** image dimensions */
	private RectF imgRect;
	/** screen dimensions */
	private RectF scrRect;
	/** image view to use during the fragment life cycle */
	private ImageView imageView;
	/** current matrix */
	private Matrix rotate = new Matrix();
	/** original scale matrix */
	private Matrix scale = new Matrix();
	/** path of image to display */
	private String path;
	/** memory optimization level */
	private int optimlvl;
	/** Task loading the image */
	private RepositoryGetAsync task;
	/** Handler to refresh the UI thread */
	private Handler handler = new Handler();

	/**
	 * Create a new instance of CountingFragment, providing "num" as an argument.
	 */
	public static ImageListFragment newInstance(RepositoryLoaderDao<?> repo, String path,
			PanoptimageMemoryOptimEnum optim) {
		// create instance
		ImageListFragment f = new ImageListFragment();
		// Lauch async task
		f.task = new RepositoryGetAsync(f, path);
		f.task.execute(repo);

		// Supply input arguments.
		Bundle args = new Bundle();
		args.putString(BUNDLE_IMGDATA, path);
		args.putInt(BUNDLE_OPTIM, optim.key());
		f.setArguments(args);

		return f;
	}

	/**
	 * When creating, retrieve this instance's number from its arguments.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			optimlvl = getArguments().getInt(BUNDLE_OPTIM);
			path = getArguments().getString(BUNDLE_IMGDATA);
			// get screen size
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			scrRect = new RectF(0, 0, display.getWidth(), display.getHeight());
		}
	}

	/**
	 * The Fragment's UI is just a simple text view showing its instance number.
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_image_list, container, false);
		// set image
		imageView = (ImageView) v.findViewById(R.id.myImage);
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// destroy task
		if (task != null) {
			task.cancel(true);
			task = null;
		}
		// destroy image
		if (image != null) {
			image.recycle();
		}
	}

	@Override
	public void onPreGet() {
		// Nothing to do
	}

	@Override
	public void onPostGet(RepositoryContent result) {
		// task is finished, release reference
		task = null;
		if (result == null) {
			PanoptimageMsg.showErrorMsg(getActivity(), R.string.error_loading_file, path);
			return;
		}
		// put content in view
		if (imageView != null) {
			try {
				// get image at correct size
				image = result.decodeAsBitmap((int) scrRect.width(), (int) scrRect.height(), optimlvl);
				// resize data to optimize memory
				imgRect = new RectF(0, 0, image.getWidth(), image.getHeight());
				// Compute matrix
				scale.setRectToRect(imgRect, scrRect, Matrix.ScaleToFit.CENTER);
				imageView.getLayoutParams().width = LayoutParams.MATCH_PARENT;
				imageView.getLayoutParams().height = LayoutParams.MATCH_PARENT;
				imageView.setImageBitmap(image);
			} catch (OutOfMemoryError oem) {
				onOEM(oem);
			}
		}
		System.gc();
		System.gc();
	}

	@Override
	public void onOEM(Throwable t) {
		Log.d(PanoptesConstants.TAGNAME, t.toString());
		handler.post(new Runnable() {
			@Override
			public void run() {
				if (getActivity() != null)
					PanoptimageMsg.showErrorMsg(getActivity(), R.string.error_outofmemory);
			}
		});

		// do something to revive memory
		System.gc();
		System.gc();
	}

	@Override
	public void onGetProgressUpdate(Long... values) {
		final int splashRes;
		float val = ((float) values[0]) / ((float) values[1]) * PanoptesConstants.ONPROGRESS_STEPS;
		// identify image to draw
		if (val < 1.0f) {
			splashRes = R.drawable.splash_0;
		} else if (val < 2.0f) {
			splashRes = R.drawable.splash_1;
		} else if (val < 3.0f) {
			splashRes = R.drawable.splash_2;
		} else if (val < 4.0f) {
			splashRes = R.drawable.splash_3;
		} else {
			splashRes = R.drawable.splash_4;
		}
		handler.post(new Runnable() {
			@Override
			public void run() {
				// This gets executed on the UI thread so it can safely modify
				// Views
				imageView.getLayoutParams().width = 120;
				imageView.getLayoutParams().height = 120;
				imageView.setImageResource(splashRes);
			}
		});
	}

	/**
	 * Rotate image clockwise
	 * 
	 * @param view
	 */
	public void rotateClockwise() {
		rotateImage(90.0f);
	}

	/**
	 * Rotate image counterclockwise
	 * 
	 * @param view
	 */
	public void rotateCounterClockwise() {
		rotateImage(-90.0f);
	}

	/**
	 * Rotate the matrix
	 * 
	 * @param angle angle to rotate
	 */
	private void rotateMatrix(float angle) {
		int pivX = (int) (imgRect.width() / 2);
		int pivY = (int) (imgRect.height() / 2);
		rotate.postRotate(angle, pivX, pivY);
	}

	/**
	 * Rotate the image view
	 * 
	 * @param angle angle to rotate
	 */
	private void rotateImage(float angle) {
		if (imgRect == null) {
			return;
		}
		// rotate the matrix
		rotateMatrix(angle);
		// compute the new image rectangle
		RectF iDst = new RectF();
		rotate.mapRect(iDst, imgRect);

		if (imageView != null) {
			// create a matrix for the imageview
			Matrix newTransform = new Matrix();
			newTransform.setRectToRect(iDst, imgRect, Matrix.ScaleToFit.CENTER);
			newTransform.postConcat(rotate);
			newTransform.postConcat(scale);
			// rotate the image
			imageView.setScaleType(ScaleType.MATRIX);
			imageView.setImageMatrix(newTransform);
		}
	}
}
