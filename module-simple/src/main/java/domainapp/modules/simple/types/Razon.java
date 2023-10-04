package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;

@Property(maxLength = Razon.MAX_LEN)
@PropertyLayout(named = "razon")
@Parameter(maxLength = Razon.MAX_LEN)
@ParameterLayout(named = "razon")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Razon {
	int MAX_LEN = 255;
}
