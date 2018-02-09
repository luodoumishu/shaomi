package com.xjj._extensions.groupUser.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TreeNode implements Serializable

{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * 
     */
    private String parentId;
    private String selfId;
    protected String text;

    protected Object obj;
    protected TreeNode pNode;
    private boolean singleClickExpand = true;
    private String url;
    private boolean leaf;
    protected List<TreeNode> child = new ArrayList<TreeNode>();

    public TreeNode()
    {
        super();
    }

    public TreeNode(String parentId, String selfId, String text, Object obj)
    {
        super();
        this.parentId = parentId;
        this.selfId = selfId;
        this.text = text;
        this.obj = obj;
    }

    public String getParentId()
    {
        return parentId;
    }

    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }

    public String getSelfId()
    {
        return selfId;
    }

    public void setSelfId(String selfId)
    {
        this.selfId = selfId;
    }

    public String getText()
    {
        return text;
    }

    public void setText(String text)
    {
        this.text = text;
    }

    public Object getObj()
    {
        return obj;
    }

    public void setObj(Object obj)
    {
        this.obj = obj;
    }

    public boolean isSingleClickExpand()
    {
        return singleClickExpand;
    }

    public void setSingleClickExpand(boolean singleClickExpand)
    {
        this.singleClickExpand = singleClickExpand;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public boolean isLeaf()
    {
        return leaf;
    }

    public void setLeaf(boolean leaf)
    {
        this.leaf = leaf;
    }

    public void addChildNode(TreeNode treeNode)
    {
        this.child.add(treeNode);
    }

    public TreeNode getpNode()
    {
        return pNode;
    }

    public void setpNode(TreeNode pNode)
    {
        this.pNode = pNode;
    }

    public List<TreeNode> getChild()
    {
        return child;
    }

    public void setChild(List<TreeNode> child)
    {
        this.child = child;
    }

}
