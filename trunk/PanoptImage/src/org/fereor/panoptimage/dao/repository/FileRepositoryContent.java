package org.fereor.panoptimage.dao.repository;

import java.io.File;

public class FileRepositoryContent extends RepositoryContent {

	private File content;

	public FileRepositoryContent(File content) {
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
}
