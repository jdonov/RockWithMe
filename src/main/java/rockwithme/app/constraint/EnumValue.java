package rockwithme.app.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE, TYPE_USE })
@Retention(RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
@Documented
@Repeatable(EnumValue.List.class)
public @interface EnumValue {
    Class<? extends Enum<?>> enumClass();
    String message() default "You have entered invalid enum value!";
    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

//    String value();

    @Target({ FIELD, METHOD, PARAMETER, ANNOTATION_TYPE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        EnumValue[] value();
    }
}
