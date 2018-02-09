package com.xjj.cms.base.util.ztree;



/**
 * 构造树的配置,构造一颗树最少需要ID字段和父节点ID字段
 * <p>
 * @author yeyunfeng 2014-8-29 下午6:19:10 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-29
 * @modify by reason:{方法名}:{原因}
 * @param <T>
 */
public interface TreeConfig<T> {
	
	
	/**
	 * 取对象的父节点ID
	 * @author yeyunfeng 2014-8-29  下午6:19:26
	 * @param obj
	 * @return
	 *
	 */
	Object getParentId(T obj);
	
	
	/**
	 * 取对象ID
	 * @author yeyunfeng 2014-8-29  下午6:19:37
	 * @param obj
	 * @return
	 *
	 */
	Object getPkid(T obj);
	
	/**
	 * 根据实体构造树节点
	 * @author yeyunfeng 2014-8-29  下午6:19:50
	 * @param obj
	 * @return
	 *
	 */
	TreeNode getTreeNode(T obj);
	
}
