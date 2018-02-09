package com.xjj.cms.base.util.progress;


/**
 * 
 * 创建人：yeyunfeng <br>
 * 功能描述： 文件上传进度model<br>
 */
public class Progress {
	/** 处理动作名称 **/
	private String actionName;
	/** 已读字节 **/
	private long bytesRead = 0L;
	/** 已读MB **/
	private String mbRead = "0";
	/** 总长度 **/
	private long contentLength = 0L;
	/****/
	private int items;
	/** 已读百分比 **/
	private String percent;
	/** 读取速度 **/
	private String speed;
	/** 开始读取的时间 **/
	private long startReatTime = System.currentTimeMillis();

	/** 转换百分比 用于一些没有根据mbRead、contentLength计算**/
	private String conv_percent;


	public String getActionName() {
		return actionName;
	}

	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public long getBytesRead() {
		return bytesRead;
	}

	public void setBytesRead(long bytesRead) {
		this.bytesRead = bytesRead;
	}

	public long getContentLength() {
		return contentLength;
	}

	public void setContentLength(long contentLength) {
		this.contentLength = contentLength;
	}

	public int getItems() {
		return items;
	}

	public void setItems(int items) {
		this.items = items;
	}

	public String getPercent() {
		percent = NumUtil.getPercent(bytesRead, contentLength);
		return percent;
	}

	public void setPercent(String percent) {
		this.percent = percent;
	}

	public String getSpeed() {
		speed = NumUtil.divideNumber(
								NumUtil.divideNumber(bytesRead * 1000, System.currentTimeMillis()- startReatTime), 
								1000);
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public long getStartReatTime() {
		return startReatTime;
	}

	public void setStartReatTime(long startReatTime) {
		this.startReatTime = startReatTime;
	}

	public String getMbRead() {
		mbRead = NumUtil.divideNumber(bytesRead, 1000000);
		return mbRead;
	}

	public void setMbRead(String mbRead) {
		this.mbRead = mbRead;
	}

	public String getConv_percent() {
		return conv_percent;
	}

	public void setConv_percent(String conv_percent) {
		this.conv_percent = conv_percent;
	}

	@Override
	public String toString() {
		return "Progress{" +
				"actionName='" + actionName + '\'' +
				", bytesRead=" + bytesRead +
				", mbRead='" + mbRead + '\'' +
				", contentLength=" + contentLength +
				", items=" + items +
				", percent='" + percent + '\'' +
				", speed='" + speed + '\'' +
				", startReatTime=" + startReatTime +
				", conv_percent=" + conv_percent +
				'}';
	}
}