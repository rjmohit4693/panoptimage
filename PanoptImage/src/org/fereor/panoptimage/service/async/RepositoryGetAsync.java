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

package org.fereor.panoptimage.service.async;

import java.io.InputStream;

import org.fereor.panoptimage.exception.PanoptimageException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.service.RepositoryService;

import android.os.AsyncTask;

public class RepositoryGetAsync extends AsyncTask<RepositoryService<?>, Integer, InputStream> {
	/** path to get */
	private String path;
	/** listener for this task */
	RepositoryGetListener<Integer, InputStream> listener;

	public RepositoryGetAsync(RepositoryGetListener<Integer, InputStream> listener, String path) {
		this.path = path;
		this.listener = listener;
	}

	@Override
	protected InputStream doInBackground(RepositoryService<?>... repo) {
		try {
			return repo[0].get(path);
		} catch (PanoptimageFileNotFoundException e) {
			return null;
		} catch (PanoptimageException e) {
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		listener.onGetProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(InputStream result) {
		listener.onPostGet(result);
	}
}
