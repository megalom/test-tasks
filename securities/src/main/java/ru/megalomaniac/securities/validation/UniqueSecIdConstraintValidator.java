package ru.megalomaniac.securities.validation;

import org.springframework.beans.factory.annotation.Autowired;
import ru.megalomaniac.securities.model.SecuritiesInfo;
import ru.megalomaniac.securities.service.SecuritiesInfoService;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueSecIdConstraintValidator implements ConstraintValidator<UniqueSecIdConstraint,String> {
    @Autowired
    private SecuritiesInfoService securitiesInfoService;

    @Override
    public void initialize(UniqueSecIdConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(String secid, ConstraintValidatorContext constraintValidatorContext) {
        Boolean result = true;
        if(secid == null) {
            System.out.println("secid is null");
            return false;
        }
        System.out.println("check secid "+secid);
        System.out.println(securitiesInfoService.existsBySecid(secid));
        result = !securitiesInfoService.existsBySecid(secid);
        System.out.println("result = "+result);
        return result;
    }
}
