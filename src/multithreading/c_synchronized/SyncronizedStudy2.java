package multithreading.c_synchronized;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SyncronizedStudy2 {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("Not synchronized ---------------------");
        new Worker().main();
        System.out.println("\r\nSynchronized ---------------------");
        new WorkerWithLocks().main();
    }

}

/**
 * Код ниже должен создавать по 200000 случайных элементов в каждом из списков
 * В силу несинхронизированности по факту элементов окажется меньше
 */
class Worker {
    private final Random rnd = new Random();

    private final List<Integer> list1 = new ArrayList<>(200000);
    private final List<Integer> list2 = new ArrayList<>(200000);

    public void addToList1() {
        list1.add(Integer.valueOf(rnd.nextInt(1000)));
    }

    public void addToList2() {
        list2.add(Integer.valueOf(rnd.nextInt(1000)));
    }

    public void work() {
        for (int i = 0; i < 100000; i++) {
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

    public List<Integer> getList1() {
        return list1;
    }

    public List<Integer> getList2() {
        return list2;
    }
}

/**
 * Пример сонхронизациис локом
 * Код ниже гарантированно создает по 200000 случайных элементов в каждом из списков
 * Синхронизация обеспечивается использованием блока synchronized
 */
class WorkerWithLocks {
    private final Random rnd = new Random();

    private final Object lock1 = new Object();
    private final Object lock2 = new Object();

    private final List<Integer> list1 = new ArrayList<>();
    private final List<Integer> list2 = new ArrayList<>();

    public void addToList1() {
        synchronized (lock1) {
            list1.add(Integer.valueOf(rnd.nextInt(1000)));
        }
    }

    public void addToList2() {
        synchronized (lock2) {
            list2.add(Integer.valueOf(rnd.nextInt(1000)));
        }
    }

    public void work() {
        for (int i = 0; i < 100000; i++) {
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

    public List<Integer> getList1() {
        return list1;
    }

    public List<Integer> getList2() {
        return list2;
    }
}