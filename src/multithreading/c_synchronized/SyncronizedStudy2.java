package multithreading.c_synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SyncronizedStudy2 {
    public static void main(String[] args) throws InterruptedException {
        new Worker().main();
    }

}

/**
 * Закомментированный код ниже должен создавать по 2000 случайных элементов в каждом из списков
 * В силу несинхронизированности по факту элементов окажется меньше
 */
//class Worker {
//    private final Random rnd = new Random();
//
//    private final List<Integer> list1 = new ArrayList<>(2000);
//    private final List<Integer> list2 = new ArrayList<>(2000);
//
//    public void addToList1() {
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        list1.add(Integer.valueOf(rnd.nextInt(1000)));
//
//    }
//
//    public void addToList2() {
//        try {
//            Thread.sleep(1);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        list2.add(Integer.valueOf(rnd.nextInt(1000)));
//    }
//
//    public void work() {
//        for (int i = 0; i < 1000; i++) {
//            addToList1();
//            addToList2();
//        }
//    }
//
//    public void main() throws InterruptedException {
//        long before = System.currentTimeMillis();
//
//        Thread thread1 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                work();
//            }
//        });
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                work();
//            }
//        });
//
//        thread1.start();
//        thread2.start();
//
//        thread1.join();
//        thread2.join();
//
//        long after = System.currentTimeMillis();
//
//        System.out.println("Program took " + (after - before) + " ms.");
//
//        System.out.println("List1 has " + list1.size() + " elements");
//        System.out.println("List2 has " + list2.size() + " elements");
//    }
//}

/**
 * Пример сонхронизациис локом
 * Код ниже гарантированно создает по 2000 случайных элементов в каждом из списков
 * Синхронизация обеспечивается использованием блока synchronized
 */
class Worker {
    private final Random rnd = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        synchronized (lock1) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list1.add(Integer.valueOf(rnd.nextInt(1000)));
        }
    }

    public void addToList2() {
        synchronized (lock2) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            list2.add(Integer.valueOf(rnd.nextInt(1000)));
        }
    }

    public void work() {
        for (int i = 0; i < 1000; i++) {
            addToList1();
            addToList2();
        }
    }

    public void main() throws InterruptedException {
        long before = System.currentTimeMillis();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                work();
            }
        });

        thread1.start();
        thread2.start();

        thread1.join();
        thread2.join();

        long after = System.currentTimeMillis();

        System.out.println("Program took " + (after - before) + " ms.");

        System.out.println("List1 has " + list1.size() + " elements");
        System.out.println("List2 has " + list2.size() + " elements");
    }
}