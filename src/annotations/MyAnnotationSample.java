package annotations;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class MyAnnotationSample {
    public static void main(String[] args) {
        Class personClass;
        try {
            personClass = Class.forName("annotations.Person");
        } catch (ClassNotFoundException e) {
            System.out.println("Не найден класс Person.");
            return;
        }

        // переберем поля и их аннотации
        System.out.println("-------------------- ПОЛЯ --------------------");
        Field[] fields = personClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            //get annotations
            System.out.println("-- Поле '" + field.getName() + "' аннотировано следующими аннотациями:");
            Annotation[] annotations = field.getDeclaredAnnotations();
            for (Annotation a : annotations) {
                System.out.print(a.annotationType().getSimpleName() + ". ");
                if (a instanceof MyRuntimeRetentionAnnotation) {
                    System.out.println("value = " + ((MyRuntimeRetentionAnnotation) a).value());
                }
            }
        }

        // переберем методы и проверим, аннотированы ли они @MyAnnotation
        System.out.println("-------------------- МЕТОДЫ --------------------");
        Method[] methods= personClass.getDeclaredMethods();
        for (Method m : methods) {
            String s = "-- Метод " + m.getName();
            MyAnnotation a = m.getAnnotation(MyAnnotation.class);
            if (a != null) {
                s += " аннотирован @MyAnnotation (id=" + a.id() + ", name=" + a.name() + ")";
            } else {
                s += " НЕ аннотирован @MyAnnotation";
            }
            System.out.println(s);
        }
    }
}

//@MyMethodAnnotation - не применимо, т.к. имеет таргет ElementType.METHOD
@MyAnnotation(name = "name", id = 1) // автор тоже неявно присутствует как default-значение
class Person {
    /*
    Аннотации первых двух полей не будут видны при исполнении.
    Для третьего поля только одна аннотация будет видна при исполнении - остальные компилятор
    Если посмотреть скомпилированный Person.class, то аннотации уровня CLASS и RUNTIME будут видны,
        SOURCE - отсутствуют.
    */
    @MySourceRetentionAnnotation("source")
    private int field1;

    @MyClassRetentionAnnotation("class")
    private int field2;

    @MySourceRetentionAnnotation("source")
    @MyClassRetentionAnnotation("class")
    @MyRuntimeRetentionAnnotation("runtime")
    private int field3;

    public Person(int field1, int field2, int field3) {
        this.field1 = field1;
        this.field2 = field2;
        this.field3 = field3;
    }

    @MyMethodAnnotation
    @MyAnnotation(name = "sum", author = "frol", id = 1)
    public int sum() {
        return field1 + field2 + field3;
    }

    public int getField(@MyAnnotation(name = "getField.num", author = "frol", id = 2) int num) {
        switch (num) {
            case 1:
                return field1;
            case 2:
                return field2;
            case 3:
                return field3;
            default:
                return 0;
        }
    }
}