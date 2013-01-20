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

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class PanoptimageMsg {
	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public static void showErrorMsg(Context ctx, int stringRes, Object... args) {
		String msg = ctx.getString(stringRes, args);
		showErrorMsg(ctx, msg);
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public static void showErrorMsg(Context ctx, Exception e) {
		showErrorMsg(ctx, e.toString());
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public static void showInfoMsg(Context ctx, int stringRes, Object... args) {
		String msg = ctx.getString(stringRes, args);
		showInfoMsg(ctx, msg);
	}

	/**
	 * Display a message
	 * 
	 * @param msg message to display
	 */
	public static void showInfoMsg(Context ctx, String msg) {
		Log.d(PanoptesConstants.TAGNAME, "Info:" + msg);
		Toast.makeText(ctx, msg, Toast.LENGTH_LONG).show();
	}

	/**
	 * Display an error message
	 * 
	 * @param e
	 */
	public static void showErrorMsg(Context ctx, String msg) {
		Log.d(PanoptesConstants.TAGNAME, "Error:" + msg);
		Toast.makeText(ctx, ctx.getString(R.string.error_header, msg), Toast.LENGTH_LONG).show();
	}

}
