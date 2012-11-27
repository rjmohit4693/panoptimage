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

package org.fereor.panoptimage.service;

import org.fereor.panoptimage.model.CreateParam;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Class to store the content to build the Home activity
 * 
 * @author "arnaud.p.fereor"
 */
public class HomePagerParamService implements Parcelable {
	private CreateParam param;
	private String message;
	private int imgid;

	public HomePagerParamService(CreateParam param, String message, int imgid) {
		this.param = param;
		this.message = message;
		this.imgid = imgid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String messageid) {
		this.message = messageid;
	}

	public int getImgid() {
		return imgid;
	}

	public void setImgid(int imgid) {
		this.imgid = imgid;
	}

	public CreateParam getParam() {
		return param;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeInt(imgid);
		dest.writeString(message);
		dest.writeSerializable(param);
	}

	/**
	 * Creator of the parcelable
	 */
	public static final Parcelable.Creator<HomePagerParamService> CREATOR = new Parcelable.Creator<HomePagerParamService>() {
		public HomePagerParamService createFromParcel(Parcel in) {
			return new HomePagerParamService(in);
		}

		public HomePagerParamService[] newArray(int size) {
			return new HomePagerParamService[size];
		}
	};

	/**
	 * private constructor for internal use only
	 * 
	 * @param in parcel to read from
	 */
	private HomePagerParamService(Parcel in) {
		imgid = in.readInt();
		message = in.readString();
		param = (CreateParam)in.readSerializable();

	}
}
