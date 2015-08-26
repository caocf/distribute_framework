package com.appleframework.oss.boss.web;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.exception.ServiceException;
import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.service.DepartmentService;
import com.appleframework.web.bean.Message;
 
@Controller
public class DepartmentController extends BaseController {
	
	@Resource
	private DepartmentService departmentService;

	@RequestMapping(value = "/department/add", method = RequestMethod.GET)
	public String add(Model model, Integer pid, HttpServletResponse response) throws Exception {
		if(null != pid && pid != 0) {
			Department department = departmentService.get(pid);
			model.addAttribute("PARENT_DEPARTMENT", department);
			if(department.getGrade() >= 2) {
				addErrorMessage(model, "最高只能添加3级部门");
				return ERROR_VIEW;
			}
		}
		else {
			pid = 0;
		}
		model.addAttribute("parentId", pid);
		return "department/add";
	}
	
	@RequestMapping(value = "/department/edit", method = RequestMethod.GET)
	public String edit(Model model, Integer id, HttpServletResponse response) throws Exception {
		Department department = departmentService.get(id);
		Integer pid = department.getParentId();
		if(null != pid && pid != 0) {
			Department parentDepartment = departmentService.get(pid);
			model.addAttribute("PARENT_DEPARTMENT", parentDepartment);
		}
		else {
			pid = 0;
		}
		model.addAttribute("parentId", pid);
		model.addAttribute("DEPARTMENT", department);
		return "department/edit";
	}
	
	@RequestMapping(value = "/department/save")
	public String save(Model model, Department department, HttpServletResponse response) {
		try {
			if (department.getParentId() != 0) {
				Department parent = departmentService.get(department.getParentId());
				department.setGrade(parent.getGrade() + 1);
			} else {
				department.setGrade(0);
			}
			departmentService.add(department);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "添加部门成功", "list");
		return SUCCESS_VIEW;

	}
	
	@RequestMapping(value = "/department/update")
	public String update(Model model, Department department, HttpServletResponse response) {
		try {			
			if (department.getParentId() != 0) {
				Department parent = departmentService.get(department.getParentId());
				department.setGrade(parent.getGrade() + 1);
			} else {
				department.setGrade(0);
			}
			departmentService.update(department);
		} catch (ServiceException e) {
			addErrorMessage(model, e.getMessage());
			return ERROR_VIEW;
		}
		addSuccessMessage(model, "修改部门成功", "list");
		return SUCCESS_VIEW;
	}
	
	@RequestMapping(value = "/department/list")
	public String list(Model model, HttpServletResponse response) throws Exception {
		List<Department> list = departmentService.findAll();
		model.addAttribute("DEPARTMENT_LIST", list);
		return "department/list";
	}
	
	/*@RequestMapping(value = "/department/delete", method = RequestMethod.GET)
	public String delete(Model model, Integer id, HttpServletResponse response) {
		try {
			Department department = departmentService.get(id);
			departmentService.delete(department);
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		addSuccessMessage(model, "修改部门成功", "list");
		return SUCCESS_VIEW;
	}*/
	
	@RequestMapping(value = "/department/delete", method = RequestMethod.POST)
	public @ResponseBody
	Message delete(Integer id) {
		Department department;
		try {
			department = departmentService.get(id);
			departmentService.delete(department);
			return SUCCESS_MESSAGE;
		} catch (ServiceException e) {
			return Message.error(e.getMessage());
		}
	}
	
	@RequestMapping(value = "/department/json")
	public @ResponseBody List<Department> jsonList(Integer id) {
		List<Department> departmentList;
		try {
			return departmentService.findAll();
		} catch (ServiceException e) {
			departmentList = new ArrayList<Department>();
		}
		return departmentList;
	}
	
	@RequestMapping(value = "/department/select")
	public String selectList(Model model, HttpServletResponse response) throws Exception {
		List<Department> list = departmentService.findAll();
		model.addAttribute("DEPARTMENT_LIST", list);
		return "department/list_select";
	}

}
