package com.appleframework.oss.boss.template.directive;

import java.io.IOException;
import java.util.List;
import java.util.Map;


import org.springframework.stereotype.Component;

import com.appleframework.web.freemarker.directive.BaseDirective;


import freemarker.core.Environment;
import freemarker.template.SimpleNumber;
import freemarker.template.SimpleSequence;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

@Component("authorizeDirective")
public class AuthorizeDirective extends BaseDirective {

	private static final String VARIABLE_NAME = "hasRights";

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {		
		SimpleSequence rightsSequence = (SimpleSequence)params.get("rights");
		SimpleNumber menuNumber = (SimpleNumber)params.get("menuId");
		SimpleNumber adminNumber = (SimpleNumber)params.get("isAdmin");
		List<String> rightsList = rightsSequence.toList();
		String menuId = menuNumber.toString();
		Integer isAdmin = (Integer)adminNumber.getAsNumber();
		Integer hasRights = 0;
		if(isAdmin == 1) {
			hasRights = 1;
		}
		else {
			if(rightsList.contains(menuId)) {
				hasRights = 1;
			}
		}
		setLocalVariable(VARIABLE_NAME, hasRights, env, body);
	}

}