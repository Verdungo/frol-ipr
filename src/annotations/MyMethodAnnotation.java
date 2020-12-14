package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ����� ���������� ����� ������������ ������ �����
 * IDE ����� ��������, ���� ����������� ������������ ���-�� ������
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.SOURCE)      // - �� ����������� ������������
//@Retention(RetentionPolicy.CLASS)     // - ����������� � ���������������� class (default)
//@Retention(RetentionPolicy.RUNTIME)   // - �������� JVM ��� ����������
public @interface MyMethodAnnotation {
}
