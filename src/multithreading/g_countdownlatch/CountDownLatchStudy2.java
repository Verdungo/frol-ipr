package multithreading.g_countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Пример блокирования потока с применением CountDownLatch
 * тут наоборот основной поток отсчитывает, а другие ждут, когда им можно продолжать работать
 */
public class CountDownLatchStudy2 {
    public static void main(String[] args) throws InterruptedException {
        // этот латч должен "отсчитаться" 3 раза, чтобы await разрешил дальнейшее выполнение
        CountDownLatch cdl = new CountDownLatch(3);

        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            // передаем латч по все потоки, каждый его отсчитает после таймаута
            es.submit(new Processor2(cdl, i));
        }

        es.shutdown();

        // отсчитываем. когда будет ноль, висящие потоки продолжат выполнение
        for (int i = 0; i < 3; i++) {
            System.out.println("Текущее состояние латча: " + cdl.getCount());
            Thread.sleep(2000);
            cdl.countDown();
        }

        System.out.println("Латч отсчитался до нуля");
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

    /** поток ждет свой таймаут, а потом дожидается, когда извне отсчитается латч и продолжает выполнение*/
    @Override
    public void run() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            System.out.println("Поток ("+ id + ") подождал свой таймаут ожидает CountDownLatch");
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Поток ("+id+") дождался латч. Завершение");
    }
}
