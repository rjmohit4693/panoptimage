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

package org.fereor.panoptimage.util;

import org.fereor.panoptimage.R;
import org.fereor.panoptimage.activity.create.CreateFragment;
import org.fereor.panoptimage.activity.create.EmptyCreateFragment;
import org.fereor.panoptimage.activity.create.LocalCreateFragment;
import org.fereor.panoptimage.activity.create.WebdavCreateFragment;
import org.fereor.panoptimage.model.CreateParam;

public enum PanoptimageTypeEnum {
	/**
	 * List of available fragments for the Create activity
	 */
	EMPTY(
			0,
			R.string.create_new_default,
			R.layout.fragment_create_empty,
			R.drawable.ic_empty_storage,
			R.drawable.ic_empty_storage_xs,
			EmptyCreateFragment.class),
	LOCAL(
			1,
			R.string.create_new_local,
			R.layout.fragment_create_local,
			R.drawable.ic_local_storage,
			R.drawable.ic_local_storage_xs,
			LocalCreateFragment.class),
	WEBDAV(
			2,
			R.string.create_new_webdav,
			R.layout.fragment_create_webdav,
			R.drawable.ic_webdav_storage,
			R.drawable.ic_webdav_storage_xs,
			WebdavCreateFragment.class);

	/** ID (used internally for DB) */
	private int id;
	/** ID of spinner text */
	private int key;
	/** ID of fragment layout */
	private int layout;
	/** ID of icon */
	private int icon;
	/** ID of icon extra small */
	private int iconxs;
	/** class of fragment to instantiate */
	private Class<? extends CreateFragment<? extends CreateParam>> fragmentClass;


	/**
	 * Private constructor for the fragments of the Create activity
	 * 
	 * @param <T>
	 * @param id id of fragment (used to store internally in DB)
	 * @param spinnerText ID for the spinner
	 * @param layout ID for the fragment layout
	 * @param fragmentClass Class for the fragment to create it
	 */
	private PanoptimageTypeEnum(int id, int spinnerText, int layout, int icon, int iconxs,
			Class<? extends CreateFragment<? extends CreateParam>> fragmentClass) {
		this.id = id;
		this.key = spinnerText;
		this.layout = layout;
		this.icon = icon;
		this.iconxs = iconxs;
		this.fragmentClass = fragmentClass;
	}

	/**
	 * Getter for ID
	 * 
	 * @return id of fragment
	 */
	public int id() {
		return id;
	}

	/**
	 * Getter for spinnerText
	 * 
	 * @return spinner text ID
	 */
	public int key() {
		return key;
	}

	/**
	 * Getter for layout ID
	 * 
	 * @return layout
	 */
	public int layout() {
		return layout;
	}

	/**
	 * Getter for icon ID
	 * 
	 * @return icon ID
	 */
	public int icon() {
		return icon;
	}

	/**
	 * Getter for iconxs ID
	 * 
	 * @return iconxs ID
	 */
	public int iconxs() {
		return iconxs;
	}

	/**
	 * Creates a new fragment of given type
	 * 
	 * @return new instance of the fragment
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 */
	public CreateFragment<?> instance() throws InstantiationException, IllegalAccessException {
		return fragmentClass.newInstance();
	}
}
