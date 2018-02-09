package com.xjj._extensions.groupUser.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TreeHelper
{
    public TreeHelper()
    {

    }

    // 执行函数
    @SuppressWarnings("rawtypes")
    public TreeNode execFunction(List<TreeNode> nodeList, String root)
    {
        HashMap tempNodeMap = putNodesIntoMap(nodeList);
        HashMap tempParentNodeMap = putParentNodesIntoMap(nodeList);
        HashMap nodeMap = putChildIntoParent(tempNodeMap, tempParentNodeMap);

        TreeNode treeNode = (TreeNode) nodeMap.get(root);

        return treeNode;
    }

    // 将list集合转换成hashmap
    /**
     * put all the treeNodes into a hash table by its id as the key
     * 
     * @return hashmap that contains the treenodes
     */
    @SuppressWarnings("rawtypes")
    public HashMap putNodesIntoMap(List<TreeNode> nodeList)
    {

        HashMap<Object, TreeNode> nodeMap = new HashMap<Object, TreeNode>();
        Iterator it = nodeList.iterator();
        while (it.hasNext())
        {
            TreeNode treeNode = (TreeNode) it.next();
            String selfId = treeNode.getSelfId();
            String keyId = String.valueOf(selfId);
            nodeMap.put(keyId, treeNode);
        }
        return nodeMap;
    }

    // 只存储父节点

    @SuppressWarnings("rawtypes")
    public HashMap putParentNodesIntoMap(List<TreeNode> nodeList)
    {
        HashMap<Object, TreeNode> parentNodeMap = new HashMap<Object, TreeNode>();
        Iterator it = nodeList.iterator();
        while (it.hasNext())
        {
            TreeNode treeNode = (TreeNode) it.next();
            String keyId = treeNode.getParentId();

            parentNodeMap.put(keyId, treeNode);
        }
        return parentNodeMap;
    }

    // 递归调用
    /**
     * set the parent nodes point to the child nodes
     * 
     * @param nodeMap
     *            a hashmap that contains all the treenodes by its id as the key
     */
    @SuppressWarnings("rawtypes")
    public HashMap putChildIntoParent(HashMap nodeMap, HashMap parentNodeMap)
    {
        List<TreeNode> tempNodeList = new ArrayList<TreeNode>();

        Iterator it = nodeMap.values().iterator();
        // 遍历所有节点
        while (it.hasNext())
        {
            TreeNode treeNode = (TreeNode) it.next();

            String parentKeyId = treeNode.getParentId();
            String selfKeyId = treeNode.getSelfId();

            // 如果是叶子节点，则将叶子节点指向其父节点，然后从列表中删除
            if (!parentNodeMap.containsKey(selfKeyId))
            {
                if (nodeMap.containsKey(parentKeyId))
                {
                    TreeNode parentNode = (TreeNode) nodeMap.get(parentKeyId);
                    it.remove();
                    parentNode.addChildNode(treeNode);
                }
            }
        }
        // tempit 为剩下的节点
        Iterator tempit = nodeMap.values().iterator();
        while (tempit.hasNext())
        {
            TreeNode treeNode = (TreeNode) tempit.next();
            tempNodeList.add(treeNode);
        }
        HashMap tempNodeMap = putNodesIntoMap(tempNodeList);
        HashMap tempParentNodeMap = putParentNodesIntoMap(tempNodeList);
        if (tempParentNodeMap.size() > 1)
        {
            putChildIntoParent(tempNodeMap, tempParentNodeMap);
        }
        else
        {
            return nodeMap;
        }

        return nodeMap;

    }

    public static void validateFieldMap(Map fieldMap) throws Exception
    {
        String message = null;

        if (!fieldMap.containsKey("id"))
            message = "key id is not exist in fieldMap";
        if (!fieldMap.containsKey("parentId"))
            message = "key id is not exist in fieldMap";
        if (!fieldMap.containsKey("text"))
            message = "key id is not exist in fieldMap";
        if (message != null)
        {
            throw new Exception(message);
        }
    }

    private String[] getTreeInfoFromObj(Object ObjEntity, Map fieldMap)
            throws Exception
    {
        String[] treeNodeInfo = new String[3];
        
        Class realEntity = (Class) ObjEntity.getClass();
        
        Field[] fs = realEntity.getDeclaredFields(); for (Field field : fs) {
        field.setAccessible(true); if
        (fieldMap.get("parentId").equals(field.getName())) { treeNodeInfo[0]
        = field.get(ObjEntity) == null ? null : field
        .get(ObjEntity).toString(); } else if
        (fieldMap.get("id").equals(field.getName())) { treeNodeInfo[1] =
        field.get(ObjEntity) == null ? null : field
        .get(ObjEntity).toString(); } else if
        (fieldMap.get("text").equals(field.getName())) { treeNodeInfo[2] =
        (String) field.get(ObjEntity); }
        
        }
        
        treeNodeInfo[0] = (String) getFieldValue(ObjEntity,
                fieldMap.get("parentId").toString());
        treeNodeInfo[1] = (String) getFieldValue(ObjEntity, fieldMap.get("id")
                .toString());
        treeNodeInfo[2] = (String) getFieldValue(ObjEntity, fieldMap
                .get("text").toString());
        return treeNodeInfo;
    }

    @SuppressWarnings("unchecked")
    public List<TreeNode> convertListToTreeNodes(List entityList, Map fieldMap)
            throws Exception
    {
        validateFieldMap(fieldMap);
        Object ObjEntity = new Object();
        List<TreeNode> tempNodeList = new ArrayList<TreeNode>();
        TreeNode treeNode;

        Iterator<Object> it = entityList.iterator();
        while (it.hasNext())
        {
            ObjEntity = it.next();
            treeNode = new TreeNode();
            treeNode.setObj(ObjEntity);

            String[] treeNodeInfo = getTreeInfoFromObj(ObjEntity, fieldMap);

            treeNode.setParentId(treeNodeInfo[0]);
            treeNode.setSelfId(treeNodeInfo[1]);
            treeNode.setText(treeNodeInfo[2]);
            tempNodeList.add(treeNode);
        }
        return tempNodeList;
    }

    public TreeNode listToTreeByRootNode(List list, Map fieldMap, Object root)
            throws Exception
    {
        TreeHelper.validateFieldMap(fieldMap);
        String selfId = (String) TreeHelper.getFieldValue(root,
                fieldMap.get("id").toString());
        String text = (String) TreeHelper.getFieldValue(root,
                fieldMap.get("text").toString());
        String parentId = (String) TreeHelper.getFieldValue(root,
                fieldMap.get("parentId").toString());
        TreeNode rootNode = new TreeNode(parentId, selfId, text, root);
        List<TreeNode> treeNodeList = convertListToTreeNodes(list, fieldMap);
        treeNodeList.add(rootNode);

        return execFunction(treeNodeList, selfId);
    }

    public TreeNode listToTreeByRootId(List list, Map fieldMap, String rootId)
            throws Exception
    {
        List<TreeNode> treeNodeList = convertListToTreeNodes(list, fieldMap);
        return execFunction(treeNodeList, rootId);
    }

    public static Object getFieldValue(Object aObject, String aFieldName)
    {
        Field field = getClassField(aObject.getClass(), aFieldName);
        if (field != null)
        {
            field.setAccessible(true);
            try
            {
                return field.get(aObject);
            }
            catch (Exception e)
            {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    private static Field getClassField(Class aClazz, String aFieldName)
    {
        Field[] declaredFields = aClazz.getDeclaredFields();
        for (Field field : declaredFields)
        {
            // 注意：这里判断的方式，是用字符串的比较。很傻瓜，但能跑。要直接返回Field。我试验中，尝试返回Class，然后用getDeclaredField(String
            // fieldName)，但是，失败了
            if (field.getName().equals(aFieldName))
            {
                return field;// define in this class
            }
        }

        Class superclass = aClazz.getSuperclass();
        if (superclass != null)
        {// 简单的递归一下
            return getClassField(superclass, aFieldName);
        }
        return null;
    }
}
