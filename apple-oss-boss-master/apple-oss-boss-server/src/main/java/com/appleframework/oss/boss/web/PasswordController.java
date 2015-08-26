package com.appleframework.oss.boss.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.jasig.cas.client.filter.CasFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.appleframework.web.bean.Message;
import com.appleframework.oss.boss.entity.User;
import com.appleframework.oss.boss.service.UserService;
import com.appleframework.oss.boss.vo.PasswordBO;

@Controller
public class PasswordController extends BaseController {
		
	@Resource
	private UserService userService;
		
	@RequestMapping(value = "/password/modify", method = RequestMethod.GET)
	public String modify(Model model, HttpServletRequest request) throws Exception {		
		return "password/modify";
	}
	

	@RequestMapping(value = "/password/update", method = RequestMethod.POST)
	public @ResponseBody Message update(Model model, PasswordBO bo, HttpServletRequest request) throws Exception {
		User user = (User)request.getSession().getAttribute(CasFilter.SESSION_USER_KEY);
		String oldPassword = bo.getOldPassword();
		String newPassword = bo.getNewPassword();
		try {
			userService.updatePassword(user.getId(), oldPassword, newPassword);
		} catch (Exception e) {
			return Message.error(e.getMessage());
		}
		return SUCCESS_MESSAGE;
	}
}
