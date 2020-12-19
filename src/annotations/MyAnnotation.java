package annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Такую аннотацию можно применять на все подряд - классы, методы, поля, агрументы */
// default @Retention = CLASS
// default @Target = ANY
@Retention(RetentionPolicy.RUNTIME)
public @interface MyAnnotation {
    String name();
    String author() default "[none]";
    int id();
}
