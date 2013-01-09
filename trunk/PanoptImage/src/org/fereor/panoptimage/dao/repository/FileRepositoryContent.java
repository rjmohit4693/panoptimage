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

package org.fereor.panoptimage.dao.repository;

import java.io.File;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

public class FileRepositoryContent extends RepositoryContent {

	private File content;

	public FileRepositoryContent(File content) {
		this.content = content;
	}

	@Override
	public Bitmap decodeAsBitmap(int reqWidth, int reqHeight, int optimlvl) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(content.getAbsolutePath(), options);
		// Calculate inSampleSize
		options.inSampleSize = (int) Math.pow(2d,
				calculateInSampleSize(optimlvl, options, reqWidth, reqHeight));
		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory
				.decodeFile(content.getAbsolutePath(), options);
	}
}
