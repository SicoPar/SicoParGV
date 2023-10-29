package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;
import org.apache.isis.applib.spec.AbstractSpecification;

@Property(

        maxLength = Modelo.MAX_LEN,
        optionality = Optionality.OPTIONAL
)
@PropertyLayout(named = "Modelo")   
@Parameter(maxLength = Modelo.MAX_LEN, optionality = Optionality.OPTIONAL)
@ParameterLayout(named = "Modelo")  
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Modelo {

    int MAX_LEN = 100;
}