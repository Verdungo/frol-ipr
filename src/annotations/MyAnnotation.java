package annotations;

// default @Retention = CLASS
// default @Target = ANY
/** ����� ��������� ����� ��������� �� ��� ������ - ������, ������, ����, ��������� */
public @interface MyAnnotation {
    String name();
    String author() default "[none]";
    int id();
}
