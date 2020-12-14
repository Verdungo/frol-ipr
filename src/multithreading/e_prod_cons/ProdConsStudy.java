package multithreading.e_prod_cons;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Реализация паттерна производитель/потребитель с использованием ArrayBlockingQueue
 */
public class ProdConsStudy {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";

    /**
     * Очередь блокирующая, думать о потокобезопасности - ее задача.
     * В коде ниже - никаких явных синхронизаций
     */
    private static BlockingQueue<Integer> queue= new ArrayBlockingQueue<>(10);

    public static void main(String[] args) throws InterruptedException {
        // поток производителя - пишет в очередь числа (всегда, когда очередт доступна и размер позволяет)
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

        // поток потребителя - забирает из очереди числа (с интервалом 100 мс)
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
            System.out.println(ANSI_RED + "Producer положил: " + i + ANSI_RESET);
            queue.put(i);
            System.out.println(ANSI_RED + "Prosucer: длина очереди - " + queue.size() + ANSI_RESET);
        }
    }

    private static Integer consume() throws InterruptedException {
        Random random = new Random();

        while (true) {
            Thread.sleep(100);
            if (random.nextInt(100) < 5) {
                System.out.println(ANSI_GREEN + "Consumer взял: " + queue.take() + "." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "Consumer: длина очереди - " + queue.size() + ANSI_RESET);
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
