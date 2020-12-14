package multithreading.e_prod_cons;

import java.util.LinkedList;
import java.util.Queue;

/**
 * ���������� �������� �������������/����������� � �������������� �������� LinkedList
 * ���������� ������������������ ��� ���� - ��� ������.
 */
public class ProdConsStudy2 {

    public static void main(String[] args) throws InterruptedException {
        ProducerConsumer pc = new ProducerConsumer();

        Thread prodThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    pc.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        prodThread.start();
        consThread.start();

        prodThread.join();
        consThread.join();
    }
}

class ProducerConsumer {
    private Queue<Integer> queue = new LinkedList<>();
    private final int limit = 10;

    // ��������� ���, �� ������� ����� ������������� ������ ��������� � ����������
    private final Object lock = new Object();

    public void produce() throws InterruptedException {
        int val = 0;
        while (true) {
            synchronized (lock) { // ��� ArrayBlockingQueue ��� ������������� ��������������� ����� �������������
                while (queue.size() == limit) {
                    lock.wait();
                }
                queue.offer(Integer.valueOf(val++));
                lock.notifyAll();
            }
        }
    }

    public void consume() throws InterruptedException{
        while (true) {
            synchronized (lock) { // ��� ArrayBlockingQueue ��� ������������� ��������������� ����� �������������
                while (queue.size() == 0) {
                    lock.wait();
                }
                System.out.println("Polled: " + queue.poll());
                lock.notifyAll();
                System.out.println("Queue size: " + queue.size());
            }
            System.out.println("Queue size: " + queue.size());
            Thread.sleep(500);
        }
    }
}