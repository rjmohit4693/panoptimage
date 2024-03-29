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

package org.fereor.panoptimage.dao.db;

import org.fereor.panoptimage.util.PanoptimageConstants;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

/**
 * Utility class to generate the ormlite configuration file
 * 
 * @author "arnaud.p.fereor"
 */
public class DatabaseConfigUtil extends OrmLiteConfigUtil {
	public static void main(String[] args) throws Exception {
		writeConfigFile("ormlite_config.txt", PanoptimageConstants.DATABASE_CLASSES);
	}
}
