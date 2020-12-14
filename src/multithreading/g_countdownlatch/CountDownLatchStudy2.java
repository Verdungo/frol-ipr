package multithreading.g_countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ������ ������������ ������ � ����������� CountDownLatch
 * ��� �������� �������� ����� �����������, � ������ ����, ����� �� ����� ���������� ��������
 */
public class CountDownLatchStudy2 {
    public static void main(String[] args) throws InterruptedException {
        // ���� ���� ������ "�����������" 3 ����, ����� await �������� ���������� ����������
        CountDownLatch cdl = new CountDownLatch(3);

        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            // �������� ���� �� ��� ������, ������ ��� ��������� ����� ��������
            es.submit(new Processor2(cdl, i));
        }

        es.shutdown();

        // �����������. ����� ����� ����, ������� ������ ��������� ����������
        for (int i = 0; i < 3; i++) {
            System.out.println("������� ��������� �����: " + cdl.getCount());
            Thread.sleep(2000);
            cdl.countDown();
        }

        System.out.println("���� ���������� �� ����");
        es.awaitTermination(1, TimeUnit.DAYS);
    }
}

class Processor2 implements Runnable {
    private final CountDownLatch countDownLatch;
    private final int id;

    public Processor2(CountDownLatch countDownLatch, int id) {
        this.countDownLatch = countDownLatch;
        this.id = id;
    }

    /** ����� ���� ���� �������, � ����� ����������, ����� ����� ����������� ���� � ���������� ����������*/
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("����� ("+ id + ") �������� ���� ������� ������� CountDownLatch");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("����� ("+id+") �������� ����. ����������");
    }
}
