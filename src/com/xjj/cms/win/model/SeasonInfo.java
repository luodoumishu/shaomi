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
 * 赛季实体
 * @author xieyuzi
 * 2018年2月3日19:31:04
 *
 */
@Entity
@Table(name = "F_SEASON_INFO")
public class SeasonInfo extends SimpleModel{

	private static final long serialVersionUID = 1L;
	
	@Column(name = "ID")
    @Id
    @GenericGenerator(name = "pk", strategy = "org.hibernate.id.UUIDGenerator")
    @GeneratedValue(generator = "pk")
	private String id;
	/**
	 * 赛季名称
	 */
	@Column(name = "NAME")
	private String name;
	/**
	 * 所在联赛id
	 */
	@Column(name = "LEAGUE_INFO_ID")
	private String leagueInfoId;
	/**
	 * 所在联赛名称
	 */
	@Column(name = "LEAGUE_INFO_NAME")
	private String leagueInfoName;
	/**
	 * 比赛轮数
	 */
	@Column(name = "GAME_ROUND")
	private String gameRound;
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
	public String getLeagueInfoId() {
		return leagueInfoId;
	}
	public void setLeagueInfoId(String leagueInfoId) {
		this.leagueInfoId = leagueInfoId;
	}
	public String getGameRound() {
		return gameRound;
	}
	public void setGameRound(String gameRound) {
		this.gameRound = gameRound;
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
	public String getLeagueInfoName() {
		return leagueInfoName;
	}
	public void setLeagueInfoName(String leagueInfoName) {
		this.leagueInfoName = leagueInfoName;
	}
	
}
