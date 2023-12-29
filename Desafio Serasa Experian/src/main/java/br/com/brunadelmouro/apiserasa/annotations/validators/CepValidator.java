package br.com.brunadelmouro.apiserasa.annotations.validators;

import br.com.brunadelmouro.apiserasa.annotations.NoHyphen;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CepValidator implements ConstraintValidator<NoHyphen, String> {

    @Override
    public void initialize(NoHyphen constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !value.contains("-");
    }
}
