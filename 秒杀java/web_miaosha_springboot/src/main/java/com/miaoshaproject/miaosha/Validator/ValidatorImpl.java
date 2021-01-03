
package com.miaoshaproject.miaosha.Validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;


@Component
public class ValidatorImpl  implements InitializingBean {

    private Validator validator;

    public ValidationResult validate(Object bean){
        ValidationResult validationResult=new ValidationResult();

        Set<ConstraintViolation<Object>> constraintValidators=validator.validate(bean);

        if (constraintValidators.size()>0){
            validationResult.setHasErrors(true);
            constraintValidators.forEach(it->{
                String errMsg=it.getMessage();
                String propertyName=it.getPropertyPath().toString();
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }
    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator= Validation.buildDefaultValidatorFactory().getValidator();
    }
}
