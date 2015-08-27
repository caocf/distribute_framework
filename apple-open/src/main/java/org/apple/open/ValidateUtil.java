package org.durcframework.open;

import java.util.Collections;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 验证工具类 JSR303
 * 
 * @author hc.tang 2014年6月5日
 * 
 */
public class ValidateUtil {

	private static Validator validator;
	private static final ValidateHolder SUCCESS_HOLDER = new ValidateHolder();

	static {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		validator = factory.getValidator();

		SUCCESS_HOLDER.setSuccess(true);
		SUCCESS_HOLDER.setConstraintViolations(Collections
				.<ConstraintViolation<Object>> emptySet());
	}

	/**
	 * 验证
	 * @param obj 属性使用JSR303注解
	 * @return 
	 */
	public static ValidateHolder validate(Object obj) {

		ValidateHolder holder = new ValidateHolder();

		Set<ConstraintViolation<Object>> set = validator.validate(obj);

		holder.setConstraintViolations(set);
		holder.setSuccess(set.size() == 0);

		return holder;

	}

}
