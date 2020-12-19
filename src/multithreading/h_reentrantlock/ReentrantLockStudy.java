package multithreading.h_reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Синхронизация с использованием ReentrantLock
 */
public class ReentrantLockStudy {
    public static void main(String[] args) throws InterruptedException {

        // Без синхронизации
        TaskNotSyncro taskNotSyncro = new TaskNotSyncro();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                taskNotSyncro.thread1();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                taskNotSyncro.thread2();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        taskNotSyncro.showCounter();




        // С синхронизацией
        Task task = new Task();

        thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                task.thread1();
            }
        });

        thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                task.thread2();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        task.showCounter();

    }
}

// методы класса несинхронизированны - результат не совпадает с ожидаемым
class TaskNotSyncro {
    private int counter;

    public void thread1() {
        increment();
    }

    public void thread2() {
        increment();
    }

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }

    public void showCounter() {
        System.out.println("Результат работы без применения ReentrantLock'a = " + counter);
    }
}

// используем ReentrantLock для синхронизации
class Task {
    private int counter;
    Lock lock = new ReentrantLock();

    // в каждом методе перед вызовом метода берем лок, после - отпускаем
    public void thread1() {
        lock.lock();
        increment();
        // АТТЕНШЕН! тут нет анлока! Анлочим внутри метода.
    }

    public void thread2() {
        lock.lock();
        increment();
        // АТТЕНШЕН! тут нет анлока! Анлочим внутри метода.
    }

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
        // В этом примере - анлок тут, но это ИМХО не наглядно, и можно запутаться.
        lock.unlock();
    }

    public void showCounter() {
        System.out.println("Результат работы c применением ReentrantLock'a = " + counter);
    }
}