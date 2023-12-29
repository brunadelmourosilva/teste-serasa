package br.com.brunadelmouro.apiserasa.annotations.validators;

import br.com.brunadelmouro.apiserasa.annotations.ScoreRange;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ScoreValidator implements ConstraintValidator<ScoreRange, Integer> {

  @Override
  public void initialize(ScoreRange constraintAnnotation) {}

  @Override
  public boolean isValid(Integer value, ConstraintValidatorContext context) {
    return value >= 0 && value <= 1000;
  }
}
