package org.fereor.panoptimage.dao;

public class DatabaseStatus {
	/** singleton isntance */
	private static DatabaseStatus instance = null;

	/** Config status updated */
	private long configUpdated = 0;
	/** Local status updated */
	private long localUpdated = 0;
	/** Webdav status updated */
	private long webdavUpdated = 0;

	/** get instance */
	public static DatabaseStatus getInstance() {
		if (instance == null) {
			instance = new DatabaseStatus();
		}
		return instance;
	}

	public void markConfigSaved() {
		configUpdated = System.currentTimeMillis();
	}

	public void markLocalSaved() {
		localUpdated = System.currentTimeMillis();
	}

	public void markWebdavSaved() {
		webdavUpdated = System.currentTimeMillis();
	}

	public long getConfigUpdated() {
		return configUpdated;
	}

	public long getLocalUpdated() {
		return localUpdated;
	}

	public long getWebdavUpdated() {
		return webdavUpdated;
	}

}
