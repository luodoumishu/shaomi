package com.xjj.cms.base.VO;

/**
 * @Description: 常用常量
 * @author fengjunkong
 * @created 2014-9-3 下午5:11:30
 */
public class CmsCC {

	/**
	 * 审核否定
	 */
	public static Integer ARTICLE_CHECK_NO = -2;
	/**
	 * 退回
	 */
	public static Integer ARTICLE_CHECK_BACK = -1;
	/**
	 * 文章刚创建，未提交审核
	 */
	public static Integer ARTICLE_CREATE = 0;
	/**
	 * 审核过程中
	 */
	public static Integer ARTICLE_IN_CHECKING = 1;
	/**
	 * 审核通过
	 */
	public static Integer ARTICLE_CHECK_OK = 2;
	/**
	 * 已发布
	 */
	public static Integer ARTICLE_PUBLISH_OK = 1;
	// ----------评论部分设定-------------
	/**
	 * 评论审核否定
	 */
	public static Integer REVIRE_CHECK_NO = -1;
	/**
	 * 评论刚创建
	 */
	public static Integer REVIRE_CREATE = 0;
	/**
	 * 评论审核通过
	 */
	public static Integer REVIRE_CHECK_OK = 1;
	/**
	 * 是否整体可评论
	 */
	public static boolean CAN_REVIEW = false;
	/**
	 * 评论是否需要审核 默认无需审核
	 */
	public static boolean IS_REVIEW_MUST_CHECK = false;

	/**
	 * 评论模板代码
	 */
	public static String CODE_REVIEW_LIST = "";

	// ----------评论部分设定-------------
	/**
	 * 附件及图片的物理路径
	 */
	public static String ENCLOSURE_PATH = "";
	/**
	 * 附件及图片的访问url地址
	 */
	public static String ENCLOSURE_URI = "";
	/**
	 * 视频服务器地址
	 */
	public static String VIDEO_ENCLOSURE_URI = "";
	/**
	 * 视频服务器地址
	 */
	public static String VIDEO_FWQ_URL = "";
	/**
	 * 静态页面存储路径
	 */
	public static String STATIC_HTML_DIR = "";
	/**
	 * 超链条目图片存储路径（基于enclosure_path之上）
	 */
	public static String LINK_IMAGE_PATH = "";
	/**
	 * 视频存储路径（基于enclosure_path之上）
	 */
	public static String LINK_VIDEO_PATH = "";
	/**
	 * 栏目存储路径，固定
	 */
	public static String LINK_MENU_PATH = "/menu";
	// -------------------------------

	/**
	 * 文章列表公共的排序字段
	 */
	public static String[] commonOrderBy = { "last_update_date",
			"article_issue_time", "id" };
	/**
	 * 文章列表公共的排序字段的排序方法
	 */
	public static boolean[] commonIsAsc = { false, false, false };

	/**
	 * 文章列表公共的排序字段(外网部分)
	 */
	public static String[] commonOrderByForWeb = { "article_issue_time", "id" };
	/**
	 * 文章列表公共的排序字段的排序方法(外网部分)
	 */
	public static boolean[] commonIsAscForWeb = { false, false };

	/**
	 * 当前模版套别代码
	 */
	public static String CUR_TEMPLET_MAIN = "";
	/**
	 * 默认当前模版套别代码
	 */
	public static String DEFAULT_CUR_TEMPLET_MAIN = "default";

	// ---------------频道明细关联类型 开始----------------
	/**
	 * 栏目类型
	 */
	public static Integer RELATED_TYPE_IN_MENU = 1;
	/**
	 * 超链类型
	 */
	public static Integer RELATED_TYPE_IN_LINK = 2;
	/**
	 * 投票类型
	 */
	public static Integer RELATED_TYPE_IN_VOTE = 3;

	// ---------------频道明细关联类型 结束----------------
	/**
	 * lucene全文检索数据存放目录
	 */
	public static String LUCENE_DIR = "";
	/**
	 * 文章标记最新的延迟日期，即多少天以内的文章标记最新标志
	 */
	public static Integer ARTICLE_NEW_FLAG_DAY = 1;

	/**
	 * 栏目类型，文章型的栏目
	 */
	public static Integer MENU_TYPE_ARTICLE = 1;
	/**
	 * 栏目类型，视频型的栏目
	 */
	public static Integer MENU_TYPE_VIDEO = 2;
	/**
	 * 频道明细的路径间隔符号，用于cms_channel_item表的item_path;
	 */
	public static String CCI_SEP_FLAG = "--";

	// -------------------栏目类型设置----------------
	public static Integer MENU_TYPE_REAL = 1;

	public static Integer MENU_TYPE_JUMP = 2;

	public static Integer MENU_TYPE_VIRTUAL = 3;

	/**
	 * 招投标系统访问地址
	 */
	public static String ZTB_ROOT_URL = "";
	/**
	 * 审批系统访问地址
	 */
	public static String SP_ROOT_URL = "";

	/**
	 * 是否栏目分级授权
	 */
	public static Boolean ENABLED_MENU_FJSQ = false;
	
	/**
	 * 栏目分级授权的部门显示级别
	 */
	public static int MENU_FJSQ_ORG_SHOWLEVEL = 1;
	
	/**
	 * 资源配置文件
	 */
	public static String WEB_CONFIG_ZH = "/webconfig_zh.properties";
	public static String ARTICLE_AFFIX_PATH = "article_affix_path";
	public static String ARTICLE_AFFIX_UI = "article_affix_ui";
	public static String MENU_IMAGE_PATH = "menuImg_path";//栏目配图路径
	public static String FILE_PATH = "file_path";//文章附件配图路径
	public static String LINK_PATH = "link_path";//超链接图片路径
	public static String EFFECT_IMAGE_PATH = "effectImg_path";//特效图片路径
	public static String VIDEO_PATH = "video_path";
	/**
	 * 文件格式配置文件
	 */
	public static String FILE_FORMAT = "/FileFormat.properties";
	public static String IMAGES_FORMAT = "imageFormat";
	public static String MENU_IMAGES_SIZE = "menuImageSize";
	public static String AFFIX_FORMAT = "fileFormat";
	public static String FILE_SIZE = "fileSize";
	public static String VIDEO_SIZE2PR = "videoSize";
	public static String VIDEO_FORMAT2PR = "videoFormat";

	/**
	 * 过滤关键字
	 */
	public static String[] keyWordBlank = { "and", "exec", "count", "chr",
			"mid", "master", "or", "truncate", "char", "declare", "join" ,"script"};

	public static String[] keyWordNoBlank = { "<", ">", "/*", ";", "*/", "'","\"",
			"|", "\\u", "insert", "select", "delete", "update", "create",
			"drop","having","alert","window","eval","onmouseover","new",
			"function","edit","add","script","location","document"};
}
