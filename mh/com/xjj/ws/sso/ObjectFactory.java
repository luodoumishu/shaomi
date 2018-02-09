
package com.xjj.ws.sso;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.xjj.ws.sso package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _CheckLoginByToken_QNAME = new QName("http://sso.ws.xjj.com/", "checkLoginByToken");
    private final static QName _GetUserInfoByAccount_QNAME = new QName("http://sso.ws.xjj.com/", "getUserInfoByAccount");
    private final static QName _GetUserInfoResponse_QNAME = new QName("http://sso.ws.xjj.com/", "getUserInfoResponse");
    private final static QName _GetOrgTreeByOrgId_QNAME = new QName("http://sso.ws.xjj.com/", "getOrgTreeByOrgId");
    private final static QName _UserLogoutResponse_QNAME = new QName("http://sso.ws.xjj.com/", "userLogoutResponse");
    private final static QName _CheckLoginByCookieResponse_QNAME = new QName("http://sso.ws.xjj.com/", "checkLoginByCookieResponse");
    private final static QName _GetUserOrgByAccount_QNAME = new QName("http://sso.ws.xjj.com/", "getUserOrgByAccount");
    private final static QName _UserLogin_QNAME = new QName("http://sso.ws.xjj.com/", "userLogin");
    private final static QName _CheckLoginByCookie_QNAME = new QName("http://sso.ws.xjj.com/", "checkLoginByCookie");
    private final static QName _GetOrgTreeByOrgIdResponse_QNAME = new QName("http://sso.ws.xjj.com/", "getOrgTreeByOrgIdResponse");
    private final static QName _GetUserOrg_QNAME = new QName("http://sso.ws.xjj.com/", "getUserOrg");
    private final static QName _CheckLoginByTokenResponse_QNAME = new QName("http://sso.ws.xjj.com/", "checkLoginByTokenResponse");
    private final static QName _GetUserInfo_QNAME = new QName("http://sso.ws.xjj.com/", "getUserInfo");
    private final static QName _GetUserOrgByAccountResponse_QNAME = new QName("http://sso.ws.xjj.com/", "getUserOrgByAccountResponse");
    private final static QName _UserLoginResponse_QNAME = new QName("http://sso.ws.xjj.com/", "userLoginResponse");
    private final static QName _UserLogout_QNAME = new QName("http://sso.ws.xjj.com/", "userLogout");
    private final static QName _GetUserInfoByAccountResponse_QNAME = new QName("http://sso.ws.xjj.com/", "getUserInfoByAccountResponse");
    private final static QName _GetUserOrgResponse_QNAME = new QName("http://sso.ws.xjj.com/", "getUserOrgResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.xjj.ws.sso
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link CheckLoginByToken }
     * 
     */
    public CheckLoginByToken createCheckLoginByToken() {
        return new CheckLoginByToken();
    }

    /**
     * Create an instance of {@link UserLogin }
     * 
     */
    public UserLogin createUserLogin() {
        return new UserLogin();
    }

    /**
     * Create an instance of {@link CheckLoginByCookie }
     * 
     */
    public CheckLoginByCookie createCheckLoginByCookie() {
        return new CheckLoginByCookie();
    }

    /**
     * Create an instance of {@link GetUserInfoByAccountResponse }
     * 
     */
    public GetUserInfoByAccountResponse createGetUserInfoByAccountResponse() {
        return new GetUserInfoByAccountResponse();
    }

    /**
     * Create an instance of {@link GetUserOrgByAccountResponse }
     * 
     */
    public GetUserOrgByAccountResponse createGetUserOrgByAccountResponse() {
        return new GetUserOrgByAccountResponse();
    }

    /**
     * Create an instance of {@link UserLogout }
     * 
     */
    public UserLogout createUserLogout() {
        return new UserLogout();
    }

    /**
     * Create an instance of {@link GetUserInfo }
     * 
     */
    public GetUserInfo createGetUserInfo() {
        return new GetUserInfo();
    }

    /**
     * Create an instance of {@link GetOrgTreeByOrgId }
     * 
     */
    public GetOrgTreeByOrgId createGetOrgTreeByOrgId() {
        return new GetOrgTreeByOrgId();
    }

    /**
     * Create an instance of {@link GetUserOrgResponse }
     * 
     */
    public GetUserOrgResponse createGetUserOrgResponse() {
        return new GetUserOrgResponse();
    }

    /**
     * Create an instance of {@link UserLogoutResponse }
     * 
     */
    public UserLogoutResponse createUserLogoutResponse() {
        return new UserLogoutResponse();
    }

    /**
     * Create an instance of {@link GetUserInfoResponse }
     * 
     */
    public GetUserInfoResponse createGetUserInfoResponse() {
        return new GetUserInfoResponse();
    }

    /**
     * Create an instance of {@link CheckLoginByTokenResponse }
     * 
     */
    public CheckLoginByTokenResponse createCheckLoginByTokenResponse() {
        return new CheckLoginByTokenResponse();
    }

    /**
     * Create an instance of {@link GetOrgTreeByOrgIdResponse }
     * 
     */
    public GetOrgTreeByOrgIdResponse createGetOrgTreeByOrgIdResponse() {
        return new GetOrgTreeByOrgIdResponse();
    }

    /**
     * Create an instance of {@link CheckLoginByCookieResponse }
     * 
     */
    public CheckLoginByCookieResponse createCheckLoginByCookieResponse() {
        return new CheckLoginByCookieResponse();
    }

    /**
     * Create an instance of {@link GetUserOrgByAccount }
     * 
     */
    public GetUserOrgByAccount createGetUserOrgByAccount() {
        return new GetUserOrgByAccount();
    }

    /**
     * Create an instance of {@link GetUserInfoByAccount }
     * 
     */
    public GetUserInfoByAccount createGetUserInfoByAccount() {
        return new GetUserInfoByAccount();
    }

    /**
     * Create an instance of {@link UserLoginResponse }
     * 
     */
    public UserLoginResponse createUserLoginResponse() {
        return new UserLoginResponse();
    }

    /**
     * Create an instance of {@link GetUserOrg }
     * 
     */
    public GetUserOrg createGetUserOrg() {
        return new GetUserOrg();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginByToken }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "checkLoginByToken")
    public JAXBElement<CheckLoginByToken> createCheckLoginByToken(CheckLoginByToken value) {
        return new JAXBElement<CheckLoginByToken>(_CheckLoginByToken_QNAME, CheckLoginByToken.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfoByAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserInfoByAccount")
    public JAXBElement<GetUserInfoByAccount> createGetUserInfoByAccount(GetUserInfoByAccount value) {
        return new JAXBElement<GetUserInfoByAccount>(_GetUserInfoByAccount_QNAME, GetUserInfoByAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfoResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserInfoResponse")
    public JAXBElement<GetUserInfoResponse> createGetUserInfoResponse(GetUserInfoResponse value) {
        return new JAXBElement<GetUserInfoResponse>(_GetUserInfoResponse_QNAME, GetUserInfoResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTreeByOrgId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getOrgTreeByOrgId")
    public JAXBElement<GetOrgTreeByOrgId> createGetOrgTreeByOrgId(GetOrgTreeByOrgId value) {
        return new JAXBElement<GetOrgTreeByOrgId>(_GetOrgTreeByOrgId_QNAME, GetOrgTreeByOrgId.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserLogoutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "userLogoutResponse")
    public JAXBElement<UserLogoutResponse> createUserLogoutResponse(UserLogoutResponse value) {
        return new JAXBElement<UserLogoutResponse>(_UserLogoutResponse_QNAME, UserLogoutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginByCookieResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "checkLoginByCookieResponse")
    public JAXBElement<CheckLoginByCookieResponse> createCheckLoginByCookieResponse(CheckLoginByCookieResponse value) {
        return new JAXBElement<CheckLoginByCookieResponse>(_CheckLoginByCookieResponse_QNAME, CheckLoginByCookieResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOrgByAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserOrgByAccount")
    public JAXBElement<GetUserOrgByAccount> createGetUserOrgByAccount(GetUserOrgByAccount value) {
        return new JAXBElement<GetUserOrgByAccount>(_GetUserOrgByAccount_QNAME, GetUserOrgByAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserLogin }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "userLogin")
    public JAXBElement<UserLogin> createUserLogin(UserLogin value) {
        return new JAXBElement<UserLogin>(_UserLogin_QNAME, UserLogin.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginByCookie }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "checkLoginByCookie")
    public JAXBElement<CheckLoginByCookie> createCheckLoginByCookie(CheckLoginByCookie value) {
        return new JAXBElement<CheckLoginByCookie>(_CheckLoginByCookie_QNAME, CheckLoginByCookie.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetOrgTreeByOrgIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getOrgTreeByOrgIdResponse")
    public JAXBElement<GetOrgTreeByOrgIdResponse> createGetOrgTreeByOrgIdResponse(GetOrgTreeByOrgIdResponse value) {
        return new JAXBElement<GetOrgTreeByOrgIdResponse>(_GetOrgTreeByOrgIdResponse_QNAME, GetOrgTreeByOrgIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOrg }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserOrg")
    public JAXBElement<GetUserOrg> createGetUserOrg(GetUserOrg value) {
        return new JAXBElement<GetUserOrg>(_GetUserOrg_QNAME, GetUserOrg.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CheckLoginByTokenResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "checkLoginByTokenResponse")
    public JAXBElement<CheckLoginByTokenResponse> createCheckLoginByTokenResponse(CheckLoginByTokenResponse value) {
        return new JAXBElement<CheckLoginByTokenResponse>(_CheckLoginByTokenResponse_QNAME, CheckLoginByTokenResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfo }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserInfo")
    public JAXBElement<GetUserInfo> createGetUserInfo(GetUserInfo value) {
        return new JAXBElement<GetUserInfo>(_GetUserInfo_QNAME, GetUserInfo.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOrgByAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserOrgByAccountResponse")
    public JAXBElement<GetUserOrgByAccountResponse> createGetUserOrgByAccountResponse(GetUserOrgByAccountResponse value) {
        return new JAXBElement<GetUserOrgByAccountResponse>(_GetUserOrgByAccountResponse_QNAME, GetUserOrgByAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserLoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "userLoginResponse")
    public JAXBElement<UserLoginResponse> createUserLoginResponse(UserLoginResponse value) {
        return new JAXBElement<UserLoginResponse>(_UserLoginResponse_QNAME, UserLoginResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UserLogout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "userLogout")
    public JAXBElement<UserLogout> createUserLogout(UserLogout value) {
        return new JAXBElement<UserLogout>(_UserLogout_QNAME, UserLogout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserInfoByAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserInfoByAccountResponse")
    public JAXBElement<GetUserInfoByAccountResponse> createGetUserInfoByAccountResponse(GetUserInfoByAccountResponse value) {
        return new JAXBElement<GetUserInfoByAccountResponse>(_GetUserInfoByAccountResponse_QNAME, GetUserInfoByAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetUserOrgResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://sso.ws.xjj.com/", name = "getUserOrgResponse")
    public JAXBElement<GetUserOrgResponse> createGetUserOrgResponse(GetUserOrgResponse value) {
        return new JAXBElement<GetUserOrgResponse>(_GetUserOrgResponse_QNAME, GetUserOrgResponse.class, null, value);
    }

}
