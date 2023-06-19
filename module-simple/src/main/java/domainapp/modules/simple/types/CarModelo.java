package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;

@Property(maxLength = CarModelo.MAX_LEN, optionality = Optionality.MANDATORY)
@Parameter(maxLength = CarModelo.MAX_LEN, optionality = Optionality.MANDATORY)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CarModelo {

    int MAX_LEN = 60;
}