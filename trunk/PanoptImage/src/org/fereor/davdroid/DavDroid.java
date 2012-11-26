// This file is part of DavDroid.
//
// DavDroid is free software: you can redistribute it and/or modify
// it under the terms of the GNU Lesser General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// DavDroid is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public License
// along with DavDroid.  If not, see <http://www.gnu.org/licenses/>

package org.fereor.davdroid;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.ProxySelector;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.StatusLine;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.params.AuthPolicy;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.ProxySelectorRoutePlanner;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.protocol.HttpContext;
import org.fereor.davdroid.exception.DavDroidException;
import org.fereor.davdroid.exception.DavDroidStatusException;
import org.fereor.davdroid.exception.DavDroidUnimplementedException;
import org.fereor.davdroid.http.enums.HttpDepth;
import org.fereor.davdroid.http.enums.HttpMethod;
import org.fereor.davdroid.http.request.HttpCopy;
import org.fereor.davdroid.http.request.HttpExists;
import org.fereor.davdroid.http.request.HttpMkcol;
import org.fereor.davdroid.http.request.HttpPropfind;
import org.fereor.davdroid.http.response.AllowResponseHandler;
import org.fereor.davdroid.http.response.BufferResponseHandler;
import org.fereor.davdroid.http.response.InputStreamResponseHandler;
import org.fereor.davdroid.http.response.ExistsResponseHandler;
import org.fereor.davdroid.http.response.StatusCodeResponseHandler;
import org.fereor.davdroid.http.util.ResponseBuffer;
import org.fereor.davdroid.http.util.XmlContentParser;
import org.fereor.davdroid.http.util.XmlRegexpIterator;
import org.fereor.davdroid.http.util.XmlMultistatusIterator;
import org.fereor.davdroid.http.xml.i.Prop;
import org.fereor.davdroid.http.xml.i.Propfind;
import org.fereor.davdroid.http.xml.o.Response;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;

/**
 * Implementation of the DavDroid interface
 * 
 * @author "arnaud.p.fereor"
 * 
 */
public class DavDroid {
	/**
	 * HTTP Implementation
	 */
	private AbstractHttpClient client;

	/**
	 * HTTP Host for Webdav server
	 */
	private HttpHost host;

	/**
	 * XML parser to use
	 */
	private XmlPullParserFactory factory;

	/**
	 * Local context with authentication cache. Make sure the same context is
	 * used to execute logically related requests.
	 */
	private HttpContext context = new BasicHttpContext();

	/**
	 * Default constructor with no authentication and default settings for SSL.
	 * 
	 * @throws XmlPullParserException
	 */
	public DavDroid(HttpHost host) throws XmlPullParserException {
		this(host, null, null);
	}

	/**
	 * Pass in a HTTP Auth username/password for being used with all connections
	 * 
	 * @param username
	 *            Use in authentication header credentials
	 * @param password
	 *            Use in authentication header credentials
	 * @throws XmlPullParserException
	 */
	public DavDroid(HttpHost host, String username, String password)
			throws XmlPullParserException {
		this(host, username, password, null);
	}

	/**
	 * Constructor for the DavDroid implementation
	 * 
	 * @param username
	 *            user to connect to WebDav
	 * @param password
	 *            password to connect to WebDav
	 * @param proxy
	 *            proxy configuration
	 * @throws XmlPullParserException
	 */
	public DavDroid(HttpHost host, String username, String password,
			ProxySelector proxy) throws XmlPullParserException {
		// save basic values
		this.host = host;

		// create default HTTP client
		this.client = createDefaultClient(proxy);

		// Create parser
		factory = XmlPullParserFactory.newInstance();

		// set client credentials
		if (username != null) {
			// client.getCredentialsProvider().setCredentials(
			// new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
			// AuthScope.ANY_REALM, AuthPolicy.NTLM),
			// new NTCredentials(username, password, workstation, domain));
			client.getCredentialsProvider().setCredentials(
					new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
							AuthScope.ANY_REALM, AuthPolicy.BASIC),
					new UsernamePasswordCredentials(username, password));
			client.getCredentialsProvider().setCredentials(
					new AuthScope(AuthScope.ANY_HOST, AuthScope.ANY_PORT,
							AuthScope.ANY_REALM, AuthPolicy.DIGEST),
					new UsernamePasswordCredentials(username, password));
		}
	}

	/**
	 * Creates an AbstractHttpClient with all of the defaults.
	 */
	private AbstractHttpClient createDefaultClient(ProxySelector selector) {
		// create scheme registry
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory
				.getSocketFactory(), 80));
		schemeRegistry.register(new Scheme("https", SSLSocketFactory
				.getSocketFactory(), 443));

		// Prepare HTTP parameters
		HttpParams params = new BasicHttpParams();
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setUserAgent(params, "DavDroid/"
				+ DavDroidConstants.VERSION);
		HttpProtocolParams.setUseExpectContinue(params, false);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		HttpProtocolParams.setContentCharset(params,
				HTTP.DEFAULT_CONTENT_CHARSET);
		HttpConnectionParams.setTcpNoDelay(params, true);
		HttpConnectionParams.setSocketBufferSize(params, 81920);

		// create connection manager
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params,
				schemeRegistry);
		AbstractHttpClient client = new DefaultHttpClient(cm, params);
		client.setRoutePlanner(new ProxySelectorRoutePlanner(schemeRegistry,
				selector));
		return client;
	}

	public void setCredentials(String username, String password, String domain,
			String workstation) {

	}

	public void abort() {
		// TODO Auto-generated method stub
	}

	public boolean isAborted() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * Get the list of options supported by the given path
	 * 
	 * @param path
	 *            path to check
	 * @return list of options supported
	 * @throws IOException
	 *             if any error occurred
	 */
	public List<HttpMethod> options(String path) throws IOException {
		HttpOptions req = new HttpOptions(path);
		// OPTIONS request returns a 200 code, managed by the Allow response
		// handler
		List<HttpMethod> result = client.execute(host, req,
				new AllowResponseHandler(), context);
		Log.d(DavDroidConstants.LOG_TAG,
				String.format("Result of 'options' call : %s",
						result.toString()));
		return result;
	}

	/**
	 * List content of a path folder, using the PROPFIND option
	 * 
	 * @param path
	 *            path to search
	 * @param regexp
	 *            Regular expression to match the data found, as
	 *            <code>String.matches</code>. If null, no filter is done
	 * @return
	 * @throws IOException
	 * @throws XmlPullParserException
	 */
	public Iterator<String> dir(String path, String regexp) throws IOException,
			XmlPullParserException {
		// call propfind to get data
		byte[] data = propfind(path,
				Propfind.createProp(new String[] { "creationdate", "etag" }),
				HttpDepth.DEPTH_ONE);

		// Parse the result
		XmlRegexpIterator res = new XmlRegexpIterator(data,
				factory.newPullParser(), "href", regexp);
		return res;
	}

	/**
	 * Get the available properties of a given path
	 * 
	 * @param path
	 *            path to scan
	 * @return properties of the path
	 * @throws IOException
	 *             if IO exception occurred
	 * @throws XmlPullParserException
	 *             if XML parsing exception
	 */
	public Prop getPropnames(String path) throws IOException,
			XmlPullParserException {
		// call propfind to get data
		byte[] data = propfind(path, Propfind.createPropname(),
				HttpDepth.DEPTH_ZERO);
		// search for prop tag
		XmlContentParser<Prop> parser = new XmlContentParser<Prop>(data,
				factory.newPullParser(), Prop.class, Prop.TAGNAME);
		return parser.parseContent();
	}

	/**
	 * Get the properties of a given path
	 * 
	 * @param path
	 *            path to scan
	 * @return properties of the path
	 * @throws IOException
	 *             if IO exception occurred
	 * @throws XmlPullParserException
	 *             if XML parsing exception
	 */
	public Prop getProps(String path) throws IOException,
			XmlPullParserException {
		// call propfind to get data
		byte[] data = propfind(path, Propfind.createAllprop(),
				HttpDepth.DEPTH_ZERO);
		// search for prop tag
		XmlContentParser<Prop> parser = new XmlContentParser<Prop>(data,
				factory.newPullParser(), Prop.class, Prop.TAGNAME);
		return parser.parseContent();
	}

	/**
	 * Get the content of the destination path
	 * 
	 * @param path
	 *            destination to scan
	 * @param buf
	 *            use a buffer to store the stream
	 * @return content as a byte array
	 * @throws IOException
	 *             is content cannot be retrieved
	 */
	public InputStream get(String path, boolean buf) throws IOException {
		HttpGet req = new HttpGet(path);
		// GET request returns a 200 code if the target exists
		return client.execute(host, req, new InputStreamResponseHandler(buf),
				context);
	}

	/**
	 * Check if a given content exists
	 * 
	 * @param path
	 *            path to check
	 * @return true if the exist
	 * @throws IOException
	 */
	public Boolean head(String path) throws IOException {
		HttpHead req = new HttpHead(path);
		// GET request returns a 200 code if the target exists
		Boolean result = client.execute(host, req, new ExistsResponseHandler(),
				context);
		Log.d(DavDroidConstants.LOG_TAG,
				String.format("Result of 'head' call : %s", result.toString()));
		return result;
	}

	/**
	 * Executes a PROPFIND request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 * @throws XmlPullParserException
	 *             if XML parsing exception occurred
	 */
	public byte[] propfind(String path, Propfind inputProp, HttpDepth depth)
			throws IOException, XmlPullParserException {
		// serialize request
		StringWriter writer = new StringWriter();
		XmlSerializer serializer = factory.newSerializer();
		serializer.setOutput(writer);
		serializer.startDocument(HTTP.UTF_8, null);
		inputProp.writeself(serializer);
		serializer.endDocument();
		Log.d(DavDroidConstants.LOG_TAG, writer.toString());

		HttpPropfind propfind = new HttpPropfind(path, writer.toString(), depth);
		// PROPFIND request returns a 200 code if the target exists
		byte[] result = client.execute(host, propfind,
				new BufferResponseHandler(), context).getBuffer();
		return result;
	}

	/**
	 * Executes a PROPPATCH request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] proppatch(String path, String inputProp) throws IOException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("proppatch");
	}

	/**
	 * Executes a MKCOL request
	 * 
	 * @param path
	 *            path to create
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public void mkcol(String path) throws DavDroidException, IOException {
		HttpMkcol req = new HttpMkcol(path);
		// PROPFIND request returns a 200 code if the target exists
		StatusLine result = client.execute(host, req,
				new StatusCodeResponseHandler(), context);
		// 201 (Created) - The collection was created.
		if (result.getStatusCode() != HttpStatus.SC_CREATED)
			throw new DavDroidStatusException(String.format("[%d] : %s",
					result.getStatusCode(), result.getReasonPhrase()));
	}

	/**
	 * Executes a POST request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] post(String path, InputStream inputProp) throws IOException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("post");
	}

	/**
	 * Executes a DELETE request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] delete(String path, String inputProp) throws IOException,
			XmlPullParserException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("delete");
	}

	/**
	 * Executes a COPY request
	 * 
	 * @param source
	 *            source URL
	 * @param destination
	 *            destination URL
	 * @param overwrite
	 *            overwrite (true/false)
	 * @throws IOException
	 * @throws XmlPullParserException
	 * @throws
	 */
	public Iterator<Response> copy(String source, String destination,
			boolean overwrite) throws XmlPullParserException, IOException {
		HttpCopy req = new HttpCopy(source, String.format("%s/%s",
				host.toString(), destination), overwrite);
		// COPY request returns a 200 code if the target exists
		ResponseBuffer res = client.execute(host, req,
				new BufferResponseHandler(), context);
		// check result of request
		if (res.getStatus().getStatusCode() == HttpStatus.SC_MULTI_STATUS) {
			Log.d(DavDroidConstants.LOG_TAG, new String(res.getBuffer()));
			// parse the result
			return new XmlMultistatusIterator(res.getBuffer(),
					factory.newPullParser());
		} else {
			// for other return codes, no return is needed
			return null;
		}

	}

	/**
	 * Executes a MOVE request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] move(String path, String inputProp) throws IOException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("move");
	}

	/**
	 * Executes a LOCK request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] lock(String path, String inputProp) throws IOException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("lock");
	}

	/**
	 * Executes a UNLOCK request
	 * 
	 * @param path
	 *            path to search
	 * @return byte buffer with XML response not parsed
	 * @throws IOException
	 *             if an IO Exception occurred
	 */
	public byte[] unlock(String path, String inputProp) throws IOException {
		// TODO Unimplemented method
		throw new DavDroidUnimplementedException("unlock");
	}

	/**
	 * enable compression for HTTP requests <br>
	 * If set, the HTTP Header "Accept-Content: gzip" will be added, and the
	 * response content will be unzipped before analysis
	 */
	public void enableCompression() {
		Log.e(DavDroidConstants.LOG_TAG, "enableCompression not implemented");
	}

	/**
	 * 
	 */
	public void disableCompression() {
		Log.e(DavDroidConstants.LOG_TAG, "disableCompression not implemented");
	}

	/**
	 * 
	 * @param hostname
	 */
	public void enablePreemptiveAuthentication(String hostname) {
		Log.e(DavDroidConstants.LOG_TAG,
				"enablePreemptiveAuthentication not implemented");
	}

	/**
	 * 
	 */
	public void disablePreemptiveAuthentication() {
		Log.e(DavDroidConstants.LOG_TAG,
				"disablePreemptiveAuthentication not implemented");
	}
}
