package rockwithme.app.constraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValidator implements ConstraintValidator<EnumValue, String> {
    private List<String> acceptedValues;
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return acceptedValues.contains(s);
    }

    @Override
    public void initialize(EnumValue constraintAnnotation) {
        acceptedValues = Stream.of(constraintAnnotation.enumClass().getEnumConstants())
                .map(Enum::name)
                .collect(Collectors.toList());
    }
}
