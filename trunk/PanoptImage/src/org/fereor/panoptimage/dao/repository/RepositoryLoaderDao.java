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

import java.util.ArrayList;
import java.util.List;

import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.exception.PanoptimageException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.service.HomePagerParamService;
import org.fereor.panoptimage.service.async.RepositoryDirListener;
import org.fereor.panoptimage.service.async.RepositoryGetListener;
import org.fereor.panoptimage.util.PanoptesHelper;

/**
 * Interface able to analyze the content of a repository
 * 
 * @author "arnaud.p.fereor"
 */
public abstract class RepositoryLoaderDao<T> {
	/** parent parameter */
	protected T param;
	/** Root of directory to browse */
	protected String root;
	/** Current path */
	protected List<String> currentPath = new ArrayList<String>();

	/**
	 * Create a new repository service matching the type passed
	 * 
	 * @return
	 * @throws PanoptesUnknownParamException
	 * @throws PanoptimageNoNetworkException
	 * @throws
	 */
	public static RepositoryLoaderDao<?> createInstance(HomePagerParamService content)
			throws PanoptesUnknownParamException, PanoptimageNoNetworkException {
		// case of type LocalParam
		if (content.getParam() instanceof LocalParam)
			return new LocalRepositoryDao((LocalParam) content.getParam());
		// case of type WebdavParam
		if (content.getParam() instanceof WebdavParam)
			return new WebdavRepositoryDao((WebdavParam) content.getParam());
		// if type is unknown : throw error
		throw new PanoptesUnknownParamException();
	}

	/**
	 * Constructor for child classes
	 * 
	 * @param param parameter to use in the class
	 */
	protected RepositoryLoaderDao(T param) {
		this.param = param;
	}

	/**
	 * Change the repository location
	 * 
	 * @param path path to navigate to
	 * @return list of child locations
	 */
	public void cd(String path) {
		// test param values
		if (path == null || path.length() == 0) {
			return;
		} // else if (!isDirectory(path)) {
			// try to access to current path
			// throw new PanoptimageFileNotFoundException(path);
		// }
		else if (PanoptesHelper.DOT.equals(path.trim())) {
			// test param values
			return;
		} else if (PanoptesHelper.DDOT.equals(path.trim()) && currentPath.size() != 0) {
			// if currentpath is not empty and .. is required
			currentPath.remove(currentPath.size() - 1);
		} else if (!path.trim().contains(PanoptesHelper.SLASH)) {
			// if currentpath does not contain a /, add it
			currentPath.add(path.trim());
		} else {
			// recursive cd
			String subpath = path.substring(0, path.indexOf(PanoptesHelper.SLASH));
			String postpath = path.substring(path.indexOf(PanoptesHelper.SLASH) + 1, path.length());
			cd(subpath);
			cd(postpath);
		}
	}

	/**
	 * Returns the formatted value of the path
	 * 
	 * @return path formatted
	 */
	public String getformatedPath() {
		return PanoptesHelper.formatPath(currentPath);
	}
	
	/**
	 * Returns true if the path is root
	 * @return
	 */
	public boolean isRoot(){
		return currentPath.size() == 0;
	}

	/**
	 * Read the content of current path location
	 * 
	 * @param regexp regular expression to match to search for files
	 * @return list of child locations
	 */
	public abstract List<String> dir(String regexp, RepositoryDirListener<Long, List<String>> lsn)
			throws PanoptimageFileNotFoundException;

	/**
	 * Get the content of a repository location
	 * 
	 * @param location location to get
	 * @return byte content of location
	 */
	public abstract byte[] get(String location, RepositoryGetListener<Long, byte[]> lsn) throws PanoptimageException;

	/**
	 * Tests if a path exists (absolute or relative)
	 * 
	 * @param path path to test
	 * @return true if the path exists
	 * @throws PanoptimageFileNotFoundException
	 */
	public abstract boolean exists(String path);

	/**
	 * Tests if the path is a directory
	 * 
	 * @param path path to test
	 * @return true if the path is a directory
	 */
	public abstract boolean isDirectory(String path);

	/**
	 * Determines if we should dispaly a splash screen while loading
	 * 
	 * @return true is the splash has to be displayed
	 */
	public abstract boolean showSplashWhileLoading();

}
