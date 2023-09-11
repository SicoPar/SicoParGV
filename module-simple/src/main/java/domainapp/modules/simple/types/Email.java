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

@Property(editing = Editing.ENABLED, maxLength = Email.MAX_LEN, optionality = Optionality.OPTIONAL, regexPattern = "[^@]+@[^@]+[.][^@]+", regexPatternReplacement = "Formato de direccion de correo Incorrecta", mustSatisfy = Email.Spec.class)
@PropertyLayout(named = "E-mail")
@Parameter(maxLength = Email.MAX_LEN, optionality = Optionality.OPTIONAL,mustSatisfy = Email.Spec.class)
@ParameterLayout(named = "E-mail")
@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface Email {

	int MAX_LEN = 50;

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
