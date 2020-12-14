package annotations;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * ������������ ���������������� ��������� {@link ActionListenerFor},
 * ��������������� ���������� ������� (������� ������)
 */
//@MyMethodAnnotation - �� ���������, �.�. ����� ������ ElementType.METHOD
@MyAnnotation(name = "name", id = 1) // ����� ���� ������ ������������ ��� default-��������
public class AnnotationStudy {
    public static void main(String[] args) {
        ButtonFrame frame = new ButtonFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}

/**
 * ��������������� ����� ������ � ��������
 */
class ButtonFrame extends JFrame {
    private static final int DEFAULT_HEIGHT = 200;
    private static final int DEFAULT_WIDTH = 300;
    private final JPanel panel;
    private final JButton yellowButton = new JButton("Yellow");
    private final JButton blueButton = new JButton("Blue");
    private final JButton redButton = new JButton("Red");

    public ButtonFrame() throws HeadlessException {
        setTitle("������ � ��������");
        setSize(DEFAULT_WIDTH,DEFAULT_HEIGHT);

        panel = new JPanel();
        add(panel);

        panel.add(yellowButton);
        panel.add(blueButton);
        panel.add(redButton);

        ActionListenerUtil.setListeners(this);

    }

    // ������ ������������ � ������� ���������

    @ActionListenerFor("yellowButton")
    public void yellowBackground() {
        panel.setBackground(Color.YELLOW);
    }

    @ActionListenerFor("blueButton")
    public void blueBackground() {
        panel.setBackground(Color.BLUE);
    }

    @ActionListenerFor("redButton")
    public void redBackground() {
        panel.setBackground(Color.RED);
    }

}

/**
 * �� ���������� ��������� �����
 * ��� ���������� ���������� �������� � �� ����������
 * � ��� - ���������!
 */
class ActionListenerUtil {

    /**
     * ���� ��� �������������� ActionListenerFor ������, ����������� � ��������������� �����
     * @param obj
     */
    @MyMethodAnnotation
    public static void setListeners(Object obj) {
        Class cl = obj.getClass();
        for (Method method : cl.getDeclaredMethods()) {
            ActionListenerFor a = method.getAnnotation(ActionListenerFor.class);
            if (a != null) {
                try {
                    String fieldName = a.value();
                    Field field = cl.getDeclaredField(fieldName);
                    field.setAccessible(true);

                    addListener(field.get(obj), obj, method);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void addListener(Object field, final Object container, Method method) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        InvocationHandler handler = new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method m, Object[] args) throws Throwable {
                return method.invoke(container);
            }
        };

        Object listener = Proxy.newProxyInstance(null, new Class[] {java.awt.event.ActionListener.class}, handler);
        Method addMethod = field.getClass().getMethod("addActionListener", ActionListener.class);

        addMethod.invoke(field, listener);
    }


}



