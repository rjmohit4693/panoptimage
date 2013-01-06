package org.fereor.panoptimage.dao.repository;

import java.io.File;

public class CacheRepositoryContent extends RepositoryContent {

	private File content;

	public CacheRepositoryContent(File content) {
		this.setContent(content);
	}

	/**
	 * @return the content
	 */
	public File getContent() {
		return content;
	}

	/**
	 * @param content the content to set
	 */
	public void setContent(File content) {
		this.content = content;
	}

	/** Destroy content */
	public void destroy() {
		content.delete();		
	}

	@Override
	protected void finalize() throws Throwable {
		try {
			content.delete();
		} catch (Exception e) {
			// Do nothing
		}
		super.finalize();
	}
}
