package org.fereor.panoptimage.dao.repository;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileRepositoryContent extends RepositoryContent {

	private File content;

	public FileRepositoryContent(File content) {
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

	@Override
	public Bitmap decodeAsBitmap(int reqWidth, int reqHeight, int optimlvl) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(getContent().getAbsolutePath(), options);
		// Calculate inSampleSize
		options.inSampleSize = (int) Math.pow(2d,
				calculateInSampleSize(optimlvl, options, reqWidth, reqHeight));
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory
				.decodeFile(getContent().getAbsolutePath(), options);
	}
}
