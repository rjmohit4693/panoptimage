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

import java.lang.ref.WeakReference;

import org.fereor.panoptimage.exception.PanoptimageException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.service.RepositoryService;

import android.os.AsyncTask;

public class RepositoryGetAsync extends AsyncTask<RepositoryService<?>, Long, byte[]> {
	/** path to get */
	private String path;
	/** listener for this task */
	WeakReference<RepositoryGetListener<Long, byte[]>> listener;

	public RepositoryGetAsync(RepositoryGetListener<Long, byte[]> listener, String path) {
		this.path = path;
		this.listener = new WeakReference<RepositoryGetListener<Long, byte[]>>(listener);
	}

	@Override
	protected byte[] doInBackground(RepositoryService<?>... repo) {
		try {
			if (listener != null && listener.get() != null)
				return repo[0].get(path, listener.get());
			return null;
		} catch (PanoptimageFileNotFoundException e) {
			return null;
		} catch (PanoptimageException e) {
			return null;
		}
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		if (listener != null && listener.get() != null)
			listener.get().onGetProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(byte[] result) {
		if (listener != null && listener.get() != null)
			listener.get().onPostGet(result);
	}
}
