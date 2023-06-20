package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;

@Property(editing = Editing.ENABLED, maxLength = Documento.MAX_LEN)
@Parameter(maxLength = Documento.MAX_LEN, optionality = Optionality.OPTIONAL)
@Target({ ElementType.METHOD ,ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Documento {

	int MAX_LEN = 30;
}
