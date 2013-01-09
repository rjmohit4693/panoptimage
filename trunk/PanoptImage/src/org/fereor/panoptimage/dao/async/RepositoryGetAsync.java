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

import java.lang.ref.WeakReference;

import org.fereor.panoptimage.dao.repository.RepositoryContent;
import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.exception.PanoptimageException;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;

import android.os.AsyncTask;

public class RepositoryGetAsync extends AsyncTask<RepositoryLoaderDao<?>, Long, RepositoryContent> {
	/** path to get */
	private String path;
	/** listener for this task */
	private WeakReference<RepositoryGetListener<Long, RepositoryContent>> listenerRef;

	public RepositoryGetAsync(RepositoryGetListener<Long, RepositoryContent> listener, String path) {
		this.path = path;
		this.listenerRef = new WeakReference<RepositoryGetListener<Long, RepositoryContent>>(listener);
	}

	@Override
	protected RepositoryContent doInBackground(RepositoryLoaderDao<?>... repo) {

		if (listenerRef != null && listenerRef.get() != null) {
			try {
				return repo[0].get(path, listenerRef.get());
			} catch (OutOfMemoryError oem) {
				listenerRef.get().onOEM(oem);
			} catch (PanoptimageFileNotFoundException e) {
				return null;
			} catch (PanoptimageException e) {
				return null;
			}
		}
		return null;
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		super.onProgressUpdate(values);
		if (listenerRef != null && listenerRef.get() != null && !isCancelled())
			listenerRef.get().onGetProgressUpdate(values);
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(RepositoryContent result) {
		if (listenerRef != null && listenerRef.get() != null && !isCancelled())
			listenerRef.get().onPostGet(result);
	}
}
