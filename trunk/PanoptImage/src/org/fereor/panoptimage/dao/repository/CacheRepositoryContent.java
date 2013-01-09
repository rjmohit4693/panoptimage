package org.fereor.panoptimage.dao.repository;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class CacheRepositoryContent extends RepositoryContent {

	private File content;

	public CacheRepositoryContent(File content) {
		this.setContent(content);
	}

	/**
	 * @return the content
	 */
	public File getContent() {
		return content;
	}

	/**
	 * @param content
	 *            the content to set
	 */
	public void setContent(File content) {
		this.content = content;
	}

	/** Destroy content */
	public void destroy() {
		content.delete();
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			content.delete();
		} catch (Exception e) {
			// Do nothing
		}
		super.finalize();
	}

	@Override
	public Bitmap decodeAsBitmap(int reqWidth, int reqHeight, int optimlvl) {
		// Get bitmap size
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(getContent().getAbsolutePath(), options);
		// Calculate inSampleSize
		options.inSampleSize = (int) Math.pow(2d,
				calculateInSampleSize(optimlvl, options, reqWidth, reqHeight));
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(getContent()
				.getAbsolutePath(), options);
		// cached file is no more needed : destroy it
		destroy();
		return bmp;
	}
}
