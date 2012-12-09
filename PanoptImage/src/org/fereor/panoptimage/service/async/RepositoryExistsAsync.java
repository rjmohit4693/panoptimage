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

import org.fereor.panoptimage.dao.repository.RepositoryLoaderDao;

import android.os.AsyncTask;

/**
 * Class to browse content of a directory
 * 
 * @author "arnaud.p.fereor"
 */
public class RepositoryExistsAsync extends AsyncTask<RepositoryLoaderDao<?>, Integer, Boolean> {
	/** regular expression for the dir command */
	private String path;
	/** listener for this task */
	RepositoryExistsListener<Integer, Boolean> listener;

	public RepositoryExistsAsync(RepositoryExistsListener<Integer, Boolean> listener, String path) {
		this.path = path;
		this.listener = listener;
	}

	@Override
	protected Boolean doInBackground(RepositoryLoaderDao<?>... repo) {
		return repo[0].exists(path);
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
		super.onCancelled();
	}

	@Override
	protected void onPostExecute(Boolean result) {
		listener.onPostExists(result);
	}

	@Override
	protected void onPreExecute() {
		listener.onPreExists();
	}

	@Override
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		listener.onExistsProgressUpdate(values[0]);
	}

}
