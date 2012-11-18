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

import java.util.ArrayList;
import java.util.List;

import org.fereor.panoptimage.exception.PanoptesException;
import org.fereor.panoptimage.exception.PanoptesFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.util.PanoptesHelper;

/**
 * Interface able to analyze the content of a repository
 * 
 * @author "arnaud.p.fereor"
 */
public abstract class RepositoryService<T> {
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
	 */
	public static RepositoryService<?> createInstance(HomePagerParam content) throws PanoptesUnknownParamException {
		// case of type LocalParam
		if (content.getParam() instanceof LocalParam)
			return new LocalService((LocalParam) content.getParam());
		// case of type WebdavParam
		if (content.getParam() instanceof WebdavParam)
			return new WebdavService((WebdavParam) content.getParam());
		// if type is unknown : throw error
		throw new PanoptesUnknownParamException();
	}

	/**
	 * Constructor for child classes
	 * 
	 * @param param parameter to use in the class
	 */
	protected RepositoryService(T param) {
		this.param = param;
	}

	/**
	 * Change the repository location
	 * 
	 * @param path path to navigate to
	 * @return list of child locations
	 */
	public void cd(String path) throws PanoptesException {
		if (path == null) {
			// test param values
			return;
		} else if (PanoptesHelper.DOT.equals(path.trim())) {
			// test param values
			return;
		} else if (PanoptesHelper.DDOT.equals(path.trim()) && currentPath.size() != 0) {
			// if currentpath is not empty an .. is required
			currentPath.remove(currentPath.size() - 1);
		} else if (!path.trim().contains(PanoptesHelper.SLASH)) {
			// if currentpath does not contain a /, add it
			currentPath.add(path.trim());
		} else {
			// TODO : implement recursive cd
		}

	}

	/**
	 * Read the content of current path location
	 * 
	 * @param regexp regular expression to match to search for files
	 * @return list of child locations
	 */
	public abstract String[] dir(String regexp) throws PanoptesFileNotFoundException;

	/**
	 * Get the content of a repository location
	 * 
	 * @param location location to get
	 * @return byte content of location
	 */
	public abstract byte[] get(String location) throws PanoptesException;

	/**
	 * Determines if we should dispaly a splash screen while loading
	 * 
	 * @return true is the splash has to be displayed
	 */
	public abstract boolean showSplashWhileLoading();
}
