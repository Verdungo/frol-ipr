package multithreading.e_prod_cons;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * ���������� �������� �������������/����������� � �������������� ArrayBlockingQueue
 */
public class ProdConsStudy {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * ������� �����������, ������ � ������������������ - �� ������.
     * � ���� ���� - ������� ����� �������������
     */
    private static BlockingQueue<Integer> queue= new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        // ����� ������������� - ����� � ������� ����� (������, ����� ������� �������� � ������ ���������)
        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        // ����� ����������� - �������� �� ������� ����� (� ���������� 100 ��)
        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        producerThread.start();
        consumerThread.start();

        producerThread.join();
        consumerThread.join();

    }

    private static void produce() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Integer i = Integer.valueOf(random.nextInt(20));
            System.out.println(ANSI_RED + "Producer �������: " + i + ANSI_RESET);
            queue.put(i);
            System.out.println(ANSI_RED + "Prosucer: ����� ������� - " + queue.size() + ANSI_RESET);
        }
    }

    private static Integer consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);
            if (random.nextInt(100) < 5) {
                System.out.println(ANSI_GREEN + "Consumer ����: " + queue.take() + "." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Consumer: ����� ������� - " + queue.size() + ANSI_RESET);
            }
        }
    }
}

class Producer implements Runnable {

    @Override
    public void run() {

    }
}

class Comsumer implements Runnable {

    @Override
    public void run() {

    }
}
