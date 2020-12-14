package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация метода, связывающая элемент с его слушателем
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionListenerFor {
    /** Элемент, для которого задается обработчик */
    String value();     // имя value позволяет задавать значения без указания имени атрибута
}
