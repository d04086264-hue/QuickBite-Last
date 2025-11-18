package com.cts.annotation;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;


@Target({ElementType.FIELD, ElementType.PARAMETER}) 
@Retention(RetentionPolicy.RUNTIME) 
@Constraint(validatedBy = CustomValidation.class) 
@Documented
public @interface CustomValidationAnnotation {
	
    
    String message() default "Invalid";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
	
}

