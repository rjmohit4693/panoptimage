package org.fereor.panoptimage.util;

import org.fereor.panoptimage.R;

/** Enum of error levels */
public enum ToastMsg {

	/** List of Toast messages */
	ERROR(R.drawable.ic_msg_error), WARNING(R.drawable.ic_msg_warn), INFO(R.drawable.ic_msg_info);
	private int imageId;

	private ToastMsg(int imageId) {
		this.imageId = imageId;
	}

	public int id() {
		return imageId;
	}
}
