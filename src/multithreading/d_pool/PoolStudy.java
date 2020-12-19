package multithreading.d_pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;
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
        for (int i = 1; i <= 5; i++) {
            executorService.submit(new Work(i));
        }
        System.out.println("Submitted");

        // закончили "выдавать работу".
        executorService.shutdown();        // Без shutdown'а awaitTermination будет ждать весь свой таймаут, потоки будут активны
        System.out.println("Shutdown");

        // после шатдауна дать пулу работу нельзя
        try {
            executorService.submit(new Work(20));
        } catch (RejectedExecutionException e) {
            System.out.print("EXCEPTION! Попытка нагрузить пул работой после шатдауна. ");
            System.out.println("После shutdown'a пула добавить ему работы не получится!");
        }

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