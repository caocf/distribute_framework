package com.appleframework.oss.boss.filter;

import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.filter.CasFilter;
import org.jasig.cas.client.util.AbstractConfigurationFilter;
import org.jasig.cas.client.util.RequestURIFilter;
import org.jasig.cas.client.util.ServletUtil;
import org.jasig.cas.client.validation.Assertion;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.core.utils.SpringUtility;
import com.appleframework.oss.boss.entity.RtsRights;
import com.appleframework.oss.boss.entity.User;
import com.appleframework.oss.boss.service.AuthorizeService;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BossFilter extends AbstractConfigurationFilter {
		
	public static String SESSION_RTS_KEY  = "CAS_USER_RIGHTS";
	public static String SESSION_CAS_KEY  = "_const_cas_assertion_";
	
	/**
     * 过滤地址集合
     */
	protected RequestURIFilter excludeFilter;
	
    /**
     * 过滤地址
     */
	private String exclusions = null;

	public void destroy() {
	}
	
	public boolean isExcluded(String path) {
        // 如果指定了excludes，并且当前requestURI匹配任何一个exclude pattern，
        // 则立即放弃控制，将控制还给servlet engine。
        // 但对于internal path，不应该被排除掉，否则internal页面会无法正常显示。
        if (excludeFilter != null && excludeFilter.matches(path)) {
        	return true;
        }
        return false;
    }
	
	/**
	 * 过滤逻辑：首先判断单点登录的账户是否已经存在本系统中，
	 * 如果不存在使用用户查询接口查询出用户对象并设置在Session中
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
		
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		
		String path = ServletUtil.getResourcePath(httpRequest);
        if (isExcluded(path)) {
            filterChain.doFilter(request, response);
            return;
        }
		
		// _const_cas_assertion_是CAS中存放登录用户名的session标志
		Object object = httpRequest.getSession().getAttribute(SESSION_CAS_KEY);
		
		if (object != null) {
			User user = (User)httpRequest.getSession().getAttribute(CasFilter.SESSION_USER_KEY);
			if(null == user) {
				Assertion assertion = (Assertion) object;
				String username = assertion.getPrincipal().getName();
				AuthorizeService authorizeService = (AuthorizeService)SpringUtility.getBean("authorizeService");
				
				/*UserService userService = (UserService)SpringUtility.getBean("userService");
				user = userService.getByUsername(username);*/
				AttributePrincipal principal = (AttributePrincipal) httpRequest.getUserPrincipal(); 
				Map<String, Object> attributes = principal.getAttributes();
				
				user = new User();
				user.setUsername(username);
				user.setId(Integer.parseInt(attributes.get("id").toString()));
				user.setIsadmin(Integer.parseInt(attributes.get("isadmin").toString()));
				if(null != attributes.get("realname")) {
					user.setRealname(attributes.get("realname").toString());
				}
				if(null != attributes.get("roles")) {
					user.setRoles(attributes.get("roles").toString());
				}
				if(null != attributes.get("mobile")) {
					user.setMobile(attributes.get("mobile").toString());
				}
				if(null != attributes.get("email")) {
					user.setEmail(attributes.get("email").toString());
				}

				httpRequest.getSession().setAttribute(CasFilter.SESSION_USER_KEY, user);
				
				Set<RtsRights> rset = authorizeService.getAuthResource(user.getId());
				Set<String> rsIdSet = new HashSet<String>();
				for (RtsRights rtsRights : rset) {
					rsIdSet.add(rtsRights.getId().toString());
				}
				httpRequest.getSession().setAttribute(SESSION_RTS_KEY, rsIdSet);
				
			}
		}
		filterChain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		//获取需要过滤拦截地址
        setExclusions(getPropertyFromInitParams(filterConfig, "exclusions", null));
        String filterExclusions = exclusions  + "," + PropertyConfigurer.getValue("filter.exclusions");
		excludeFilter = new RequestURIFilter(filterExclusions);
	}
	
	public void setExclusions(String exclusions) {
		this.exclusions = exclusions;
	}
		
}

