package com.appleframework.oss.boss.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.exception.ServiceException;
import com.appleframework.web.bean.Message;
import com.appleframework.model.Search;
import com.appleframework.model.page.Pagination;
import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.entity.RtsMenu;
import com.appleframework.oss.boss.entity.RtsRole;
import com.appleframework.oss.boss.entity.RtsUserRole;
import com.appleframework.oss.boss.service.DepartmentService;
import com.appleframework.oss.boss.service.RtsMenuService;
import com.appleframework.oss.boss.service.RtsRoleService;
import com.appleframework.oss.boss.service.RtsUserRoleService;
import com.appleframework.oss.boss.service.UserService;

@Controller
public class RoleMngController extends BaseController {
	
	@Resource
	private RtsRoleService rtsRoleService;
	
	@Resource
	private RtsUserRoleService rtsUserRoleService;
	
	@Resource
	private RtsMenuService menuService;
	
	@Resource
	private UserService userService;
	
	@Resource
	private DepartmentService departmentService;

	
	@RequestMapping(value = "/role/add", method = RequestMethod.GET)
	public String add(Model model, HttpServletResponse response) throws Exception {
		List<RtsMenu> menuList = menuService.findAllMenuRights();
		List<Department> departmentList = departmentService.findAll();
		model.addAttribute("DEPART_LIST", departmentList);
		model.addAttribute("MENU_LIST", menuList);
		return "role/add";
	}
	
	@RequestMapping(value = "/role/save")
	public String save(Model model, RtsRole role, String[] ckd, HttpServletResponse response) {
		try {
			rtsRoleService.add(role, ckd);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加角色成功", "list");
		return SUCCESS_VIEW;
	}

	
	@RequestMapping(value = "/role/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
        RtsRole role = rtsRoleService.get(id);
        model.addAttribute("ROLE", role );
        List<RtsMenu> menuList = menuService.findAllMenuRights();
		List<Department> departmentList = departmentService.findAll();
		List<RtsMenu> haveMenuRights = rtsRoleService.findHaveMenuRights(id);
		model.addAttribute("HV_MENU_LIST", haveMenuRights);
		model.addAttribute("ALL_MENU_LIST", menuList);
		model.addAttribute("DEPART_LIST", departmentList);
		return "role/edit";
	}
	
	@RequestMapping(value = "/role/view", method = RequestMethod.GET)
	public String view(Model model, Integer id, HttpServletResponse response) throws Exception {
        RtsRole role = rtsRoleService.get(id);
        model.addAttribute("ROLE", role );
        List<RtsMenu> menuList = menuService.findAllMenuRights();
		model.addAttribute("ALL_MENU_LIST", menuList);
		List<RtsMenu> haveMenuRights = rtsRoleService.findHaveMenuRights(id);
		model.addAttribute("HV_MENU_LIST", haveMenuRights);
		return "role/view";
	}
	
	
	@RequestMapping(value = "/role/update")
	public String update(Model model, RtsRole role, String[] ckd, HttpServletResponse response) {
		try {
			rtsRoleService.update(role, ckd);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "修改角色成功", "list");
		return SUCCESS_VIEW;
	}
	
	@RequestMapping(value = "/role/list")
	public String list(Model model, Pagination page, Search search, HttpServletResponse response) throws Exception {
		page = rtsRoleService.getList(page, search);
		model.addAttribute("page", page);
		model.addAttribute("search", search);
		return "role/list";
	}
	
	@RequestMapping(value = "/role/userlist")
	public String listUser(Model model, Integer id, HttpServletResponse response) throws Exception {
		List<RtsUserRole> list = rtsUserRoleService.getByRoleId(id);
		model.addAttribute("list", list);
		return "role/list_user";
	}
	
	@RequestMapping(value = "/role/userdelete", method = RequestMethod.POST)
	public @ResponseBody
	Message deleteUser(Integer id) {
		try {
			RtsUserRole userRole = rtsUserRoleService.get(id);
			Integer userId = userRole.getUser().getId();
			rtsUserRoleService.delete(userRole);
			userService.updateRole(userId);
			return SUCCESS_MESSAGE;
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/role/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Integer id) {
		try {
			rtsRoleService.delete(id);
			return SUCCESS_MESSAGE;
		} catch (ServiceException e) {
			return Message.error(e.getMessage());
		}
	}
	
	// AJAX唯一验证
	@RequestMapping(value = "/role/check_rolename", method = RequestMethod.GET)
	public @ResponseBody String checkRoleName(String oldRoleName, String roleName) {
		if (rtsRoleService.isUniqueByName(oldRoleName, roleName)) {
			return ajax("true");
		} else {
			return ajax("false");
		}
	}

}
