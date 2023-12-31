package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.spec.AbstractSpecification;

@Property(maxLength = Patente.MAX_LEN, optionality = Optionality.MANDATORY,mustSatisfy = Patente.Spec.class)
@Parameter(maxLength = Patente.MAX_LEN, optionality = Optionality.MANDATORY,mustSatisfy = Patente.Spec.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Patente {

    int MAX_LEN = 60;
    
	class Spec extends AbstractSpecification<String> {
		@Override
		public String satisfiesSafely(String candidate) {
			for (char prohibitedCharacter : "&%$!".toCharArray()) {
				if (candidate.contains("" + prohibitedCharacter)) {
					return "Caracter '" + prohibitedCharacter + "' no esta permitido.";
				}
			}
			return null;
		}
	}
}