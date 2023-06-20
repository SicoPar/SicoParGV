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

@Property(editing = Editing.ENABLED, maxLength = Email.MAX_LEN, optionality = Optionality.OPTIONAL)
@PropertyLayout(named = "E-mail")
@Parameter(maxLength = Email.MAX_LEN, optionality = Optionality.OPTIONAL)
@ParameterLayout(named = "E-mail")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

	int MAX_LEN = 50;
}
