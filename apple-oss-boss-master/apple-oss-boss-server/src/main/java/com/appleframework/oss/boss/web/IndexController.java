package com.appleframework.oss.boss.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jasig.cas.client.filter.CasFilter;
import org.jasig.cas.client.util.CasPropertiesConfig;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appleframework.config.core.PropertyConfigurer;
import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.filter.BossFilter;
import com.appleframework.oss.boss.service.RtsMenuService;

@Controller
public class IndexController {
	
	@Resource
	private RtsMenuService menuService;

	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String index(Model model, HttpServletResponse response) {
		response.setCharacterEncoding("UTF-8");
		return "index";
	}
	
	@RequestMapping(value = "/main", method = RequestMethod.GET)
	public String main(Model model, HttpServletResponse response) throws Exception {
		List<RtsMenu> list = menuService.findRootMenuList();
		model.addAttribute("ROOT_MENU_LIST", list);
		return "main";
	}
	
	@RequestMapping(value = "/panel", method = RequestMethod.GET)
	public String panel(Model model, HttpServletResponse response) throws Exception {
		List<RtsMenu> list = menuService.findRootMenuList();
		model.addAttribute("ROOT_MENU_LIST", list);
		return "panel";
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout(Model model, HttpServletRequest request) throws Exception {
		String casServer = PropertyConfigurer.getValue(CasPropertiesConfig.CAS_SERVER);
		String serverName = PropertyConfigurer.getValue(CasPropertiesConfig.SERVER_NAME);
		String logoutUrl = casServer + "/logout?service=" + serverName + "/boss";
		
		request.getSession().removeAttribute(CasFilter.SESSION_USER_KEY);
		request.getSession().removeAttribute(BossFilter.SESSION_RTS_KEY);
		request.getSession().removeAttribute(BossFilter.SESSION_CAS_KEY);
		model.addAttribute("LOGOUT_URL", logoutUrl);
		return "logout";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome(Model model, HttpServletRequest request) throws Exception {
		return "index";
	}
	
	@RequestMapping(value = "/boss", method = RequestMethod.GET)
	public String boss(Model model, HttpServletRequest request) throws Exception {
		return "index";
	}

}
