package com.xjj.cms.win.model;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.GenericGenerator;

import com.xjj.framework.core.model.SimpleModel;

/**
 * 球队实体
 * @author xieyuzi
 * 2018年2月3日22:53:56
 */
public class TeamInfo extends SimpleModel{

	private static final long serialVersionUID = 1L;
	@Column(name = "ID")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
	private String id;
	/**
	 * 球队名称
	 */
	private String name;
	/**
	 * 所属联赛
	 */
	private String leagueInfoId;
	/**
	 * 
	 */
	private String seasonInfoId;
	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;
	

}
