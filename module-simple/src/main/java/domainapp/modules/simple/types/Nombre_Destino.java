package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.ParameterLayout;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.annotation.PropertyLayout;


@Property(maxLength = Nombre_Destino.MAX_LEN, optionality = Optionality.MANDATORY)
@Parameter(maxLength = Nombre_Destino.MAX_LEN, optionality = Optionality.MANDATORY)
@ParameterLayout(named = "Nombre")
@PropertyLayout(named = "Nombre")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Nombre_Destino {

    int MAX_LEN = 60;
}