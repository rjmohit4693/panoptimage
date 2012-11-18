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

import org.fereor.panoptimage.exception.PanoptesException;
import org.fereor.panoptimage.exception.PanoptesFileNotFoundException;
import org.fereor.panoptimage.model.WebdavParam;

/**
 * Implementation of Repository Service for Webdav repository
 * 
 * @author "arnaud.p.fereor"
 */
public class WebdavService extends RepositoryService<WebdavParam> {
	/**
	 * Default constructor with param data
	 * 
	 * @param param
	 */
	public WebdavService(WebdavParam param) {
		super(param);
		this.root = param.getPath();
	}

	@Override
	public String[] dir(String regexp) throws PanoptesFileNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] get(String location) throws PanoptesException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean showSplashWhileLoading() {
		return false;
	}
}
