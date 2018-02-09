package com.xjj._extensions.web.filter;

import com.xjj.framework.extensions.IWebContextHandle;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created with IntelliJ IDEA.
 * User: Zhengyuyang
 * Date: 14-1-23
 */
public class WebContextHandle implements IWebContextHandle{

    private String sessionId;

    private String tenantCode;

    // private final long processingTimeMillis;

    private String backUrl;

    private String userId;

    private String userName;

    private String orgID;

    private String orgName;

    private String orgRootId;



    public WebContextHandle() {
    }

    @Override
    public WebContextHandle populate(HttpServletRequest request,
                            HttpServletResponse response, ServletContext servletContext) {
        this.sessionId = request.getSession().getId();
        this.tenantCode = (String) request.getSession().getAttribute("tenantCode");
        this.backUrl = "";
        this.userId = (String) request.getSession().getAttribute("userId");
        this.userName = (String) request.getSession().getAttribute("userName");
        this.orgID = (String) request.getSession().getAttribute("orgId");
        this.orgName =  (String) request.getSession().getAttribute("orgName");
        this.orgRootId = (String) request.getSession().getAttribute("orgRootId");
        return this;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    @Override
    public String getTenantCode() {
        return tenantCode;
    }

    public void setTenantCode(String tenantCode) {
        this.tenantCode = tenantCode;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getOrgID() {
        return orgID;
    }

    public void setOrgID(String orgID) {
        this.orgID = orgID;
    }

    public String getOrgRootId() {
        return orgRootId;
    }

    public void setOrgRootId(String orgRootId) {
        this.orgRootId = orgRootId;
    }
}
