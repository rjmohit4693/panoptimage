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

package org.fereor.panoptimage.util;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Utility class to keep several static methods
 * 
 * @author "arnaud.p.fereor"
 */
public class PanoptesHelper {
	public static final String DOT = ".";
	public static final String DDOT = "..";
	public static final String SLASH = "/";
	public static final String REGEXP_ALLIMAGES = ".+\\.jpg|.+\\.jpeg|.+\\.png|.+\\.gif";
	public static final String REGEXP_DIRECTORY = "^[^\\\\.]*$";

	/**
	 * Format a string to a path using SLASH
	 * 
	 * @param path
	 * @return
	 */
	public static final String formatPath(List<String> path, String... others) {
		return formatPath(null, path, others);
	}

	/**
	 * Format a string to a path using SLASH
	 * @param root root path
	 * @param path current path
	 * @param others others as list
	 * @return
	 */
	public static final String formatPath(String root, List<String> path, String... others) {
		StringBuilder str = new StringBuilder(SLASH);
		if (root != null) {
			str.append(root);
		}
		for (String cur : path) {
			str.append(cur).append(SLASH);
		}
		for (String cur : others) {
			str.append(cur).append(SLASH);
		}
		return str.toString();
	}

	/**
	 * Calculates the sample size of image
	 * 
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.round((float) height / (float) reqHeight);
			} else {
				inSampleSize = Math.round((float) width / (float) reqWidth);
			}
		}
		return inSampleSize;
	}

	/**
	 * Extract bitmap at the given size
	 * 
	 * @param data binary data
	 * @param reqWidth target height
	 * @param reqHeight target width
	 * @return bitmap at the target size
	 */
	public static Bitmap decodeSampledBitmap(byte[] data, int reqWidth, int reqHeight) {
		// get image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}
}
