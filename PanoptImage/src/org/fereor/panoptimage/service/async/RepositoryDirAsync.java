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

import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.service.RepositoryService;

import android.os.AsyncTask;

/**
 * Class to browse content of a directory
 * @author "arnaud.p.fereor"
 *
 */
public class RepositoryDirAsync extends AsyncTask<RepositoryService<?>, Integer, List<String>> {
	/** regular expression for the dir command */
	private String regexp;
	/** listener for this task */
	RepositoryDirListener<Integer, List<String>> listener;

	public RepositoryDirAsync(RepositoryDirListener<Integer, List<String>> listener, String regexp) {
		this.regexp = regexp;
		this.listener = listener;
	}

	@Override
	protected List<String> doInBackground(RepositoryService<?>... repo) {
		try {
			return repo[0].dir(regexp);
		} catch (PanoptimageFileNotFoundException e) {
			return null;
		}
	}

	@Override
	protected void onCancelled() {
		// TODO Auto-generated method stub
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
	protected void onProgressUpdate(Integer... values) {
		// TODO Auto-generated method stub
		super.onProgressUpdate(values);
		listener.onDirProgressUpdate(values);
	}

}
