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

package org.fereor.panoptimage.service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.IOException;

import org.fereor.panoptimage.exception.PanoptesException;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.fereor.panoptimage.util.RegexpFilenameFilter;

/**
 * Implementation of Repository Service for local repository
 * 
 * @author "arnaud.p.fereor"
 */
public class LocalService extends RepositoryService<LocalParam> {
	/**
	 * Default constructor with param data
	 * 
	 * @param param
	 */
	public LocalService(LocalParam param) {
		super(param);
		this.root = param.getPath();
	}

	@Override
	public String[] dir(String regexp) throws PanoptesException {
		// browse directory location
		File locationFile = new File(root + PanoptesHelper.formatPath(currentPath));
		FilenameFilter filter = new RegexpFilenameFilter(regexp);
		return locationFile.list(filter);
	}

	@Override
	public byte[] get(String filename) throws PanoptesException {
		try {
			// read content file
			File currentDir = new File(root + PanoptesHelper.formatPath(currentPath));
			File locationFile = new File(currentDir, filename);
			FileInputStream fis = new FileInputStream(locationFile);
			// put content in a byte array
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buf = new byte[1024];
			int cnt;
			while ((cnt = fis.read(buf)) > 0) {
				baos.write(buf, 0, cnt);
			}
			fis.close();
			baos.close();

			return baos.toByteArray();
		} catch (IOException e) {
			throw new PanoptesException(e.toString());
		}
	}

	@Override
	public boolean showSplashWhileLoading() {
		return false;
	}

}
