package com.xjj._extensions.groupUser.util;

import java.util.List;

import com.xjj._extensions.groupUser.model.ZGroup;
import com.xjj._extensions.groupUser.model.ZGroupUser;
import com.xjj._extensions.groupUser.service.IZGroupService;
import com.xjj.framework.core.util.SpringContextUtil;

public class GroupUtil {
	public static IZGroupService zGroupService = SpringContextUtil
			.getInstance().getBean("ZGroupService");

	// 获取该组类型下所有组信息
	public static List<ZGroup> getGroupsByTypeId(String typeId) {
		return zGroupService.getGroupsByTypeId(typeId);

	}

	// 通过组ID获取组里用户信息
	public static List<ZGroupUser> getUsersByGroupId(String groupId) {
		return zGroupService.getUsersByGroupId(groupId);
	}

	// 通过组织ID与组ID获取用户信息
	public static List<ZGroupUser> getUserFilterByOrg(String orgId,
			String groupId) {
		return zGroupService.getUserFilterByOrg(orgId, groupId);
	}

	// 获取用户所在组织下同组用户信息
	public static List<ZGroupUser> getUserFilterByUserOrg(String userId,
			String groupId) {
		return zGroupService.getUserFilterByUserOrg(userId, groupId);
	}
}
