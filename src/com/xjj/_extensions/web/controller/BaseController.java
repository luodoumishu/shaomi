package com.xjj._extensions.web.controller;

import java.lang.reflect.ParameterizedType;

import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;
import com.xjj.framework.core.model.BaseModel;
import com.xjj.framework.core.page.Page;
import com.xjj.framework.core.page.PageUtil;
import com.xjj._extensions.web.util.PageParam;

/**处理查询控制器
 * 
 *
 * @param <E>
 */
public abstract class BaseController<E extends BaseModel> {

	/**
	 * 从前台传过来的参数对象
	 * 
	 */
	private PageParam<E> pageParam;

	public final Class<E> entityClass;
	
	@SuppressWarnings("unchecked")
	public BaseController(){
		this.entityClass = (Class<E>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
	}
	
	/**扩展查询条件和排序
	 * 
	 * @param conditionQuery 查询对象
	 * @param orderBy 排序对象
	 */
	protected abstract void extendQueryOrder(ConditionQuery conditionQuery,
			OrderBy orderBy);

	/**
	 * 获取展示到列表的数据
	 * 
	 * @param pageParam 参数对象
	 * @return 返回到前台的数据对象
	 */
	protected Page<E> refreshGridModel(PageParam<E> pageParam) {
		if (pageParam == null)
			return null;
		Page<E> pageModel = null;
		ConditionQuery conditionQuery = null;
		OrderBy orderBy = null;
		try {
			if (pageParam.getQueryObj() != null
					&& pageParam.getQueryObj().length() > 0) {
				conditionQuery = pageParam.generateSearchFromConditions();
				orderBy = pageParam.generateOrderByFromConditions();
				extendQueryOrder(conditionQuery, orderBy);
				E objct = this.entityClass.newInstance();
				pageModel = objct.page(conditionQuery, orderBy, PageUtil.getPageStart(pageParam.getPage(), pageParam.getPageSize()), pageParam.getPageSize());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pageModel;
	}

	protected PageParam<E> getPageParam() {
		return pageParam;
	}

	protected void setPageParam(PageParam<E> pageParam) {
		this.pageParam = pageParam;
	}
}
