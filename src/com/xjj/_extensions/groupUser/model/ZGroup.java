package com.xjj._extensions.groupUser.model;

// default package

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.xjj.framework.core.model.SimpleModel;

/**
 * ZGroupDo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "z_group")
public class ZGroup extends SimpleModel
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Fields

    /**
     * 
     */

    /**
     * 
     */
    @Column(name = "id")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
    private String id;

    @Column(name = "group_name", length = 100)
    private String groupName;

    @Column(name = "type_id")
    private String typeId;

    @Transient
    private String typeName;

    @Column(name = "remark", length = 150)
    private String remark;

    @Transient
    private List<String> userIds = null;

    // Constructors

    /** default constructor */
    public ZGroup()
    {
    }

    /** full constructor */
    public ZGroup(String groupName, String typeId, String remark)
    {
        this.groupName = groupName;
        this.typeId = typeId;
        this.remark = remark;
    }

    public String getId()
    {
        return this.id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getGroupName()
    {
        return this.groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getTypeId()
    {
        return this.typeId;
    }

    public void setTypeId(String typeId)
    {
        this.typeId = typeId;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public String getTypeName()
    {
        return typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public List<String> getUserIds()
    {
        return userIds;
    }

    public void setUserIds(List<String> userIds)
    {
        this.userIds = userIds;
    }

}