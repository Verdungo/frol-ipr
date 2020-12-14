package multithreading.h_reentrantlock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * ������������� � �������������� ReentrantLock
 */
public class ReentrantLockStudy {
    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                task.thread1();
            }
        });

        Thread thread2 = new Thread(new Runnable() {
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

// ������ ������������������� ������ ������������������� - ��������� �� ��������� � ���������
//class Task {
//    private int counter;
//
//    public void thread1() {
//        increment();
//    }
//
//    public void thread2() {
//        increment();
//    }
//
//    private void increment() {
//        for (int i = 0; i < 10000; i++) {
//            counter++;
//        }
//    }
//
//    public void showCounter() {
//        System.out.println("Total counts: " + counter);
//    }
//}

// ���������� ReentrantLock ��� �������������
class Task {
    private int counter;
    Lock lock = new ReentrantLock();

    // � ������ ������ ����� ������� ������ ����� ���, ����� - ���������
    public void thread1() {
        lock.lock();
        increment();
        lock.unlock();
    }

    public void thread2() {
        lock.lock();
        increment();
        lock.unlock();
    }

    private void increment() {
        for (int i = 0; i < 10000; i++) {
            counter++;
        }
    }

    public void showCounter() {
        System.out.println("Total counts: " + counter);
    }
}