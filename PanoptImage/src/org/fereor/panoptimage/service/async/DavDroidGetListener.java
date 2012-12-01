package org.fereor.panoptimage.service.async;

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
