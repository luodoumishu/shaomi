package com.xjj.cms.base.util.ztree;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONSerializer;

/**
 * 树节点工具类,用于将各种实体转换为树节点,或将实体list转换为树节点list
 * <p>
 * @author yeyunfeng 2014-8-29 下午6:20:18 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-29
 * @modify by reason:{方法名}:{原因}
 */
public final class TreeUtil {
	
	
	/**
	 * 根据给定实体构造树节点
	 * @author yeyunfeng 2014-8-29  下午6:20:28
	 * @param obj
	 * @param treeConfig
	 * @return
	 *
	 */
	public static <T> TreeNode getTreeNode(T obj, TreeConfig<T> treeConfig) {
		if (null == treeConfig || null == obj) {
			return null;
		}
		//如果树配置未实现getTreeNode方法,则根据ID来构造树
		if(null == treeConfig.getTreeNode(obj)) {
			if(null == treeConfig.getPkid(obj)) {
				return null;
			}
			TreeNode treeNode = new TreeNode();
			treeNode.setId(treeConfig.getPkid(obj).toString());
			treeNode.setName(treeConfig.getPkid(obj).toString());
			return treeNode;
		}
		return treeConfig.getTreeNode(obj);
	}
	
	
	/**
	 * 根据对象list和树节点配置取树节点的list
	 * @author yeyunfeng 2014-8-29  下午6:20:38
	 * @param objList
	 * @param treeConfig
	 * @return
	 *
	 */
	public static <T> List<TreeNode> getTreeNodeList(List<T> objList, TreeConfig<T> treeConfig) {
		if (treeConfig == null) {
			return null;
		}
		List<TreeNode> treeNodes = new ArrayList<TreeNode>();
		pos2TreeNodes(objList, treeNodes, treeConfig);
		return treeNodes;
	}
	
	/**
	 * 递归得到树节点list,并赋值给treeNodes
	 * @author yeyunfeng 2014-8-29  下午6:20:47
	 * @param objList
	 * @param treeNodes
	 * @param treeConfig
	 *
	 */
	private static <T> void pos2TreeNodes(List<T> objList, List<TreeNode> treeNodes, TreeConfig<T> treeConfig) {
		if(null == treeNodes) {
			treeNodes = new ArrayList<TreeNode>();
		}
		if (null == objList || 0 == objList.size()) {
			return;
		}
		while(objList.size() > 0) {
			final int before = objList.size();
			for (int i = 0; i < objList.size(); i++) {
				T obj = objList.get(i);
				Object parentId = treeConfig.getParentId(obj);
				// stringparentId.toString()
				if (null == parentId || "0".equals(parentId)) {
					treeNodes.add(getTreeNode(obj, treeConfig));
					objList.remove(i);
					i = -1;
				} else {
					//根据parentId在treeNodes中递归查找是否有对应的父节点
					TreeNode parentNode = getTreeNodeById(treeNodes, parentId.toString());
					if(null != parentNode) {
						if(null == parentNode.getNodes()) {
							//父节点的nodes为null, 则先实例化
							parentNode.setNodes(new ArrayList<TreeNode>());
						}
						//创建节点
						TreeNode childNode = getTreeNode(obj, treeConfig);
						if(!parentNode.getNodes().contains(childNode)) {
							//加入到父节点nodes中
							parentNode.getNodes().add(childNode);
						}
						objList.remove(i);
						i = -1;
					}
				}
			}
			if(objList.size() == before) {
				//如果遍历了一遍数据长度没有变化,则跳出循环
				return;
			}
		}
	}
	
	
	/**
	 * 遍历树查找指定ID的节点
	 * @author yeyunfeng 2014-8-29  下午6:20:59
	 * @param treeNodes
	 * @param id
	 * @return
	 *
	 */
	private static TreeNode getTreeNodeById(List<TreeNode> treeNodes, String id) {
		for(int i = 0; i < treeNodes.size(); i ++) {
			if(id.equals(treeNodes.get(i).getId())) {
				return treeNodes.get(i);
			}
			if(null != treeNodes.get(i).getNodes()) {
				//对子节点递归
				TreeNode parentNode = getTreeNodeById(treeNodes.get(i).getNodes(), id);
				if(null != parentNode) {
					return parentNode;
				}
			}
		}
		if (null == treeNodes) {
			
		}
		return null;
	}
	
	
	/**
	 * 把TreeNode转化为JsonString,如果字段值为空则忽略该字段
	 * @author yeyunfeng 2014-8-29  下午6:21:08
	 * @param node
	 * @return
	 *
	 */
	public static String treeNode2String(TreeNode node) {
		if (null == node) {
			return "";
		}
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("{");
		
		if (null != node.getId()) {
			jsonString.append("id:\"" + node.getId() + "\",");
		}
		if (null != node.getName()) {
			jsonString.append("name:\"" + node.getName() + "\",");
		}
		if (null != node.getIcon()) {
			jsonString.append("icon:\"" + node.getIcon() + "\",");
		}
		if (null != node.getIsParent()) {
			jsonString.append("isParent:" + node.getIsParent() + ",");
		}
		if (null != node.getUrl()) {
			jsonString.append("url:\"" + node.getUrl() + "\",");
		}
		if (null != node.getTarget()) {
			jsonString.append("target:\"" + node.getTarget() + "\",");
		}
		if (null != node.getOpen()) {
			jsonString.append("open:" + node.getOpen() + ",");
		}
		if (null != node.getChecked()) {
			jsonString.append("checked:" + node.getChecked() + ",");
		}
		if (null != node.getNocheck()) {
			jsonString.append("nocheck:" + node.getNocheck() + ",");
		}
		if (null != node.getDisabled()) {
			jsonString.append("disabled:" + node.getDisabled() + ",");
		}
		if (null != node.getNodes()) {
			jsonString.append("nodes:" + treeNodeList2String(node.getNodes()) + ",");
		}
		if (null != node.getValueMap()) {
			jsonString.append("valueMap:" + JSONSerializer.toJSON(node.getValueMap()) + ",");
		}
		int length = jsonString.length();
		if (length == 1) {
			jsonString.append("}");
		} else {
			jsonString.replace(length - 1, length, "}");
		}
		
		return jsonString.toString();
	}
	
	/**
	 * 把一组TreeNode转化为JsonString
	 * @author yeyunfeng 2014-8-29  下午6:21:20
	 * @param treeNodes
	 * @return
	 *
	 */
	public static String treeNodeList2String(List<TreeNode> treeNodes) {
		if (null == treeNodes || 0 == treeNodes.size()) {
			return "[]";
		}
		StringBuffer jsonString = new StringBuffer();
		jsonString.append("[");
		for (TreeNode node : treeNodes) {
			jsonString.append(treeNode2String(node) + ",");
		}
		int length = jsonString.length();
		// 去掉最后个','并换成']'
		jsonString.replace(length - 1, length, "]");
		return jsonString.toString();
	}
	
}
