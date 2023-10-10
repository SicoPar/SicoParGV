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

@Property(maxLength = Ciudad.MAX_LEN,mustSatisfy = Ciudad.Spec.class)
@Parameter(maxLength = Ciudad.MAX_LEN,mustSatisfy = Ciudad.Spec.class)
@ParameterLayout(named = "Ciudad")
@PropertyLayout(named = "Ciudad")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Ciudad {

	int MAX_LEN = 40;
	
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