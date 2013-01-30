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

import org.fereor.panoptimage.exception.PanoptesUnknownParamException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.exception.PanoptimagePeerUnverifiedException;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.service.HomePagerParamService;

public class RepositoryLoaderFactory {
	/**
	 * Create a new repository service matching the type passed
	 * 
	 * @return
	 * @throws PanoptesUnknownParamException
	 * @throws PanoptimageNoNetworkException
	 * @throws PanoptimagePeerUnverifiedException 
	 * @throws
	 */
	public static RepositoryLoaderDao<?> createInstance(
			HomePagerParamService content, File cachedir)
			throws PanoptesUnknownParamException, PanoptimageNoNetworkException, PanoptimagePeerUnverifiedException {
		// case of type LocalParam
		if (content.getParam() instanceof LocalParam)
			return new LocalRepositoryDao((LocalParam) content.getParam());
		// case of type WebdavParam
		if (content.getParam() instanceof WebdavParam)
			return new WebdavRepositoryDao((WebdavParam) content.getParam(),
					cachedir);
		// if type is unknown : throw error
		throw new PanoptesUnknownParamException();
	}

}
