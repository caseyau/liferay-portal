/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.deploy.auto;

import com.liferay.portal.kernel.deploy.auto.AutoDeployException;
import com.liferay.portal.kernel.deploy.auto.AutoDeployer;
import com.liferay.portal.kernel.deploy.auto.BaseAutoDeployListener;
import com.liferay.portal.kernel.deploy.auto.context.AutoDeploymentContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;

import java.io.File;

/**
 * @author Ryan Park
 * @author Manuel de la Peña
 */
public class LiferayPackageAutoDeployListener extends BaseAutoDeployListener {

	public LiferayPackageAutoDeployListener() {
		_autoDeployer = new ThreadSafeAutoDeployer(
			new LiferayPackageAutoDeployer());
	}

	@Override
	public int deploy(AutoDeploymentContext autoDeploymentContext)
		throws AutoDeployException {

		File file = autoDeploymentContext.getFile();

		if (_log.isDebugEnabled()) {
			_log.debug("Invoking deploy for " + file.getPath());
		}

		if (!isDeployable(file)) {
			return AutoDeployer.CODE_NOT_APPLICABLE;
		}

		if (_log.isInfoEnabled()) {
			_log.info("Extracting Liferay package for " + file.getPath());
		}

		AutoDeployer autoDeployer = wrapAutodeployer(_autoDeployer);

		int code = autoDeployer.autoDeploy(autoDeploymentContext);

		if ((code == AutoDeployer.CODE_DEFAULT) && _log.isInfoEnabled()) {
			_log.info(
				"Liferay package for " + file.getPath() +" extracted " +
					"successfully. Deployment will start in a few seconds.");
		}

		return code;
	}

	@Override
	protected boolean isDeployable(File file) throws AutoDeployException {
		PluginAutoDeployListenerHelper pluginAutoDeployListenerHelper =
			new PluginAutoDeployListenerHelper(file);

		return pluginAutoDeployListenerHelper.isLiferayPackage();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		LiferayPackageAutoDeployListener.class);

	private final AutoDeployer _autoDeployer;

}