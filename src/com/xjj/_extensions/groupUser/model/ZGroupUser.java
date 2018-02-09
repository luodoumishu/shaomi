package com.xjj._extensions.groupUser.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

import com.xjj.framework.core.model.SimpleModel;

/**
 * ZGroupUserDo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "z_group_user")
public class ZGroupUser extends SimpleModel implements java.io.Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // Fields

    /**
     * 
     */

    @Column(name = "id")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
    private String id;

    @Column(name = "group_id", nullable = false)
    private String groupId;

    @Transient
    private String groupName;

    @Column(name = "user_id", nullable = false, length = 50)
    private String userId;

    @Column(name = "user_name", length = 100)
    private String userName;

    @Column(name = "remark", length = 150)
    private String remark;

    @Column(name = "pri")
    private Integer pri;// 排序

    @Transient
    private String userAccount;

    // Constructors

    /** default constructor */
    public ZGroupUser()
    {
    }

    /** minimal constructor */
    public ZGroupUser(String id, String groupId, String userId)
    {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
    }

    /** full constructor */
    public ZGroupUser(String id, String groupId, String userId,
            String userName, String remark)
    {
        this.id = id;
        this.groupId = groupId;
        this.userId = userId;
        this.userName = userName;
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

    public String getGroupId()
    {
        return this.groupId;
    }

    public void setGroupId(String groupId)
    {
        this.groupId = groupId;
    }

    public String getUserId()
    {
        return this.userId;
    }

    public void setUserId(String userId)
    {
        this.userId = userId;
    }

    public String getUserName()
    {
        return this.userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

    public Integer getPri()
    {
        return pri;
    }

    public void setPri(Integer pri)
    {
        this.pri = pri;
    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public String getUserAccount()
    {
        return userAccount;
    }

    public void setUserAccount(String userAccount)
    {
        this.userAccount = userAccount;
    }

}