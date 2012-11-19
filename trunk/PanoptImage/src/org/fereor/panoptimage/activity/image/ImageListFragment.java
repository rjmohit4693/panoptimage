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
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

/**
 * Class to build the fragment
 * 
 * @author "arnaud.p.fereor"
 */
public class ImageListFragment extends Fragment {
	private static final String BUNDLE_IMGDATA = "imgdata";

	/** data to display */
	private byte[] data;
	/** image to be displayed */
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

	/**
	 * Create a new instance of CountingFragment, providing "num" as an argument.
	 */
	public static ImageListFragment newInstance(byte[] bindata) {
		Log.d(PanoptesConstants.TAGNAME, "ImageListFragment:newInstance with " + bindata.length);
		// create instance
		ImageListFragment f = new ImageListFragment();
		// Supply input arguments.
		Bundle args = new Bundle();
		args.putByteArray(BUNDLE_IMGDATA, bindata);
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
			// get image data
			data = getArguments().getByteArray(BUNDLE_IMGDATA);
			// get screen size
			Display display = getActivity().getWindowManager().getDefaultDisplay();
			scrRect = new RectF(0, 0, display.getWidth(), display.getHeight());
			// get image at correct size
			image = PanoptesHelper.decodeSampledBitmap(data, (int)scrRect.width(), (int)scrRect.height());
			imgRect = new RectF(0, 0, image.getWidth(), image.getHeight());
			// Compute matrix
			scale.setRectToRect(imgRect, scrRect, Matrix.ScaleToFit.CENTER);
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
		if (imageView != null) {
			imageView.setImageBitmap(image);
		}
		return v;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
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
