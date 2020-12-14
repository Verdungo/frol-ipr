package multithreading.i_semaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * Имитация ограничения доступности ресурса (подключения)
 */
public class SemaphoreStudy {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService ex = Executors.newFixedThreadPool(200);

        // 100 "подключений" претендуют на ресурс одновременно
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

// имитация соединения с сервером с ограниченным числом подключений
class Connection {
    private static Connection instance = new Connection();
    private int connections = 0;
    // может быть не больше 10 подключений, будут пополняться по мере освобождения
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
            // release всегда в finally, чтобы гарантированно освобождать ресурс, если в doWork произошла ошибка
            semaphore.release();
        }
    }

    // имитация работы
    private void doWork(int i) throws InterruptedException {
        // connect
        synchronized (this) {
            connections++;
            System.out.println(i + ": " + connections + " connections");//тут никогда не должны увидеть больше 10
        }

        // WORK random period (чтобы все потоки не стартовали/завершались единовременно)
        Thread.sleep(new Random().nextInt(3000));

        // disconnect
        synchronized (this) {
            connections--;
        }
    }
}

