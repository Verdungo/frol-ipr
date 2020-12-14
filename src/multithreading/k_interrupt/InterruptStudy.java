package multithreading.k_interrupt;

import java.util.Random;

/**
 * ������������ ���������� ����� ������� �������
 */
public class InterruptStudy {
    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Random random = new Random();
                // ��������� ������� ������� ��������
                for (int i = 0; i < 1_000_000_000; i++) {
                    //���� ����� ��� ������� �����, �����, ��� ��� �������� � ������� �� �����
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("INTERRUPTED!");
                        break;
                    }
                    Math.sin(random.nextDouble());
                }
            }
        });

        System.out.println("Statrnig thread...");

        thread.start();

        Thread.sleep(2000);

        // ������� ����� ������, ��� �� ���������� ���
        thread.interrupt();

        thread.join();

        System.out.println("Thread finished");
    }
}
