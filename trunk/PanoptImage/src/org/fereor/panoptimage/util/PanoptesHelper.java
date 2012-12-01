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
	public static final String HTTPBASE = "://";
	public static final String HTTPPORT = ":";
	public static final String REGEXP_ALLIMAGES = ".+\\.jpg|.+\\.jpeg|.+\\.png|.+\\.gif";
	public static final String REGEXP_DIRECTORY = "^[^\\\\.]*$";

	/** Encoder for URL */
	// private static HashMap<String, String> encoder = new HashMap<String, String>();
	// static {
	// encoder.put(" ", "%20");
	// }

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
	 * 
	 * @param root root path
	 * @param path current path
	 * @param others others as list
	 * @return
	 */
	public static final String formatPath(String root, List<String> path, String... others) {
		StringBuilder str = new StringBuilder();
		if (root != null) {
			str.append(root);
		}
		for (String cur : path) {
			str.append(cur).append(SLASH);
		}
		for (String cur : others) {
			str.append(cur).append(SLASH);
		}
		if (str.length() == 0)
			return "";
		if (SLASH.equals(str.substring(str.length() - 1, str.length()))) {
			return str.substring(0, str.length() - 1);
		}
		return str.toString();
	}

	/**
	 * Encode a URL to allow correct URL encoding
	 * 
	 * @param url url to encode
	 * @return url encoded
	 */
	// public static String encode(String url) {
	// if (url == null)
	// return null;
	// // read each char of string
	// StringBuilder res = new StringBuilder();
	// for (int i = 0; i < url.length(); i++) {
	// String next = url.substring(i, i + 1);
	// // encode value
	// if (encoder.containsKey(next)) {
	// res.append(encoder.get(next));
	// } else {
	// res.append(next);
	// }
	// }
	// return res.toString();
	// }

	/**
	 * Encode a URL to allow correct URL encoding
	 * 
	 * @param url url to encode
	 * @return url encoded
	 */
	// public static String decode(String url) {
	// if (url == null)
	// return null;
	// // read each char of string
	// StringBuilder res = new StringBuilder();
	// for (int i = 0; i < url.length(); i++) {
	// String next = url.substring(i, i + 1);
	// // encode value
	// if (encoder.containsValue(value)(next)) {
	// res.append(encoder.get(next));
	// } else {
	// res.append(next);
	// }
	// }
	// return res.toString();
	// }

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
	 * @param optimlvl
	 * @return bitmap at the target size
	 */
	public static Bitmap decodeSampledBitmap(byte[] data, int reqWidth, int reqHeight, int optimlvl) {
		// get image size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(data, 0, data.length, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
		if (optimlvl >= 1)
			options.inSampleSize *= optimlvl;

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(data, 0, data.length, options);
	}
}
