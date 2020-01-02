package jun.projavawebapp.site.validation;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotBlankValidator
        implements ConstraintValidator<NotBlank, CharSequence> {

    private final static Logger log = LogManager.getLogger();

    @Override
    public void initialize(NotBlank annotation) {
        log.traceEntry();
        log.traceExit();
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        log.traceEntry("{} {}", value, context);
        if (value instanceof String)
            return log.traceExit(((String) value).trim().length() > 0);
        return log.traceExit(value.toString().trim().length() > 0);
    }
}
