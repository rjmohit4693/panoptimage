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

import java.util.List;

/**
 * Utility class to keep several static methods
 * 
 * @author "arnaud.p.fereor"
 */
public class PanoptesHelper {
	public static final String DOT = ".";
	public static final String DDOT = "..";
	public static final String SLASH = "/";
	public static final String REGEXP_ALLIMAGES = ".+\\.jpg|.+\\.jpeg|.+\\.png|.+\\.gif";
	public static final String REGEXP_DIRECTORY = "^[^\\\\.]*$";

	/**
	 * Format a string to a path using SLASH
	 * 
	 * @param path
	 * @return
	 */
	public static final String formatPath(List<String> path) {
		StringBuilder str = new StringBuilder(SLASH);
		for (String cur : path) {
			str.append(cur).append(SLASH);
		}
		return str.toString();
	}

}
