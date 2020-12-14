package multithreading.a_threads;

import java.util.logging.Logger;

public class ThreadsStudy {
    private static final Logger LOGGER = Logger.getLogger(ThreadsStudy.class.getName());

    public static void main(String[] args) throws InterruptedException {
        //������� 1 - ��������� Thread
        MyThread myThread1 = new MyThread("MyThread");
        //������� 2 - ������������ Runnable
        Thread myThread2 = new Thread(new MyRunnable("MyRunnable"));
        myThread1.start();
        myThread2.start();

        LOGGER.info("----------------------------- ����� ����� ������� �������");

        Thread.sleep(2000);

        LOGGER.info("----------------------------- ������ ��������� ��� �������");
    }
}

class MyThread extends Thread {
    private static final Logger LOGGER = Logger.getLogger(MyThread.class.getName());

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            String msg = i + ": from thread '" + this.getName() + "'";
            LOGGER.info(msg);
        }
    }
}

class MyRunnable implements Runnable {
    private static final Logger LOGGER = Logger.getLogger(MyRunnable.class.getName());

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            String msg = i + ": from thread '" + name + "'";
            LOGGER.info(msg);
        }
    }
}
