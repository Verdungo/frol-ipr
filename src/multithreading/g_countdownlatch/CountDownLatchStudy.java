package multithreading.g_countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ������ ������������ ������ � ����������� CountDownLatch
 */
public class CountDownLatchStudy {
    public static void main(String[] args) throws InterruptedException {
        // ���� ���� ������ "�����������" 3 ����, ����� await �������� ���������� ����������
        CountDownLatch cdl = new CountDownLatch(3);

        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            // �������� ���� �� ��� ������, ������ ��� ��������� ����� ��������
            es.submit(new Processor(cdl, i));
        }

        es.shutdown();

        // ��� �����, ���� ������ �� ��������� ����
        cdl.await();

        System.out.println("���� ���������� �� ����");
    }
}

class Processor implements Runnable {
    private final CountDownLatch countDownLatch;
    private final int id;

    public Processor(CountDownLatch countDownLatch, int id) {
        this.countDownLatch = countDownLatch;
        this.id = id;
    }

    @Override
    public void run() {
        try {
            // ��������� �������
            Thread.sleep(new Random().nextInt(5000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        countDownLatch.countDown();
        System.out.println("Thread("+id+") �������� ����.");
    }
}
