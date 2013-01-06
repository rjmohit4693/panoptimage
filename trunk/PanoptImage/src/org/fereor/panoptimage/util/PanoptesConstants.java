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

package org.fereor.panoptimage.util;

import org.fereor.panoptimage.model.Config;
import org.fereor.panoptimage.model.LocalParam;
import org.fereor.panoptimage.model.WebdavParam;

/**
 * Useful constants for Panoptes
 * 
 * @author "arnaud.p.fereor"
 */
public interface PanoptesConstants {
	// List of classes available for ORM
	public static final Class<?>[] DATABASE_CLASSES = new Class[] { Config.class, WebdavParam.class, LocalParam.class };
	// Tag for logs
	public static final String TAGNAME = "Panoptes";
	// Tag for image intent
	public static final String MSG_HOME = "org.fereor.panoptes.homeintent";
	// Number of steps for onProgress loading
	public static final float ONPROGRESS_STEPS = 5.0f;
	// sub directory for cache files
	public static final String CACHE_DIR = "cache";
}
