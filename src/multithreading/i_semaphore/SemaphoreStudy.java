package multithreading.i_semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * �������� ����������� ����������� ������� (�����������)
 */
public class SemaphoreStudy {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(200);

        // 100 "�����������" ���������� �� ������ ������������
        for (int i = 0; i < 200; i++) {
            int finalI = i;
            ex.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        Connection.getInstance().work(finalI);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            });
        }

        ex.shutdown();
        ex.awaitTermination(1, TimeUnit.DAYS);
    }
}

// �������� ���������� � �������� � ������������ ������ �����������
class Connection {
    private static Connection instance = new Connection();
    private int connections = 0;
    // ����� ���� �� ������ 10 �����������, ����� ����������� �� ���� ������������
    private final Semaphore semaphore = new Semaphore(10);

    private Connection() {
    }

    public static Connection getInstance() {
        return instance;
    }

    public void work(int i) throws InterruptedException {
        semaphore.acquire();
        try {
            doWork(i);
        } finally {
            // release ������ � finally, ����� �������������� ����������� ������, ���� � doWork ��������� ������
            semaphore.release();
        }
    }

    // �������� ������
    private void doWork(int i) throws InterruptedException {
        // connect
        synchronized (this) {
            connections++;
            System.out.println(i + ": " + connections + " connections");//��� ������� �� ������ ������� ������ 10
        }

        // WORK random period (����� ��� ������ �� ����������/����������� �������������)
        Thread.sleep(new Random().nextInt(3000));

        // disconnect
        synchronized (this) {
            connections--;
        }
    }
}

