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

package org.fereor.panoptimage.util.network;

import java.util.List;

public interface ScanListener {
	/**
	 * Called when scan progress
	 * 
	 * @param progress (0.0 at the beginning, 1.0 at the end)
	 */
	void onScanProgress(float max, float progress);

	/**
	 * Called when scan is finished
	 * 
	 * @param result result of scan
	 */
	void onScanFinished(List<HotSite> result);

}
