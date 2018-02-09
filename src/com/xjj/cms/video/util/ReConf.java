package com.xjj.cms.video.util;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ReConf {
	private static final String BUNDLE_NAME = "com.xjj.cms.video.util.moduleSize"; //$NON-NLS-1$
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);
	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return "";
		}
	}
}
