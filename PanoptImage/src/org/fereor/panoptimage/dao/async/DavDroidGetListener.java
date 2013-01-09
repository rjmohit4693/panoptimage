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

package org.fereor.panoptimage.dao.async;

import org.fereor.davdroid.DavDroidListener;

public class DavDroidGetListener<T, U> implements DavDroidListener<T> {
	RepositoryGetListener<T, U> parent;
	private float steps;
	/**
	 * Default constructor
	 * @param parent parent listener
	 */
	public DavDroidGetListener(RepositoryGetListener<T, U> parent, float steps) {
		super();
		this.parent = parent;
		this.steps = steps;
	}

	@Override
	public void onProgress(T... values) {
		parent.onGetProgressUpdate(values);
	}

	@Override
	public float nbSteps() {
		return steps;
	}

}
