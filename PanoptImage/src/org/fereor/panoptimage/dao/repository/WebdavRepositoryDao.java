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

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.SSLPeerUnverifiedException;

import org.apache.http.HttpHost;
import org.fereor.davdroid.DavDroid;
import org.fereor.davdroid.DavDroidFactory;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.http.xml.i.Prop;
import org.fereor.panoptimage.activity.create.WebdavProtocolIcon;
import org.fereor.panoptimage.dao.async.DavDroidGetListener;
import org.fereor.panoptimage.dao.async.RepositoryDirListener;
import org.fereor.panoptimage.dao.async.RepositoryGetListener;
import org.fereor.panoptimage.exception.PanoptimageFileNotFoundException;
import org.fereor.panoptimage.exception.PanoptimageNoNetworkException;
import org.fereor.panoptimage.exception.PanoptimagePeerUnverifiedException;
import org.fereor.panoptimage.model.WebdavParam;
import org.fereor.panoptimage.util.PanoptimageConstants;
import org.fereor.panoptimage.util.PanoptimageHelper;
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
	private File cachedir;

	/**
	 * Default constructor with param data
	 * 
	 * @param param
	 */
	public WebdavRepositoryDao(WebdavParam param, File cachedir) throws PanoptimageNoNetworkException {
		super(param);
		this.cachedir = cachedir;
		// set root path
		if (!param.getBase().endsWith(PanoptimageHelper.SLASH)) {
			this.root = param.getBase() + PanoptimageHelper.SLASH;
		} else {
			this.root = param.getBase();
		}
		if (!param.getPath().isEmpty() && !param.getPath().endsWith(PanoptimageHelper.SLASH)) {
			this.root = this.root + param.getPath() + PanoptimageHelper.SLASH;
		} else {
			this.root = this.root + param.getPath();
		}

		// Get port
		int port = WebdavProtocolIcon.STANDARD_PORT;
		if (WebdavProtocolIcon.HTTPS.toString().equalsIgnoreCase(param.getProtocol())) {
			port = WebdavProtocolIcon.SECURED_PORT;
		}
		if (param.getPort() != null && !param.getPort().isEmpty()) {
			port = Integer.parseInt(param.getPort());
		}
		// Create host
		HttpHost host = new HttpHost(param.getServer(), port, param.getProtocol().toLowerCase(Locale.FRANCE));
		// create Webdav link
		try {
			dav = DavDroidFactory.init(host, param.getUser(), param.getPwd());
		} catch (Exception e) {
			throw new PanoptimageNoNetworkException(param.getServer());
		}
	}

	@Override
	public List<String> dir(String regexp, RepositoryDirListener<Long, List<String>> lsn)
			throws PanoptimageFileNotFoundException {
		try {
			// prepare request
			String val = PanoptimageHelper.formatPath(root, currentPath);
			URI uri = new URI(param.getProtocol().toLowerCase(Locale.FRANCE), null, param.getServer(),
					Integer.parseInt(param.getPort()), val, null, null);
			String asciiPath = uri.toASCIIString();
			if (asciiPath.endsWith(PanoptimageHelper.SLASH)) {
				asciiPath = asciiPath.substring(0, asciiPath.length() - 1);
			}
			int len = asciiPath.length();

			ArrayList<String> ret = new ArrayList<String>();
			// read content returned
			Iterator<String> result = dav.dir(asciiPath, regexp);
			while (result.hasNext()) {
				// remove base path from Webdav answer
				URL pathuri = new URL(param.getProtocol().toLowerCase(Locale.FRANCE), param.getServer(),Integer.parseInt(param.getPort()), result.next());
				String path = pathuri.toString();
				ret.add(PanoptimageHelper.decodeUrl(path.substring(len + 1)));
			}
			return ret;
		} catch (DavDroidException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (StringIndexOutOfBoundsException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (SSLPeerUnverifiedException e) {
			throw new PanoptimagePeerUnverifiedException(e.toString());
		} catch (IOException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (XmlPullParserException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (URISyntaxException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		}
	}

	@Override
	public RepositoryContent get(String location, RepositoryGetListener<Long, RepositoryContent> lsn)
			throws PanoptimageFileNotFoundException {
		try {
			// construct child listener
			DavDroidGetListener<Long, RepositoryContent> plsn = new DavDroidGetListener<Long, RepositoryContent>(lsn,
					PanoptimageConstants.ONPROGRESS_STEPS);
			// prepare request
			String val = PanoptimageHelper.formatPath(root, currentPath, PanoptimageHelper.SLASH, location);
			URI uri = new URI(param.getProtocol().toLowerCase(Locale.FRANCE), param.getServer(), val, null);
			String path = uri.toASCIIString();
			// return new ByteRepositoryContent(dav.get(path, false, plsn));
			return new CacheRepositoryContent(dav.getAsFile(path, cachedir, plsn));
		} catch (IOException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		} catch (URISyntaxException e) {
			throw new PanoptimageFileNotFoundException(e.toString());
		}
	}

	@Override
	public boolean exists(String path) {
		try {
			String val = PanoptimageHelper.formatPath(root, currentPath, path);
			// prepare request
			URI uri = new URI(param.getProtocol().toLowerCase(Locale.FRANCE), param.getServer(), val, null);
			return dav.head(uri.toASCIIString());
		} catch (IOException e) {
			return false;
		} catch (URISyntaxException e) {
			return false;
		}
	}

	@Override
	public boolean isDirectory(String path) {
		try {
			String val = PanoptimageHelper.formatPath(root, currentPath, path);
			Prop result = dav.getPropnames(val);
			Log.d(PanoptimageConstants.TAGNAME, result.toString());
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
