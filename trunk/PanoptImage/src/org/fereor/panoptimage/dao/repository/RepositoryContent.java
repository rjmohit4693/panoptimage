package org.fereor.panoptimage.dao.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public abstract class RepositoryContent {
	/**
	 * Decode information from this content as a bitmap
	 * @param reqWidth width required
	 * @param reqHeight height required
	 * @param optimlvl optimization level required
	 * @return bitmap representation of the data
	 */
	public abstract Bitmap decodeAsBitmap(int reqWidth, int reqHeight,
			int optimlvl);
	
	/**
	 * Calculates the sample size of image
	 * 
	 * @param optimlvl
	 * @param options
	 * @param reqWidth
	 * @param reqHeight
	 * @return
	 */
	protected double calculateInSampleSize(int optimlvl, BitmapFactory.Options options, int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		double inSampleSize = 1;

		// Compute optimization level as closer power of 2
		if (height > reqHeight || width > reqWidth) {
			if (width > height) {
				inSampleSize = Math.ceil(Math.log((double) height / (double) reqHeight) / Math.log(2));
			} else {
				inSampleSize = Math.ceil(Math.log((double) width / (double) reqWidth) / Math.log(2));
			}
		}
		return inSampleSize * optimlvl;
	}
}
