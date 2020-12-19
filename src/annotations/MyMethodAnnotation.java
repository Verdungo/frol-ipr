package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Такой аннотацией можно аннотировать только метод
 * IDE будет истерить, если попробовать аннотировать что-то другое
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)      // - не учитывается компилятором
//@Retention(RetentionPolicy.CLASS)     // - сохраняется в скомпилированном class (default)
//@Retention(RetentionPolicy.RUNTIME)   // - доступна JVM при исполнении
public @interface MyMethodAnnotation {
}
