package com.xjj._extensions.web.util;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import com.xjj.framework.core.dao.util.ConditionQuery;
import com.xjj.framework.core.dao.util.OrderBy;

/**查询条件对象
 * 
 *
 * @param <T>
 */
public class PageParam<T> {

	/**从第几条记录开始
	 * 
	 */
	private int skin;

	/**总记录数
	 * 
	 */
	private int totalCount;

	/**每页显示记录数
	 * 
	 */
	private int pageSize;

	/**当前页码
	 * 
	 */
	private int page;

	/** 查询条件json对象
	 * 
	 */
	private String queryObj;

	private String orderBy;
	
	/**将json对象解析成hibernate Criterion接口
	 * 
	 * @param searchField 字段名称
	 * @param searchString 字段值
	 * @param searchOper 操作、比如eq等于、ne不等于、gt大于、ge大于等于、like包含、le小于等于、lt小于，等等
	 * @return 返回一个hibernate Criterion接口
	 */
	private Criterion generateSearch(String searchField,
			String searchString, String searchOper) {
		Criterion criterion = null;

		if (searchField != null && searchString != null
				& searchString.length() > 0 && searchOper != null) {
			if ("eq".equals(searchOper)) {
				criterion = Restrictions.eq(searchField, searchString);
			} else if ("ne".equals(searchOper)) {
				criterion = Restrictions.ne(searchField, searchString);
			} else if ("lt".equals(searchOper)) {
				criterion = Restrictions.lt(searchField, searchString);
			} else if ("le".equals(searchOper)) {
				criterion = Restrictions.le(searchField, searchString);
			} else if ("gt".equals(searchOper)) {
				criterion = Restrictions.gt(searchField, searchString);
			} else if ("ge".equals(searchOper)) {
				criterion = Restrictions.ge(searchField, searchString);
			} else if ("bw".equals(searchOper)) {
				// TODO 两者之间
			} else if ("like".equals(searchOper)) {
				criterion = Restrictions.like(searchField, "%"+searchString+"%");
			}
		}
		return criterion;
	}

	/** 将查询条件json对象转换成查询条件集合
	 * 
	 * @return 查询条件集合
	 */
	public ConditionQuery generateSearchFromConditions() {
		ConditionQuery conditionQuery = new ConditionQuery();
		JSONObject jsonObject = JSONObject.fromObject(queryObj);
		JSONArray rules = jsonObject.getJSONArray("rules");

		for (Object obj : rules) {
			JSONObject rule = (JSONObject) obj;
			String field = rule.getString("field");
			String op = rule.getString("op");
			String data = rule.getString("data");
			Criterion criterion = this.generateSearch(field, data, op);
			if (criterion != null) {
				conditionQuery.add(criterion);
			}
		}
		return conditionQuery;
	}

	/**解析排序json数组
	 * 
	 * @return 排序对象
	 */
	public OrderBy generateOrderByFromConditions(){
		JSONArray jsonArray = JSONArray.fromObject(orderBy);
		OrderBy orderBy = new OrderBy();
		if(jsonArray != null && !jsonArray.isEmpty()){
			for (Object obj : jsonArray) {
				JSONObject rule = (JSONObject) obj;
				String field = rule.getString("field");
				String dir = rule.getString("dir");
				Order order = generateOrder(field, dir);
				if (order != null) {
					orderBy.add(order);
				}
			}
		}
		
		return orderBy;
	}
	/**
	 * 
	 * @param orderField
	 * @param orderString
	 * @return
	 */
	private Order generateOrder(String orderField, String orderString){
		Order order = null;

		if (orderField != null && orderField != null & orderString.length() > 0 && orderString != null) {
			if ("desc".equals(orderString)) {
				order = Order.desc(orderField);
			} else if ("asc".equals(orderString)) {
				order = Order.asc(orderField);
			}
			
		}
		return order;
	}
	public int getSkin() {
		return skin;
	}

	public void setSkin(int skin) {
		this.skin = skin;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public String getQueryObj() {
		return queryObj;
	}

	public void setQueryObj(String queryObj) {
		this.queryObj = queryObj;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

}
