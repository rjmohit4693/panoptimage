// This file is part of DavDroid.
//
// DavDroid is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// DavDroid is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with DavDroid.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.davdroid.exception;

/**
 * Class to mark all methods unimplemented
 * @author "arnaud.p.fereor"
 *
 */
public class DavDroidUnimplementedException extends DavDroidException {
	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Constructor
	 * @param message message for exception
	 */
	public DavDroidUnimplementedException(String message) {
		super(message);
	}

}