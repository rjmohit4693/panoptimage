package org.fereor.panoptimage.service.async;

import org.fereor.davdroid.DavDroidListener;

public class DavDroidGetListener<T, U> implements DavDroidListener<T> {
	RepositoryGetListener<T, U> parent;
	/**
	 * Default constructor
	 * @param parent parent listener
	 */
	public DavDroidGetListener(RepositoryGetListener<T, U> parent) {
		super();
		this.parent = parent;
	}

	@Override
	public void onProgress(T... values) {
		parent.onGetProgressUpdate(values);
		
	}

}
