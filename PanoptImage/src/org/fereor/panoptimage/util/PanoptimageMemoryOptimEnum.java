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

public enum PanoptimageMemoryOptimEnum {
	/**
	 * List of available memory optimization options
	 */
	Auto(
			1,
			R.string.param_memory_auto,
			R.drawable.ic_auto),
	Level_2(
			2,
			R.string.param_memory_2,
			R.drawable.ic_level2),
	Level_4(
			4,
			R.string.param_memory_4,
			R.drawable.ic_level4),
	Level_8(
			8,
			R.string.param_memory_8,
			R.drawable.ic_level8),
	Level_16(
			16,
			R.string.param_memory_16,
			R.drawable.ic_max);

	/**
	 * Find the position of an item matching the value
	 * 
	 * @param opt
	 * @return
	 */
	public static final int findPosition(int key) {
		for (int i = 0; i < values().length; i++) {
			if (values()[i].key == key) {
				return i;
			}
		}
		return 0;
	}

	/** key for database */
	private int key;
	/** text to display */
	private int text;
	/** icon to display */
	private int ic;

	/**
	 * Default constructor
	 * 
	 * @param key
	 */
	private PanoptimageMemoryOptimEnum(int key, int text, int icon) {
		this.setKey(key);
		this.setText(text);
		this.setIc(icon);
	}

	/**
	 * @return the key
	 */
	public int key() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	private void setKey(int key) {
		this.key = key;
	}

	/**
	 * @return the text
	 */
	public int text() {
		return text;
	}

	/**
	 * @param text the text to set
	 */
	private void setText(int text) {
		this.text = text;
	}

	/**
	 * @param text the text to set
	 */
	private void setIc(int ic) {
		this.ic = ic;
	}

	/**
	 * @return the text
	 */
	public int ic() {
		return ic;
	}

}
