package multithreading.d_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Пример потоков с использованием пулов ExecutorService
 */
public class PoolStudy {
    public static void main(String[] args) throws InterruptedException {
        // пул потоков, которым можно скармливать Runnable`ы.
        // Их выполнением будет рулить пул. В этом примере в пуле два потоку выполнения
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        // загружаем пулу (из 2 потоков) 5 "работ"
        for (int i = 0; i < 5; i++) {
            executorService.submit(new Work(i));
        }
        System.out.println("Submitted");

        // закончили "выдавать работу".
        executorService.shutdown();        // Без shutdown'а awaitTermination будет ждать весь свой таймаут, потоки будут активны
        System.out.println("Shutdown");

        // после шатдауна дать пулу работу не получится
        //executorService.submit(new Work(20));

        // дожидаемся, пока все потоки из пула будут завершены
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
            // симуляция работы
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("--------- WORK (" + id + ") COMPLETED ----------");
    }
}