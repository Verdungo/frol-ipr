package multithreading.d_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * ������ ������� � �������������� ����� ExecutorService
 */
public class PoolStudy {
    public static void main(String[] args) throws InterruptedException {
        // ��� �������, ������� ����� ����������� Runnable`�.
        // �� ����������� ����� ������ ���. � ���� ������� � ���� ��� ������ ����������
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // ��������� ���� (�� 2 �������) 5 "�����"
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i));
        }
        System.out.println("Submitted");

        // ��������� "�������� ������".
        executorService.shutdown();        // ��� shutdown'� awaitTermination ����� ����� ���� ���� �������, ������ ����� �������
        System.out.println("Shutdown");

        // ����� �������� ���� ���� ������ �� ���������
        //executorService.submit(new Work(20));

        // ����������, ���� ��� ������ �� ���� ����� ���������
        executorService.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println("awaited");
    }
}


class Work implements Runnable{
    private int id;

    public Work(int id) {
        this.id = id;
    }

    @Override
    public void run() {
        System.out.println("--------- BEGIN WORK (" + id + ") ----------");
        try {
            // ��������� ������
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------- WORK (" + id + ") COMPLETED ----------");
    }
}