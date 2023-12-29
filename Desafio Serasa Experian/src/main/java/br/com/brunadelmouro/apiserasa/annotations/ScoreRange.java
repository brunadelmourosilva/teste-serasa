package br.com.brunadelmouro.apiserasa.annotations;

import br.com.brunadelmouro.apiserasa.annotations.validators.ScoreValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ScoreValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ScoreRange {

  String message() default "O valor do score deve ser entre 0 e 1000";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
