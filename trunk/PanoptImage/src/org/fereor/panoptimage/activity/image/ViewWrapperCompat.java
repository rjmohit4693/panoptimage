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

package org.fereor.panoptimage.activity.image;

import java.lang.reflect.Method;

import android.view.View;

/**
 * Class as a wrapper for View for API before 11
 * 
 * @author "arnaud.p.fereor"
 */
public class ViewWrapperCompat {
	private static ViewWrapperCompat instance = null;
	private Method ViewSetAlpha = null;
	private Method ViewSetScaleX = null;
	private Method ViewSetScaleY = null;
	private Method ViewSetRotationX = null;
	private Method ViewSetRotationY = null;
	private Method ViewSetPivotX = null;
	private Method ViewSetPivotY = null;

	/**
	 * Constructor of static instance
	 * 
	 * @return static instance of this singleton
	 */
	public static ViewWrapperCompat getInstance() {
		if (instance == null) {
			// create instance of singleton
			instance = new ViewWrapperCompat();
			try {
				instance.ViewSetAlpha = View.class.getMethod("setAlpha", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetScaleX = View.class.getMethod("setScaleX", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetScaleY = View.class.getMethod("setScaleY", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetRotationX = View.class.getMethod("setRotationX", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetRotationY = View.class.getMethod("setRotationY", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetPivotX = View.class.getMethod("setPivotX", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
			try {
				instance.ViewSetPivotY = View.class.getMethod("setPivotY", new Class[] { float.class });
			} catch (NoSuchMethodException nsme) {
				/* failure, must be older device */
			}
		}
		return instance;
	}

	/**
	 * Try to set alpha on view
	 * 
	 * @param v view
	 * @param alpha
	 */
	public void setAlpha(View v, float alpha) {
		if (ViewSetAlpha != null) {
			try {
				ViewSetAlpha.invoke(v, alpha);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}
	}

	/**
	 * Try to set scaleX on view
	 * 
	 * @param v view
	 * @param scaleX
	 */
	public void setScaleX(View v, float scaleX) {
		if (ViewSetScaleX != null) {
			try {
				ViewSetScaleX.invoke(v, scaleX);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

	/**
	 * Try to set scaleY on view
	 * 
	 * @param v view
	 * @param scaleY
	 */
	public void setScaleY(View v, float scaleY) {
		if (ViewSetScaleY != null) {
			try {
				ViewSetScaleY.invoke(v, scaleY);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

	/**
	 * Try to set rotationX on view
	 * 
	 * @param v view
	 * @param rotationX
	 */
	public void setRotationX(View v, float rotationX) {
		if (ViewSetRotationX != null) {
			try {
				ViewSetRotationX.invoke(v, rotationX);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

	/**
	 * Try to set rotationY on view
	 * 
	 * @param v view
	 * @param rotationY
	 */
	public void setRotationY(View v, float rotationY) {
		if (ViewSetRotationY != null) {
			try {
				ViewSetRotationY.invoke(v, rotationY);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

	/**
	 * Try to set pivotX on view
	 * 
	 * @param v view
	 * @param pivotX
	 */
	public void setPivotX(View v, float pivotX) {
		if (ViewSetPivotX != null) {
			try {
				ViewSetPivotX.invoke(v, pivotX);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

	/**
	 * Try to set pivotY on view
	 * 
	 * @param v view
	 * @param pivotY
	 */
	public void setPivotY(View v, float pivotY) {
		if (ViewSetPivotY != null) {
			try {
				ViewSetPivotY.invoke(v, pivotY);
			} catch (Exception e) {
				// no luck : do nothing
			}
		}

	}

}
