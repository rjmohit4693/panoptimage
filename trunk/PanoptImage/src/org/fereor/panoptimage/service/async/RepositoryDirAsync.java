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

import java.util.List;

import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;

import android.os.AsyncTask;

/**
 * Class to browse content of a directory
 * @author "arnaud.p.fereor"
 *
 */
public class RepositoryDirAsync extends AsyncTask<RepositoryLoaderDao<?>, Long, List<String>> {
	/** regular expression for the dir command */
	private String regexp;
	/** listener for this task */
	RepositoryDirListener<Long, List<String>> listener;

	public RepositoryDirAsync(RepositoryDirListener<Long, List<String>> listener, String regexp) {
		this.regexp = regexp;
		this.listener = listener;
	}

	@Override
	protected List<String> doInBackground(RepositoryLoaderDao<?>... repo) {
		try {
			return repo[0].dir(regexp, listener);
		} catch (PanoptimageFileNotFoundException e) {
			return null;
		}
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(List<String> result) {
		listener.onPostDir(result);
	}

	@Override
	protected void onPreExecute() {
		listener.onPreDir();
	}

	@Override
	protected void onProgressUpdate(Long... values) {
		super.onProgressUpdate(values);
		listener.onDirProgressUpdate(values[0]);
	}

}
