package com.appleframework.oss.boss.web;

import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;


import com.appleframework.oss.boss.entity.User;
import com.appleframework.oss.boss.util.DateEditor;
import com.appleframework.web.bean.Message;
import com.appleframework.web.bean.Message.Type;
import com.appleframework.web.freemarker.directive.FlashMessageDirective;

import org.jasig.cas.client.filter.CasFilter;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

public class BaseController {
	
	protected static final String ERROR_VIEW = "/commons/error";
	
	protected static final String SUCCESS_VIEW = "/commons/success";
	
	protected static final Message ERROR_MESSAGE = Message.error("操作错误");

	protected static final Message SUCCESS_MESSAGE = Message.success("操作成功");

	private static final String CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME = "constraintViolations";

	@Resource(name = "validator")
	private Validator validator;

	@InitBinder
	protected void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
		binder.registerCustomEditor(Date.class, new DateEditor(true));
	}

	protected boolean isValid(Object target, Class<?>... groups) {
		Set<ConstraintViolation<Object>> constraintViolations = validator.validate(target, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	protected boolean isValid(Class<?> type, String property, Object value, Class<?>... groups) {
		Set<?> constraintViolations = validator.validateValue(type, property, value, groups);
		if (constraintViolations.isEmpty()) {
			return true;
		} else {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(CONSTRAINT_VIOLATIONS_ATTRIBUTE_NAME, constraintViolations, RequestAttributes.SCOPE_REQUEST);
			return false;
		}
	}

	protected void addFlashMessage(RedirectAttributes redirectAttributes, Message message) {
		if (redirectAttributes != null && message != null) {
			redirectAttributes.addFlashAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addSuccessMessage(Model model, String content, String url) {
		if ( model != null) {
			Message message = new Message(Type.success, content, url);
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addSuccessMessage(Model model, String url) {
		if ( model != null) {
			Message message = new Message(Type.success, url);
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected void addErrorMessage(Model model, String content) {
		if ( model != null) {
			Message message = new Message(Type.error, content, "");
			model.addAttribute(FlashMessageDirective.FLASH_MESSAGE_ATTRIBUTE_NAME, message);
		}
	}
	
	protected String ajax(String content) {
		return content.trim();
	}

	/*protected void addLog(String content) {
		if (content != null) {
			RequestAttributes requestAttributes = RequestContextHolder.currentRequestAttributes();
			requestAttributes.setAttribute(Log.LOG_CONTENT_ATTRIBUTE_NAME, content, RequestAttributes.SCOPE_REQUEST);
		}
	}*/
	
	protected User getOperator(HttpServletRequest request){
		return (User)request.getSession().getAttribute(CasFilter.SESSION_USER_KEY);
	}

}
