package com.xjj._extensions.groupUser.model;

// default package

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.xjj.framework.core.model.SimpleModel;

/**
 * ZGroupTypeDo entity. @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "z_group_type")
public class ZGroupType extends SimpleModel implements java.io.Serializable
{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    /**
     * 
     */

    // Fields

    @Column(name = "id")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
    private String id;

    @Column(name = "type_name", length = 100)
    private String typeName;

    @Column(name = "remark", length = 150)
    private String remark;

    // Constructors

    /** default constructor */
    public ZGroupType()
    {
    }

    /** minimal constructor */
    public ZGroupType(String id)
    {
        this.id = id;
    }

    /** full constructor */
    public ZGroupType(String id, String typeName, String remark)
    {
        this.id = id;
        this.typeName = typeName;
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

    public String getTypeName()
    {
        return this.typeName;
    }

    public void setTypeName(String typeName)
    {
        this.typeName = typeName;
    }

    public String getRemark()
    {
        return this.remark;
    }

    public void setRemark(String remark)
    {
        this.remark = remark;
    }

}