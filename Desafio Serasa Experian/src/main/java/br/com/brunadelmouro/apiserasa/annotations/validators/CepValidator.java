package br.com.brunadelmouro.apiserasa.annotations.validators;

import br.com.brunadelmouro.apiserasa.annotations.NoHyphen;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class CepValidator implements ConstraintValidator<NoHyphen, String> {

    @Override
    public void initialize(NoHyphen constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return Objects.isNull(value) || !value.contains("-");
    }
}
