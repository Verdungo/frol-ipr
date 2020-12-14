package multithreading.l_future;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * ������ ��������� ���������� ������ �������� � ����������� Future � Callable
 */
public class FutureStudy {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(1);

        // ����� �������� ��������� ������ ������
        // ��� ����� � ��� �������� �� Runnable (������� void), � Callable (���������� Future<?>)
        // ��� ����� ���������� raw, � ��� ������� =)
        Future future = executorService.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                // ���������� �����-�� ������ ����������
                Thread.sleep(5000);
                int randomValue = new Random().nextInt(10);

                // ������������ ������������, ����� ���������� ��� ���������� ��� ������� get()
                if (randomValue < 6) {
                    throw new Exception("random is less than 6");
                }

                return Integer.valueOf(randomValue);
            }
        });

        executorService.shutdown();

        System.out.println("future: " + future);
        try {
            // ��� ��� ������� get() ����� ����������, ���� ��� ������ �����.
            System.out.println("future.get: " + future.get());
            System.out.println("future.get.class: " + future.get().getClass());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            // ����������� � ������ ����������, ������� ��� ������� get()
            System.out.println(e.getCause().getMessage());
        }


        // ����� ����� ������ �� ����������, ������ ������ ��������� ������
        /*executorService.submit(new Runnable() {
            @Override
            public void run() {
                System.out.println("Starting");
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Finished");
                result++;
            }
        });

        executorService.shutdown();
        System.out.println(result);
        executorService.awaitTermination(1, TimeUnit.DAYS);

        System.out.println(result);*/

    }
}
