package org.durcframework.open;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

/**
 * 验证结果
 * @author hc.tang
 * 2014年6月19日
 * @param <T>
 *
 */
public class ValidateHolder {
	private boolean isSuccess;
	private Set<ConstraintViolation<Object>> constraintViolations;

	public boolean isSuccess() {
		return isSuccess;
	}

	public void setSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Set<ConstraintViolation<Object>> getConstraintViolations() {
		return constraintViolations;
	}

	public void setConstraintViolations(
			Set<ConstraintViolation<Object>> constraintViolations) {
		this.constraintViolations = constraintViolations;
	}
	
	// 返回格式类似于:["用户名错误","密码不正确"]
	public List<String> buildValidateErrors(){
		Set<ConstraintViolation<Object>> set = this.getConstraintViolations();
		List<String> errors = new ArrayList<String>();
		
		for (ConstraintViolation<Object> c : set) {
			errors.add(c.getMessage());
		}
		
		return errors;
	}

}
