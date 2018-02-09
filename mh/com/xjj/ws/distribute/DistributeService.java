package com.xjj.ws.distribute;

import java.util.List;
import java.util.Map;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import com.xjj._extensions.roleUserPer.model.ZOrgUser;
import com.xjj._extensions.roleUserPer.service.IZOrgUserService;
import com.xjj._extensions.roleUserPer.service.IZuserService;
import com.xjj.sso.util.Xml2Object;
import com.xjj.ws.distribute.model.Zorganize2;
import com.xjj.ws.distribute.model.Zuser2;
import com.xjj.ws.distribute.service.Zorganize2Service;
import com.xjj.ws.distribute.service.Zuser2Service;

@WebService(endpointInterface = "com.xjj.ws.distribute.Distribute", 
			targetNamespace = "http://distribute.ws.xjj.com/",
			wsdlLocation = "META-INF/wsdl/distribute.wsdl",
			serviceName = "DistributeService")
public class DistributeService implements Distribute {
	
	@Autowired
	@Qualifier("zorganize2Service")
	private Zorganize2Service zorganize2Service;
	
	@Autowired
	@Qualifier("zuser2Service")
	private Zuser2Service zuser2Service;
	
	@Autowired
    @Qualifier("ZuserService")
    private IZuserService zuserService;
	
	@Autowired
	@Qualifier("ZOrgUserService")
	private IZOrgUserService zOrgUserService;
	
	@Override
	public boolean orgDistribute(String orgXML) {
		/**
		 * <?xml version="1.0" encoding="UTF-8" ?>
		 * <root>
		 * 	<org>
		 * 		<id>7775</id>
		 * 		<name><![CDATA[维护部门]]></name>
		 * 		<orgDesc><![CDATA[维护部门]]></orgDesc>
		 * 		<parentId>0</parentId>
		 * 		<priority>20</priority>
		 * 		<phoneNo></phoneNo>
		 * 		<faxNo></faxNo>
		 * 		<addr><![CDATA[]]></addr>
		 * 		<linkMan><![CDATA[]]></linkMan>
		 * 		<linkMan_phoneNo></linkMan_phoneNo>
		 * 		<status>0</status>  <!-- 0正常     1物理删除    5逻辑删除 -->
		 * 	</org>
		 * </root>
		 */
		try {
			List<Map<String,String>> list = Xml2Object.xml2Object(orgXML, "org");
			if(list != null && list.size()>0) {
				String status = "";
				for(Map<String, String> m : list) {
					status = m.get("status");
					if(status.equals("0")) {
						Zorganize2 model = new Zorganize2();
						model.setId(m.get("id"));
						model.setName(m.get("name"));
						model.setParentId(m.get("parentId"));
						model.setPri(Integer.parseInt(m.get("priority")));
						model.setCreateTime(new java.util.Date());
						this.zorganize2Service.saveOrUpdate(model);
						/**
						 * TODO: 直接添加或者删除,不使用原来的方法.避免级联(因为统一用户平台分发过来的数据顺序不一)
						 */
					} else if(status.equals("1") || status.equals("5")) {
						this.zorganize2Service.deleteByPK(m.get("id"));
					}
				} 
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public boolean userDistribute(String userXML) {
		/**
		 * <?xml version="1.0" encoding="UTF-8" ?>
		 * <root>
		 * 	<user>
		 * 		<id>860293</id>
		 * 		<account>sgyadmin</account>
		 * 		<orgId>7775</orgId>
		 * 		<pwdLimitDate>2043-10-29 00:00:00.0</pwdLimitDate>
		 * 		<loginMode>0</loginMode>
		 * 		<pwdErrNum>0</pwdErrNum>
		 * 		<pwdErrLock>0</pwdErrLock>
		 * 		<priority>0</priority>
		 * 		<name><![CDATA[省高院管理员]]></name>
		 * 		<duty><![CDATA[]]></duty>
		 * 		<addr><![CDATA[]]></addr>
		 * 		<phoneNo></phoneNo>
		 * 		<mobileNo></mobileNo>
		 * 		<faxNo></faxNo>
		 * 		<email></email>
		 * 		<gender><![CDATA[男]]></gender>
		 * 		<userDesc><![CDATA[]]></userDesc>
		 * 		<certificate><![CDATA[]]></certificate>
		 * 		<status>5</status> <!-- 0正常     1锁定   2物理删除    5逻辑删除 -->
		 * 	</user>
		 * </root>
		 */
		try {
			List<Map<String,String>> list = Xml2Object.xml2Object(userXML, "user");
			if(list != null && list.size()>0) {
				String status = "";
				ZOrgUser zu;
				for(Map<String, String> m : list) {
					status = m.get("status");
					if(status.equals("0") || status.equals("1")) {
						Zuser2 zuser2 = new Zuser2();
						zuser2.setId(m.get("id"));
						zuser2.setAccount(m.get("account"));
						zuser2.setName(m.get("name"));
						zuser2.setCreateTime(new java.util.Date());
						zuser2.setPassword("xjj876543");
						zuser2.setPri(Integer.parseInt(m.get("priority")));
						zuser2.setPhoneNo(m.get("mobileNo"));
						zu = this.zOrgUserService.checkExistZOrgUser(m.get("id"));
						/**
						 * TODO: 目前统一用户平台用户和部门是一一对应的,就这样解决吧
						 */
						if(zu == null) { 
							zu = new ZOrgUser();
							zu.setOrgId(m.get("orgId"));
							zu.setUserId(m.get("id"));
							zu.save();
						} else {
							zu.setOrgId(m.get("orgId"));
							zu.update();
						}
						this.zuser2Service.saveOrUpdate(zuser2);
						
					} else {
						this.zuserService.deleteByPK(m.get("id"));
					}
				}
			}
		} catch (Exception e) { 
			e.printStackTrace();
		}
		return true;
	}
}
