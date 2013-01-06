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
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.service.async.RepositoryDirListener;
import org.fereor.panoptimage.service.async.RepositoryGetListener;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.fereor.panoptimage.util.RegexpFilenameFilter;

/**
 * Implementation of Repository Service for local repository
 * 
 * @author "arnaud.p.fereor"
 */
public class LocalRepositoryDao extends RepositoryLoaderDao<LocalParam> {
	/**
	 * Default constructor with param data
	 * 
	 * @param param
	 */
	public LocalRepositoryDao(LocalParam param) {
		super(param);
		if (!param.getPath().endsWith(PanoptesHelper.SLASH)) {
			this.root = param.getPath() + PanoptesHelper.SLASH;
		} else {
			this.root = param.getPath();
		}
	}

	@Override
	public List<String> dir(String regexp, RepositoryDirListener<Long, List<String>> lsn)
			throws PanoptimageFileNotFoundException {
		// browse directory location
		String location = PanoptesHelper.formatPath(root, currentPath, PanoptesHelper.SLASH);
		File locationFile = new File(location);
		FilenameFilter filter = new RegexpFilenameFilter(regexp);
		String[] result = locationFile.list(filter);

		// if result is empty : throw error
		if (result == null) {
			throw new PanoptimageFileNotFoundException(locationFile.getAbsolutePath());
		}
		return Arrays.asList(result);
	}

	@Override
	public RepositoryContent get(String filename, RepositoryGetListener<Long, RepositoryContent> lsn) {
		// read content file
		File currentDir = new File(PanoptesHelper.formatPath(root, currentPath));
		File locationFile = new File(currentDir, filename);

		return new FileRepositoryContent(locationFile);

	}

	@Override
	public boolean exists(String path) {
		// read content file
		File currentDir = new File(PanoptesHelper.formatPath(root, currentPath, path));
		return currentDir.exists();
	}

	@Override
	public boolean isDirectory(String path) {
		File currentDir = new File(PanoptesHelper.formatPath(root, currentPath, path));
		return currentDir.isDirectory();
	}

	@Override
	public boolean showSplashWhileLoading() {
		return false;
	}
}
