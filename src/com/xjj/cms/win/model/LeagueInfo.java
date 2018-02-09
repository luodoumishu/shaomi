package com.xjj.cms.win.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.xjj.framework.core.model.SimpleModel;

/**
 * 联赛实体
 * @author xieyuzi
 * 2018年2月3日16:00:32
 *
 */

@Entity
@Table(name = "F_LEAGUE_INFO")
public class LeagueInfo extends SimpleModel{
	
	private static final long serialVersionUID = 1L;
	
	@Column(name = "id")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
    private String id;
	/**
	 * 联赛名称
	 */
	@Column(name = "NAME")
	private String name;
	/**
	 * 所属国家
	 */
	@Column(name = "COUNTRY")
	private String country;
	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;
	/**
	 * 创建时间
	 */
	@Column(name = "CREATE_TIME")
	@Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
	private Date createTime;
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	
	
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	
	
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	
}
