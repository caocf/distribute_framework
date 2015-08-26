package com.appleframework.oss.boss.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.service.RtsMenuService;

@Controller
public class MenuController {
	
	@Resource
	private RtsMenuService menuService;
	
	@RequestMapping(value = "/menu_main", method = RequestMethod.GET)
	public String main(Model model, Integer id, HttpServletResponse response) throws Exception {
		model.addAttribute("MENU_ID", id);
		return "menu_main";
	}
	
	@RequestMapping(value = "/menu_container", method = RequestMethod.GET)
	public String container(Model model, Integer id, HttpServletResponse response) throws Exception {
		model.addAttribute("MENU_ID", id);
		return "menu_container";
	}
	

	@RequestMapping(value = "/menu_header", method = RequestMethod.GET)
	public String index(Model model, Integer id, HttpServletResponse response) throws Exception {
		List<RtsMenu> list = menuService.findRootMenuList();
		List<RtsMenu> secondList = menuService.listMenus(id);
		model.addAttribute("ROOT_MENU_LIST", list);
		model.addAttribute("SECOND_MENU_LIST", secondList);
		return "menu_header";
	}
	
	@RequestMapping(value = "/menu_left", method = RequestMethod.GET)
	public String left(Model model, Integer id, HttpServletResponse response) throws Exception {
		if(null != id && id > 0) {
			List<RtsMenu> leftMenuList = null;
			leftMenuList = menuService.listMenus(id);
			
			Map<String, List<RtsMenu>> childMenuMap = new HashMap<String, List<RtsMenu>>();
			
			//all menu
			List <RtsMenu> allMenuList  = menuService.listMenus();
	        //一级菜單

			for (RtsMenu leftMenu : leftMenuList) {
				List<RtsMenu> childMenuList = menuService.findChildList(leftMenu, allMenuList);
				childMenuMap.put(leftMenu.getId() + "", childMenuList);
			}

			model.addAttribute("LEFT_MENU_LIST", leftMenuList);
			model.addAttribute("CHILD_MAP_MENU_LIST", childMenuMap);
		}
		else {
			//leftMenuList = new ArrayList<RtsMenu>();
			return null;
		}
		return "menu_left";
	}
	
	@RequestMapping(value = "/menu_footer", method = RequestMethod.GET)
	public String footer(Model model, HttpServletResponse response) throws Exception {
		return "menu_footer";
	}
	
	@RequestMapping(value = "/menu_right", method = RequestMethod.GET)
	public String right(Model model, Integer id, HttpServletResponse response) throws Exception {
		if(null != id && id > 0) {
			List<RtsMenu> rightMenuList = menuService.listMenus(id);
			model.addAttribute("RIGHT_MENU_LIST", rightMenuList);
			RtsMenu menu = menuService.get(id);
			List<RtsMenu> pathMenuList = menuService.listMenus(menu.getPath());
			model.addAttribute("PATH_MENU_LIST", pathMenuList);
		}
		else {
			return null;
		}
		return "menu_right";
	}

	@RequestMapping(value = "/menu_content", method = RequestMethod.GET)
	public String content(Model model, HttpServletResponse response) throws Exception {
		return null;
	}
}
