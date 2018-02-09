package com.xjj.cms.base.util.ztree;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * 树节点后台对应实体 
 * <p>
 * @author yeyunfeng 2014-8-29 下午6:20:02 
 * @modificationHistory=========================逻辑或功能性重大变更记录
 * @modify by user: {修改人} 2014-8-29
 * @modify by reason:{方法名}:{原因}
 */
public class TreeNode implements Serializable {
	
	/** 序列化ID */
	private static final long serialVersionUID = -1623830314374905742L;
	
	/** ID */
	private String id;
	/** 节点显示的名称 */
	private String name;
	/** 节点自定义图标 */
	private String icon;
	/**
	 * 是否非根节点
	 * <p>
	 * 需要对某节点被点击时触发异步获取子节点的事件
	 */
	private Boolean isParent;
	/** 点击时跳转的URL */
	private String url;
	/** 点击时弹出的URL页面 */
	private String target;
	/** 是否初始展开 */
	private Boolean open;
	/** 初始化时是否勾选其 CheckBox */
	private Boolean checked;
	/** 设置该节点不显示CheckBox */
	private Boolean nocheck;
	/** 节点是否可点击 */
	private Boolean disabled;
	/** 子节点数据 */
	private List<TreeNode> nodes;
	/** 需要时用于存储一些其他属性值 */
	private Map<String, Object> valueMap;
	
	public TreeNode() {
	}
	
	public TreeNode(String id, String name, String icon, Boolean isParent, String url, String target, Boolean open,
	        Boolean checked, Boolean nocheck, Boolean disabled, List<TreeNode> nodes, Map<String, Object> valueMap) {
		this.id = id;
		this.name = name;
		this.icon = icon;
		this.isParent = isParent;
		this.url = url;
		this.target = target;
		this.open = open;
		this.checked = checked;
		this.nocheck = nocheck;
		this.disabled = disabled;
		this.nodes = nodes;
		this.setValueMap(valueMap);
	}
	
	@Override
    public int hashCode() {
	    final int prime = 31;
	    int result = 1;
	    result = prime * result + ((id == null)?0:id.hashCode());
	    return result;
    }

	@Override
    public boolean equals(Object obj) {
	    if (this == obj)
		    return true;
	    if (obj == null)
		    return false;
	    if (getClass() != obj.getClass())
		    return false;
	    TreeNode other = (TreeNode)obj;
	    if (id == null) {
		    if (other.id != null)
			    return false;
	    } else
		    if (!id.equals(other.id))
			    return false;
	    return true;
    }

	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getIcon() {
		return icon;
	}
	
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public Boolean getIsParent() {
		return isParent;
	}
	
	public void setIsParent(Boolean isParent) {
		this.isParent = isParent;
	}
	
	public String getUrl() {
		return url;
	}
	
	public void setUrl(String url) {
		this.url = url;
	}
	
	public String getTarget() {
		return target;
	}
	
	public void setTarget(String target) {
		this.target = target;
	}
	
	public Boolean getOpen() {
		return open;
	}
	
	public void setOpen(Boolean open) {
		this.open = open;
	}
	
	public Boolean getChecked() {
		return checked;
	}
	
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	
	public Boolean getNocheck() {
		return nocheck;
	}
	
	public void setNocheck(Boolean nocheck) {
		this.nocheck = nocheck;
	}
	
	public Boolean getDisabled() {
		return disabled;
	}
	
	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}
	
	public List<TreeNode> getNodes() {
		return nodes;
	}
	
	public void setNodes(List<TreeNode> nodes) {
		this.nodes = nodes;
	}
	
	public Map<String, Object> getValueMap() {
		return valueMap;
	}

	public void setValueMap(Map<String, Object> valueMap) {
	    this.valueMap = valueMap;
    }

	@Override
	public String toString() {
		return "TreeNode [id=" + id + ", name=" + name + ", icon=" + icon
				+ ", isParent=" + isParent + ", url=" + url + ", target="
				+ target + ", open=" + open + ", checked=" + checked
				+ ", nocheck=" + nocheck + ", disabled=" + disabled
				+ ", nodes=" + nodes + ", valueMap=" + valueMap + "]";
	}
	
}
