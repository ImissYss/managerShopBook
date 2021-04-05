package com.maidat.mybooks.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailExistsValidator.class)
@Documented
public @interface EmailExists {

    String message() default "Email is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
