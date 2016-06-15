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

package com.liferay.wsrp.service.base;

import aQute.bnd.annotation.ProviderType;

import com.liferay.exportimport.kernel.lar.ExportImportHelperUtil;
import com.liferay.exportimport.kernel.lar.ManifestSummary;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.StagedModelDataHandlerUtil;
import com.liferay.exportimport.kernel.lar.StagedModelType;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdate;
import com.liferay.portal.kernel.dao.jdbc.SqlUpdateFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DefaultActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQuery;
import com.liferay.portal.kernel.dao.orm.DynamicQueryFactoryUtil;
import com.liferay.portal.kernel.dao.orm.ExportActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.IndexableActionableDynamicQuery;
import com.liferay.portal.kernel.dao.orm.Projection;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PersistedModel;
import com.liferay.portal.kernel.module.framework.service.IdentifiableOSGiService;
import com.liferay.portal.kernel.search.Indexable;
import com.liferay.portal.kernel.search.IndexableType;
import com.liferay.portal.kernel.service.BaseLocalServiceImpl;
import com.liferay.portal.kernel.service.PersistedModelLocalServiceRegistry;
import com.liferay.portal.kernel.service.persistence.ClassNamePersistence;
import com.liferay.portal.kernel.service.persistence.PortletPersistence;
import com.liferay.portal.kernel.service.persistence.UserPersistence;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import com.liferay.wsrp.model.WSRPConsumerPortlet;
import com.liferay.wsrp.service.WSRPConsumerPortletLocalService;
import com.liferay.wsrp.service.persistence.WSRPConsumerPersistence;
import com.liferay.wsrp.service.persistence.WSRPConsumerPortletPersistence;
import com.liferay.wsrp.service.persistence.WSRPProducerPersistence;

import java.io.Serializable;

import java.util.List;

import javax.sql.DataSource;

/**
 * Provides the base implementation for the w s r p consumer portlet local service.
 *
 * <p>
 * This implementation exists only as a container for the default service methods generated by ServiceBuilder. All custom service methods should be put in {@link com.liferay.wsrp.service.impl.WSRPConsumerPortletLocalServiceImpl}.
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see com.liferay.wsrp.service.impl.WSRPConsumerPortletLocalServiceImpl
 * @see com.liferay.wsrp.service.WSRPConsumerPortletLocalServiceUtil
 * @generated
 */
@ProviderType
public abstract class WSRPConsumerPortletLocalServiceBaseImpl
	extends BaseLocalServiceImpl implements WSRPConsumerPortletLocalService,
		IdentifiableOSGiService {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link com.liferay.wsrp.service.WSRPConsumerPortletLocalServiceUtil} to access the w s r p consumer portlet local service.
	 */

	/**
	 * Adds the w s r p consumer portlet to the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpConsumerPortlet the w s r p consumer portlet
	 * @return the w s r p consumer portlet that was added
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WSRPConsumerPortlet addWSRPConsumerPortlet(
		WSRPConsumerPortlet wsrpConsumerPortlet) {
		wsrpConsumerPortlet.setNew(true);

		return wsrpConsumerPortletPersistence.update(wsrpConsumerPortlet);
	}

	/**
	 * Creates a new w s r p consumer portlet with the primary key. Does not add the w s r p consumer portlet to the database.
	 *
	 * @param wsrpConsumerPortletId the primary key for the new w s r p consumer portlet
	 * @return the new w s r p consumer portlet
	 */
	@Override
	public WSRPConsumerPortlet createWSRPConsumerPortlet(
		long wsrpConsumerPortletId) {
		return wsrpConsumerPortletPersistence.create(wsrpConsumerPortletId);
	}

	/**
	 * Deletes the w s r p consumer portlet with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpConsumerPortletId the primary key of the w s r p consumer portlet
	 * @return the w s r p consumer portlet that was removed
	 * @throws PortalException if a w s r p consumer portlet with the primary key could not be found
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public WSRPConsumerPortlet deleteWSRPConsumerPortlet(
		long wsrpConsumerPortletId) throws PortalException {
		return wsrpConsumerPortletPersistence.remove(wsrpConsumerPortletId);
	}

	/**
	 * Deletes the w s r p consumer portlet from the database. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpConsumerPortlet the w s r p consumer portlet
	 * @return the w s r p consumer portlet that was removed
	 * @throws PortalException
	 */
	@Indexable(type = IndexableType.DELETE)
	@Override
	public WSRPConsumerPortlet deleteWSRPConsumerPortlet(
		WSRPConsumerPortlet wsrpConsumerPortlet) throws PortalException {
		return wsrpConsumerPortletPersistence.remove(wsrpConsumerPortlet);
	}

	@Override
	public DynamicQuery dynamicQuery() {
		Class<?> clazz = getClass();

		return DynamicQueryFactoryUtil.forClass(WSRPConsumerPortlet.class,
			clazz.getClassLoader());
	}

	/**
	 * Performs a dynamic query on the database and returns the matching rows.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery) {
		return wsrpConsumerPortletPersistence.findWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Performs a dynamic query on the database and returns a range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @return the range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end) {
		return wsrpConsumerPortletPersistence.findWithDynamicQuery(dynamicQuery,
			start, end);
	}

	/**
	 * Performs a dynamic query on the database and returns an ordered range of the matching rows.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param dynamicQuery the dynamic query
	 * @param start the lower bound of the range of model instances
	 * @param end the upper bound of the range of model instances (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching rows
	 */
	@Override
	public <T> List<T> dynamicQuery(DynamicQuery dynamicQuery, int start,
		int end, OrderByComparator<T> orderByComparator) {
		return wsrpConsumerPortletPersistence.findWithDynamicQuery(dynamicQuery,
			start, end, orderByComparator);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery) {
		return wsrpConsumerPortletPersistence.countWithDynamicQuery(dynamicQuery);
	}

	/**
	 * Returns the number of rows matching the dynamic query.
	 *
	 * @param dynamicQuery the dynamic query
	 * @param projection the projection to apply to the query
	 * @return the number of rows matching the dynamic query
	 */
	@Override
	public long dynamicQueryCount(DynamicQuery dynamicQuery,
		Projection projection) {
		return wsrpConsumerPortletPersistence.countWithDynamicQuery(dynamicQuery,
			projection);
	}

	@Override
	public WSRPConsumerPortlet fetchWSRPConsumerPortlet(
		long wsrpConsumerPortletId) {
		return wsrpConsumerPortletPersistence.fetchByPrimaryKey(wsrpConsumerPortletId);
	}

	/**
	 * Returns the w s r p consumer portlet with the matching UUID and company.
	 *
	 * @param uuid the w s r p consumer portlet's UUID
	 * @param companyId the primary key of the company
	 * @return the matching w s r p consumer portlet, or <code>null</code> if a matching w s r p consumer portlet could not be found
	 */
	@Override
	public WSRPConsumerPortlet fetchWSRPConsumerPortletByUuidAndCompanyId(
		String uuid, long companyId) {
		return wsrpConsumerPortletPersistence.fetchByUuid_C_First(uuid,
			companyId, null);
	}

	/**
	 * Returns the w s r p consumer portlet with the primary key.
	 *
	 * @param wsrpConsumerPortletId the primary key of the w s r p consumer portlet
	 * @return the w s r p consumer portlet
	 * @throws PortalException if a w s r p consumer portlet with the primary key could not be found
	 */
	@Override
	public WSRPConsumerPortlet getWSRPConsumerPortlet(
		long wsrpConsumerPortletId) throws PortalException {
		return wsrpConsumerPortletPersistence.findByPrimaryKey(wsrpConsumerPortletId);
	}

	@Override
	public ActionableDynamicQuery getActionableDynamicQuery() {
		ActionableDynamicQuery actionableDynamicQuery = new DefaultActionableDynamicQuery();

		actionableDynamicQuery.setBaseLocalService(wsrpConsumerPortletLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(WSRPConsumerPortlet.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"wsrpConsumerPortletId");

		return actionableDynamicQuery;
	}

	@Override
	public IndexableActionableDynamicQuery getIndexableActionableDynamicQuery() {
		IndexableActionableDynamicQuery indexableActionableDynamicQuery = new IndexableActionableDynamicQuery();

		indexableActionableDynamicQuery.setBaseLocalService(wsrpConsumerPortletLocalService);
		indexableActionableDynamicQuery.setClassLoader(getClassLoader());
		indexableActionableDynamicQuery.setModelClass(WSRPConsumerPortlet.class);

		indexableActionableDynamicQuery.setPrimaryKeyPropertyName(
			"wsrpConsumerPortletId");

		return indexableActionableDynamicQuery;
	}

	protected void initActionableDynamicQuery(
		ActionableDynamicQuery actionableDynamicQuery) {
		actionableDynamicQuery.setBaseLocalService(wsrpConsumerPortletLocalService);
		actionableDynamicQuery.setClassLoader(getClassLoader());
		actionableDynamicQuery.setModelClass(WSRPConsumerPortlet.class);

		actionableDynamicQuery.setPrimaryKeyPropertyName(
			"wsrpConsumerPortletId");
	}

	@Override
	public ExportActionableDynamicQuery getExportActionableDynamicQuery(
		final PortletDataContext portletDataContext) {
		final ExportActionableDynamicQuery exportActionableDynamicQuery = new ExportActionableDynamicQuery() {
				@Override
				public long performCount() throws PortalException {
					ManifestSummary manifestSummary = portletDataContext.getManifestSummary();

					StagedModelType stagedModelType = getStagedModelType();

					long modelAdditionCount = super.performCount();

					manifestSummary.addModelAdditionCount(stagedModelType,
						modelAdditionCount);

					long modelDeletionCount = ExportImportHelperUtil.getModelDeletionCount(portletDataContext,
							stagedModelType);

					manifestSummary.addModelDeletionCount(stagedModelType,
						modelDeletionCount);

					return modelAdditionCount;
				}
			};

		initActionableDynamicQuery(exportActionableDynamicQuery);

		exportActionableDynamicQuery.setAddCriteriaMethod(new ActionableDynamicQuery.AddCriteriaMethod() {
				@Override
				public void addCriteria(DynamicQuery dynamicQuery) {
					portletDataContext.addDateRangeCriteria(dynamicQuery,
						"modifiedDate");
				}
			});

		exportActionableDynamicQuery.setCompanyId(portletDataContext.getCompanyId());

		exportActionableDynamicQuery.setPerformActionMethod(new ActionableDynamicQuery.PerformActionMethod<WSRPConsumerPortlet>() {
				@Override
				public void performAction(
					WSRPConsumerPortlet wsrpConsumerPortlet)
					throws PortalException {
					StagedModelDataHandlerUtil.exportStagedModel(portletDataContext,
						wsrpConsumerPortlet);
				}
			});
		exportActionableDynamicQuery.setStagedModelType(new StagedModelType(
				PortalUtil.getClassNameId(WSRPConsumerPortlet.class.getName())));

		return exportActionableDynamicQuery;
	}

	/**
	 * @throws PortalException
	 */
	@Override
	public PersistedModel deletePersistedModel(PersistedModel persistedModel)
		throws PortalException {
		return wsrpConsumerPortletLocalService.deleteWSRPConsumerPortlet((WSRPConsumerPortlet)persistedModel);
	}

	@Override
	public PersistedModel getPersistedModel(Serializable primaryKeyObj)
		throws PortalException {
		return wsrpConsumerPortletPersistence.findByPrimaryKey(primaryKeyObj);
	}

	/**
	 * Returns the w s r p consumer portlet with the matching UUID and company.
	 *
	 * @param uuid the w s r p consumer portlet's UUID
	 * @param companyId the primary key of the company
	 * @return the matching w s r p consumer portlet
	 * @throws PortalException if a matching w s r p consumer portlet could not be found
	 */
	@Override
	public WSRPConsumerPortlet getWSRPConsumerPortletByUuidAndCompanyId(
		String uuid, long companyId) throws PortalException {
		return wsrpConsumerPortletPersistence.findByUuid_C_First(uuid,
			companyId, null);
	}

	/**
	 * Returns a range of all the w s r p consumer portlets.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.wsrp.model.impl.WSRPConsumerPortletModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of w s r p consumer portlets
	 * @param end the upper bound of the range of w s r p consumer portlets (not inclusive)
	 * @return the range of w s r p consumer portlets
	 */
	@Override
	public List<WSRPConsumerPortlet> getWSRPConsumerPortlets(int start, int end) {
		return wsrpConsumerPortletPersistence.findAll(start, end);
	}

	/**
	 * Returns the number of w s r p consumer portlets.
	 *
	 * @return the number of w s r p consumer portlets
	 */
	@Override
	public int getWSRPConsumerPortletsCount() {
		return wsrpConsumerPortletPersistence.countAll();
	}

	/**
	 * Updates the w s r p consumer portlet in the database or adds it if it does not yet exist. Also notifies the appropriate model listeners.
	 *
	 * @param wsrpConsumerPortlet the w s r p consumer portlet
	 * @return the w s r p consumer portlet that was updated
	 */
	@Indexable(type = IndexableType.REINDEX)
	@Override
	public WSRPConsumerPortlet updateWSRPConsumerPortlet(
		WSRPConsumerPortlet wsrpConsumerPortlet) {
		return wsrpConsumerPortletPersistence.update(wsrpConsumerPortlet);
	}

	/**
	 * Returns the w s r p consumer local service.
	 *
	 * @return the w s r p consumer local service
	 */
	public com.liferay.wsrp.service.WSRPConsumerLocalService getWSRPConsumerLocalService() {
		return wsrpConsumerLocalService;
	}

	/**
	 * Sets the w s r p consumer local service.
	 *
	 * @param wsrpConsumerLocalService the w s r p consumer local service
	 */
	public void setWSRPConsumerLocalService(
		com.liferay.wsrp.service.WSRPConsumerLocalService wsrpConsumerLocalService) {
		this.wsrpConsumerLocalService = wsrpConsumerLocalService;
	}

	/**
	 * Returns the w s r p consumer persistence.
	 *
	 * @return the w s r p consumer persistence
	 */
	public WSRPConsumerPersistence getWSRPConsumerPersistence() {
		return wsrpConsumerPersistence;
	}

	/**
	 * Sets the w s r p consumer persistence.
	 *
	 * @param wsrpConsumerPersistence the w s r p consumer persistence
	 */
	public void setWSRPConsumerPersistence(
		WSRPConsumerPersistence wsrpConsumerPersistence) {
		this.wsrpConsumerPersistence = wsrpConsumerPersistence;
	}

	/**
	 * Returns the w s r p consumer portlet local service.
	 *
	 * @return the w s r p consumer portlet local service
	 */
	public WSRPConsumerPortletLocalService getWSRPConsumerPortletLocalService() {
		return wsrpConsumerPortletLocalService;
	}

	/**
	 * Sets the w s r p consumer portlet local service.
	 *
	 * @param wsrpConsumerPortletLocalService the w s r p consumer portlet local service
	 */
	public void setWSRPConsumerPortletLocalService(
		WSRPConsumerPortletLocalService wsrpConsumerPortletLocalService) {
		this.wsrpConsumerPortletLocalService = wsrpConsumerPortletLocalService;
	}

	/**
	 * Returns the w s r p consumer portlet persistence.
	 *
	 * @return the w s r p consumer portlet persistence
	 */
	public WSRPConsumerPortletPersistence getWSRPConsumerPortletPersistence() {
		return wsrpConsumerPortletPersistence;
	}

	/**
	 * Sets the w s r p consumer portlet persistence.
	 *
	 * @param wsrpConsumerPortletPersistence the w s r p consumer portlet persistence
	 */
	public void setWSRPConsumerPortletPersistence(
		WSRPConsumerPortletPersistence wsrpConsumerPortletPersistence) {
		this.wsrpConsumerPortletPersistence = wsrpConsumerPortletPersistence;
	}

	/**
	 * Returns the w s r p producer local service.
	 *
	 * @return the w s r p producer local service
	 */
	public com.liferay.wsrp.service.WSRPProducerLocalService getWSRPProducerLocalService() {
		return wsrpProducerLocalService;
	}

	/**
	 * Sets the w s r p producer local service.
	 *
	 * @param wsrpProducerLocalService the w s r p producer local service
	 */
	public void setWSRPProducerLocalService(
		com.liferay.wsrp.service.WSRPProducerLocalService wsrpProducerLocalService) {
		this.wsrpProducerLocalService = wsrpProducerLocalService;
	}

	/**
	 * Returns the w s r p producer persistence.
	 *
	 * @return the w s r p producer persistence
	 */
	public WSRPProducerPersistence getWSRPProducerPersistence() {
		return wsrpProducerPersistence;
	}

	/**
	 * Sets the w s r p producer persistence.
	 *
	 * @param wsrpProducerPersistence the w s r p producer persistence
	 */
	public void setWSRPProducerPersistence(
		WSRPProducerPersistence wsrpProducerPersistence) {
		this.wsrpProducerPersistence = wsrpProducerPersistence;
	}

	/**
	 * Returns the counter local service.
	 *
	 * @return the counter local service
	 */
	public com.liferay.counter.kernel.service.CounterLocalService getCounterLocalService() {
		return counterLocalService;
	}

	/**
	 * Sets the counter local service.
	 *
	 * @param counterLocalService the counter local service
	 */
	public void setCounterLocalService(
		com.liferay.counter.kernel.service.CounterLocalService counterLocalService) {
		this.counterLocalService = counterLocalService;
	}

	/**
	 * Returns the class name local service.
	 *
	 * @return the class name local service
	 */
	public com.liferay.portal.kernel.service.ClassNameLocalService getClassNameLocalService() {
		return classNameLocalService;
	}

	/**
	 * Sets the class name local service.
	 *
	 * @param classNameLocalService the class name local service
	 */
	public void setClassNameLocalService(
		com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService) {
		this.classNameLocalService = classNameLocalService;
	}

	/**
	 * Returns the class name persistence.
	 *
	 * @return the class name persistence
	 */
	public ClassNamePersistence getClassNamePersistence() {
		return classNamePersistence;
	}

	/**
	 * Sets the class name persistence.
	 *
	 * @param classNamePersistence the class name persistence
	 */
	public void setClassNamePersistence(
		ClassNamePersistence classNamePersistence) {
		this.classNamePersistence = classNamePersistence;
	}

	/**
	 * Returns the portlet local service.
	 *
	 * @return the portlet local service
	 */
	public com.liferay.portal.kernel.service.PortletLocalService getPortletLocalService() {
		return portletLocalService;
	}

	/**
	 * Sets the portlet local service.
	 *
	 * @param portletLocalService the portlet local service
	 */
	public void setPortletLocalService(
		com.liferay.portal.kernel.service.PortletLocalService portletLocalService) {
		this.portletLocalService = portletLocalService;
	}

	/**
	 * Returns the portlet persistence.
	 *
	 * @return the portlet persistence
	 */
	public PortletPersistence getPortletPersistence() {
		return portletPersistence;
	}

	/**
	 * Sets the portlet persistence.
	 *
	 * @param portletPersistence the portlet persistence
	 */
	public void setPortletPersistence(PortletPersistence portletPersistence) {
		this.portletPersistence = portletPersistence;
	}

	/**
	 * Returns the resource local service.
	 *
	 * @return the resource local service
	 */
	public com.liferay.portal.kernel.service.ResourceLocalService getResourceLocalService() {
		return resourceLocalService;
	}

	/**
	 * Sets the resource local service.
	 *
	 * @param resourceLocalService the resource local service
	 */
	public void setResourceLocalService(
		com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService) {
		this.resourceLocalService = resourceLocalService;
	}

	/**
	 * Returns the user local service.
	 *
	 * @return the user local service
	 */
	public com.liferay.portal.kernel.service.UserLocalService getUserLocalService() {
		return userLocalService;
	}

	/**
	 * Sets the user local service.
	 *
	 * @param userLocalService the user local service
	 */
	public void setUserLocalService(
		com.liferay.portal.kernel.service.UserLocalService userLocalService) {
		this.userLocalService = userLocalService;
	}

	/**
	 * Returns the user persistence.
	 *
	 * @return the user persistence
	 */
	public UserPersistence getUserPersistence() {
		return userPersistence;
	}

	/**
	 * Sets the user persistence.
	 *
	 * @param userPersistence the user persistence
	 */
	public void setUserPersistence(UserPersistence userPersistence) {
		this.userPersistence = userPersistence;
	}

	public void afterPropertiesSet() {
		persistedModelLocalServiceRegistry.register("com.liferay.wsrp.model.WSRPConsumerPortlet",
			wsrpConsumerPortletLocalService);
	}

	public void destroy() {
		persistedModelLocalServiceRegistry.unregister(
			"com.liferay.wsrp.model.WSRPConsumerPortlet");
	}

	/**
	 * Returns the OSGi service identifier.
	 *
	 * @return the OSGi service identifier
	 */
	@Override
	public String getOSGiServiceIdentifier() {
		return WSRPConsumerPortletLocalService.class.getName();
	}

	protected Class<?> getModelClass() {
		return WSRPConsumerPortlet.class;
	}

	protected String getModelClassName() {
		return WSRPConsumerPortlet.class.getName();
	}

	/**
	 * Performs a SQL query.
	 *
	 * @param sql the sql query
	 */
	protected void runSQL(String sql) {
		try {
			DataSource dataSource = wsrpConsumerPortletPersistence.getDataSource();

			DB db = DBManagerUtil.getDB();

			sql = db.buildSQL(sql);
			sql = PortalUtil.transformSQL(sql);

			SqlUpdate sqlUpdate = SqlUpdateFactoryUtil.getSqlUpdate(dataSource,
					sql);

			sqlUpdate.update();
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
	}

	@BeanReference(type = com.liferay.wsrp.service.WSRPConsumerLocalService.class)
	protected com.liferay.wsrp.service.WSRPConsumerLocalService wsrpConsumerLocalService;
	@BeanReference(type = WSRPConsumerPersistence.class)
	protected WSRPConsumerPersistence wsrpConsumerPersistence;
	@BeanReference(type = WSRPConsumerPortletLocalService.class)
	protected WSRPConsumerPortletLocalService wsrpConsumerPortletLocalService;
	@BeanReference(type = WSRPConsumerPortletPersistence.class)
	protected WSRPConsumerPortletPersistence wsrpConsumerPortletPersistence;
	@BeanReference(type = com.liferay.wsrp.service.WSRPProducerLocalService.class)
	protected com.liferay.wsrp.service.WSRPProducerLocalService wsrpProducerLocalService;
	@BeanReference(type = WSRPProducerPersistence.class)
	protected WSRPProducerPersistence wsrpProducerPersistence;
	@ServiceReference(type = com.liferay.counter.kernel.service.CounterLocalService.class)
	protected com.liferay.counter.kernel.service.CounterLocalService counterLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.ClassNameLocalService.class)
	protected com.liferay.portal.kernel.service.ClassNameLocalService classNameLocalService;
	@ServiceReference(type = ClassNamePersistence.class)
	protected ClassNamePersistence classNamePersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.PortletLocalService.class)
	protected com.liferay.portal.kernel.service.PortletLocalService portletLocalService;
	@ServiceReference(type = PortletPersistence.class)
	protected PortletPersistence portletPersistence;
	@ServiceReference(type = com.liferay.portal.kernel.service.ResourceLocalService.class)
	protected com.liferay.portal.kernel.service.ResourceLocalService resourceLocalService;
	@ServiceReference(type = com.liferay.portal.kernel.service.UserLocalService.class)
	protected com.liferay.portal.kernel.service.UserLocalService userLocalService;
	@ServiceReference(type = UserPersistence.class)
	protected UserPersistence userPersistence;
	@ServiceReference(type = PersistedModelLocalServiceRegistry.class)
	protected PersistedModelLocalServiceRegistry persistedModelLocalServiceRegistry;
}