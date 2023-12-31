package domainapp.modules.simple.types;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.apache.isis.applib.annotation.Editing;
import org.apache.isis.applib.annotation.Optionality;
import org.apache.isis.applib.annotation.Parameter;
import org.apache.isis.applib.annotation.Property;
import org.apache.isis.applib.spec.AbstractSpecification;

@Property( maxLength = Telefono.MAX_LEN, optionality = Optionality.OPTIONAL, regexPattern = "[+]?[0-9 ]+", regexPatternReplacement = "Solo numeros y espacios ,opcionalmente puedes usar como prefijo el  '+'.  "
		+ "Por ejemplo, '+54 2995332211', or '0299 5332211'",mustSatisfy = Telefono.Spec.class)
@Parameter(maxLength = Telefono.MAX_LEN, optionality = Optionality.OPTIONAL,mustSatisfy = Telefono.Spec.class)
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Telefono {

	int MAX_LEN = 30;
	
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
