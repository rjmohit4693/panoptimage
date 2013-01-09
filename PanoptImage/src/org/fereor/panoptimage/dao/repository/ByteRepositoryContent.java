package org.fereor.panoptimage.dao.repository;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class ByteRepositoryContent extends RepositoryContent {

	/** byte buffer */
	private byte[] bytes;

	/**
	 * Default constructor
	 * 
	 * @param array
	 *            array of bytes
	 */
	public ByteRepositoryContent(byte[] array) {
		this.bytes = array;
	}

	@Override
	public Bitmap decodeAsBitmap(int reqWidth, int reqHeight, int optimlvl) {
		// Get bitmap size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
		// Calculate inSampleSize
		options.inSampleSize = (int) Math.pow(2d,
				calculateInSampleSize(optimlvl, options, reqWidth, reqHeight));
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
	}

}
