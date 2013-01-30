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
import java.util.Arrays;
import java.util.List;

import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptimagePeerUnverifiedException;
import org.fereor.panoptimage.util.PanoptimageConstants;

import android.os.AsyncTask;

/**
 * Class to browse content of a directory
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class RepositoryDirAsync extends
		AsyncTask<RepositoryLoaderDao<?>, Long, List<String>> {
	/** regular expression for the dir command */
	private String regexp;
	/** listener for this task */
	private WeakReference<RepositoryDirListener<Long, List<String>>> listenerRef;

	public RepositoryDirAsync(
			RepositoryDirListener<Long, List<String>> listener, String regexp) {
		this.regexp = regexp;
		this.listenerRef = new WeakReference<RepositoryDirListener<Long, List<String>>>(
				listener);
	}

	@Override
	protected List<String> doInBackground(RepositoryLoaderDao<?>... repo) {
		if (listenerRef.get() != null) {
			try {
				return repo[0].dir(regexp, listenerRef.get());
			} catch (PanoptimageFileNotFoundException e) {
				return Arrays.asList(PanoptimageConstants.ERROR_FILE_NOT_FOUND);
			} catch (PanoptimagePeerUnverifiedException e) {
				return Arrays.asList(PanoptimageConstants.ERROR_PEER_UNVERIFIED);
			}
		}
		return null;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(List<String> result) {
		if (listenerRef != null && !isCancelled())
			listenerRef.get().onPostDir(result);
	}

	@Override
	protected void onPreExecute() {
		if (listenerRef != null)
			listenerRef.get().onPreDir();
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		super.onProgressUpdate(values);
		if (listenerRef != null && !isCancelled())
			listenerRef.get().onDirProgressUpdate(values[0]);
	}

}
