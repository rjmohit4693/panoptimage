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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.PanoptesActivity;
import org.fereor.panoptimage.service.RepositoryService;
import org.fereor.panoptimage.service.async.RepositoryGetAsync;
import org.fereor.panoptimage.service.async.RepositoryGetListener;
import org.fereor.panoptimage.util.PanoptesHelper;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
public class ImageListFragment extends Fragment implements RepositoryGetListener<Integer, InputStream> {
	private static final String BUNDLE_IMGDATA = "imgdata";

	/** data to display */
	private byte[] data = null;
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
	/** path of image to display */
	private String path;

	/**
	 * Create a new instance of CountingFragment, providing "num" as an argument.
	 */
	public static ImageListFragment newInstance(RepositoryService<?> repo, String path) {
		// create instance
		ImageListFragment f = new ImageListFragment();
		// Lauch async task
		RepositoryGetAsync task = new RepositoryGetAsync(f, path);
		task.execute(repo);

		// Supply input arguments.
		Bundle args = new Bundle();
		args.putString(BUNDLE_IMGDATA, path);
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
	public void onPreGet() {
		// Nothing to do
	}

	@Override
	public void onPostGet(InputStream result) {
		if (result == null) {
			((PanoptesActivity)getActivity()).showErrorMsg(R.string.error_loading_file, path);
			return;
		}
		// read image content
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buf = new byte[1024];
		int cnt;
		try {
			while ((cnt = result.read(buf)) > 0) {
				baos.write(buf, 0, cnt);
			}
			result.close();
			baos.close();
			data = baos.toByteArray();
		} catch (IOException e) {
			((PanoptesActivity)getActivity()).showErrorMsg(R.string.error_loading_file, path);
			return;
		}
		// put content in view
		if (imageView != null) {
			// get image at correct size
			image = PanoptesHelper.decodeSampledBitmap(data, (int) scrRect.width(), (int) scrRect.height());
			// resize data to optimize memory
//			ByteArrayOutputStream sbaos = new ByteArrayOutputStream();
//			image.compress(CompressFormat.JPEG, 60, sbaos);
//			data = baos.toByteArray();
			imgRect = new RectF(0, 0, image.getWidth(), image.getHeight());
			// Compute matrix
			scale.setRectToRect(imgRect, scrRect, Matrix.ScaleToFit.CENTER);
			imageView.setImageBitmap(image);
		}
	}

	@Override
	public void onGetProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
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
