package br.com.brunadelmouro.apiserasa.annotations;

import br.com.brunadelmouro.apiserasa.annotations.validators.CepValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CepValidator.class)
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoHyphen {

    String message() default "O CEP não deve conter hífen";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
