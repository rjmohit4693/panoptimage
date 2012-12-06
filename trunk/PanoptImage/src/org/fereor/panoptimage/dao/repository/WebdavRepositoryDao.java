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

package org.fereor.panoptimage.dao.repository;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpHost;
import org.fereor.davdroid.DavDroid;
import org.fereor.davdroid.DavDroidFactory;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.http.xml.i.Prop;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.service.async.DavDroidGetListener;
import org.fereor.panoptimage.service.async.RepositoryDirListener;
import org.fereor.panoptimage.service.async.RepositoryGetListener;
import org.fereor.panoptimage.util.PanoptesConstants;
import org.fereor.panoptimage.util.PanoptesHelper;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

/**
 * Implementation of Repository Service for Webdav repository
 * 
 * @author "arnaud.p.fereor"
 */
public class WebdavRepositoryDao extends RepositoryLoaderDao<WebdavParam> {

	/** base of webdav link */
	DavDroid dav = null;

	/**
	 * Default constructor with param data
	 * 
	 * @param param
	 */
	public WebdavRepositoryDao(WebdavParam param) throws PanoptimageNoNetworkException {
		super(param);
		// set root path
		if (!param.getBase().endsWith(PanoptesHelper.SLASH)) {
			this.root = param.getBase() + PanoptesHelper.SLASH;
		} else {
			this.root = param.getBase();
		}
		if (!param.getPath().endsWith(PanoptesHelper.SLASH)) {
			this.root = this.root + param.getPath() + PanoptesHelper.SLASH;
		} else {
			this.root = this.root + param.getPath();
		}

		// create base URL
		int defaultHttpPort = 80;
		if (param.getPort() != null && !param.getPort().isEmpty()) {
			defaultHttpPort = Integer.parseInt(param.getPort());
		}
		HttpHost host = new HttpHost(param.getServer(), defaultHttpPort, param.getProtocol());
		// create Webdav link
		try {
			dav = DavDroidFactory.init(host, param.getUser(), param.getPwd());
		} catch (XmlPullParserException e) {
			throw new PanoptimageNoNetworkException(param.getServer());
		}
	}

	@Override
	public List<String> dir(String regexp, RepositoryDirListener<Long, List<String>> lsn)
			throws PanoptimageFileNotFoundException {
		try {
			// prepare request
			String val = PanoptesHelper.formatPath(root, currentPath);
			URI uri = new URI(param.getProtocol(), param.getServer(), val, null);
			int len = uri.toASCIIString().length();

			ArrayList<String> ret = new ArrayList<String>();
			// read content returned
			Iterator<String> result = dav.dir(uri.toASCIIString(), regexp);
			while (result.hasNext()) {
				// remove base path from Webdav answer
				URL pathuri = new URL(param.getProtocol(), param.getServer(), result.next());
				String path = pathuri.toString();
				ret.add(PanoptesHelper.decodeUrl(path.substring(len + 1)));
			}
			return ret;
		} catch (DavDroidException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (IOException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (XmlPullParserException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (URISyntaxException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		}
	}

	@Override
	public byte[] get(String location, RepositoryGetListener<Long, byte[]> lsn) throws PanoptimageFileNotFoundException {
		try {
			// construct child listener
			DavDroidGetListener<Long, byte[]> plsn = new DavDroidGetListener<Long, byte[]>(lsn,
					PanoptesConstants.ONPROGRESS_STEPS);
			// prepare request
			String val = PanoptesHelper.formatPath(root, currentPath, PanoptesHelper.SLASH, location);
			URI uri = new URI(param.getProtocol(), param.getServer(), val, null);
			String path = uri.toASCIIString();
			return dav.get(path, true, plsn);
		} catch (IOException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (URISyntaxException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		}
	}

	@Override
	public boolean exists(String path) {
		try {
			String val = PanoptesHelper.formatPath(root, currentPath, path);
			return dav.head(val);
		} catch (IOException e) {
			return false;
		}
	}

	@Override
	public boolean isDirectory(String path) {
		try {
			String val = PanoptesHelper.formatPath(root, currentPath, path);
			Prop result = dav.getPropnames(val);
			Log.d(PanoptesConstants.TAGNAME, result.toString());
			return false;
		} catch (IOException e) {
			return false;
		} catch (XmlPullParserException e) {
			return false;
		}
	}

	@Override
	public boolean showSplashWhileLoading() {
		return false;
	}

}
