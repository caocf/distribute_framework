package com.appleframework.oss.boss.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;


import org.springframework.stereotype.Component;

import com.appleframework.oss.boss.entity.Department;
import com.appleframework.oss.boss.service.DepartmentService;
import com.appleframework.web.freemarker.directive.BaseDirective;

import freemarker.core.Environment;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("departDirective")
public class DepartDirective extends BaseDirective {

	private static final String VARIABLE_NAME = "pathName";
	
	@Resource
	private DepartmentService departmentService;

	@SuppressWarnings({ "rawtypes" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {		
		SimpleScalar path = (SimpleScalar)params.get("path");
		//SimpleNumber idNumber = (SimpleNumber)params.get("id");
		List<Department> departmentList = departmentService.getDepartmentListByPath(path.toString());
		String departments = "";
		for (int i = 0; i < departmentList.size(); i++) {
			Department department = departmentList.get(i);
			if(i <= departmentList.size() -2) {
				departments += department.getName() + "-";
			}
			else {
				departments += department.getName();
			}
		}
		setLocalVariable(VARIABLE_NAME, departments, env, body);
	}

}