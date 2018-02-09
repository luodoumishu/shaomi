package com.xjj.cms.video.model;

import com.xjj.cms.base.model.CMSBaseModel;
import com.xjj.framework.audit.NoAuditLog;
import com.xjj.framework.core.model.annotation.Label;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CMS_VIDEO")
@Label("视频实体")
@NoAuditLog
public class CmsVideo extends CMSBaseModel {

	private static final long serialVersionUID = 1L;

	private String name;

	private String menuId;

	private String menuName;

	private String remark;

	private String attachId;

	private String imgId;

	private Integer read_count;
	
	private String img_filename;
	
	private String video_filename;
	
	private String img_filepath;
	
	private String video_filepath;
	
	private String img_ext;
	
	private String video_ext;
	
	private Integer img_filesize;
	
	private Integer video_filesize;
	
	private String img_localname;
	
	private String video_localname;

	/**
	 *  0-未上传  1-上传完成 2-未转换 3-转换完成
	 */
	private String state;

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "MENU_ID")
	public String getMenuId() {
		return menuId;
	}

	public void setMenuId(String menuId) {
		this.menuId = menuId;
	}

	@Column(name = "REMARK")
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "MENU_NAME")
	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	@Column(name = "ATTACH_ID")
	public String getAttachId() {
		return attachId;
	}

	public void setAttachId(String attachId) {
		this.attachId = attachId;
	}

	@Column(name = "IMG_ID")
	public String getImgId() {
		return imgId;
	}

	public void setImgId(String imgId) {
		this.imgId = imgId;
	}
	
	@Column(name = "READ_COUNT")
	public Integer getRead_count() {
		return read_count;
	}

	public void setRead_count(Integer read_count) {
		this.read_count = read_count;
	}

	@Column(name = "IMG_FILENAME")
	public String getImg_filename() {
		return img_filename;
	}

	public void setImg_filename(String img_filename) {
		this.img_filename = img_filename;
	}

	@Column(name = "VIDEO_FILENAME")
	public String getVideo_filename() {
		return video_filename;
	}

	public void setVideo_filename(String video_filename) {
		this.video_filename = video_filename;
	}

	@Column(name = "IMG_FILEPATH")
	public String getImg_filepath() {
		return img_filepath;
	}

	public void setImg_filepath(String img_filepath) {
		this.img_filepath = img_filepath;
	}

	@Column(name = "VIDEO_FILEPATH")
	public String getVideo_filepath() {
		return video_filepath;
	}

	public void setVideo_filepath(String video_filepath) {
		this.video_filepath = video_filepath;
	}

	@Column(name = "IMG_EXT")
	public String getImg_ext() {
		return img_ext;
	}

	public void setImg_ext(String img_ext) {
		this.img_ext = img_ext;
	}

	@Column(name = "VIDEO_EXT")
	public String getVideo_ext() {
		return video_ext;
	}

	public void setVideo_ext(String video_ext) {
		this.video_ext = video_ext;
	}

	@Column(name = "IMG_FILESIZE")
	public Integer getImg_filesize() {
		return img_filesize;
	}

	public void setImg_filesize(Integer img_filesize) {
		this.img_filesize = img_filesize;
	}

	@Column(name = "VIDEO_FILESIZE")
	public Integer getVideo_filesize() {
		return video_filesize;
	}

	public void setVideo_filesize(Integer video_filesize) {
		this.video_filesize = video_filesize;
	}

	@Column(name = "IMG_LOCALNAME")
	public String getImg_localname() {
		return img_localname;
	}

	public void setImg_localname(String img_localname) {
		this.img_localname = img_localname;
	}

	@Column(name = "VIDEO_LOCALNAME")
	public String getVideo_localname() {
		return video_localname;
	}

	public void setVideo_localname(String video_localname) {
		this.video_localname = video_localname;
	}

	@Column(name = "STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
}
