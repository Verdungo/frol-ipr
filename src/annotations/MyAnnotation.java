package annotations;

// default @Retention = CLASS
// default @Target = ANY
/** Такую аннотацию можно применять на все подряд - классы, методы, поля, агрументы */
public @interface MyAnnotation {
    String name();
    String author() default "[none]";
    int id();
}
