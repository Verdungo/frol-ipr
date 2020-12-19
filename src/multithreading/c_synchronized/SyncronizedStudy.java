package multithreading.c_synchronized;

public class SyncronizedStudy {
    private int counter = 0;

    public static void main(String[] args) throws InterruptedException {
        SyncronizedStudy myClass = new SyncronizedStudy();

        long s = System.currentTimeMillis();
        myClass.doWork();
        System.out.println("doWork completed in " + (System.currentTimeMillis() - s) + " ms");
        s = System.currentTimeMillis();
        myClass.doSyncroWork();
        System.out.println("doWork completed in " + (System.currentTimeMillis() - s) + " ms");
    }

    // несинхронизированный метод. потоки будут инкрементировать счетчик часто неактуального значения
    // значения часто будут отличаться от ожидаемого результата
    private void inc() {
        counter++;
    }

    // синхронизированный метод. только один поток сможет входить в метод
    // гарантирован ожидаемый результат
    private synchronized void syncInc() {
        counter++;
    }

    /**
     * инкремент происходит несинхронизированно, результат некорректен
     */
    public void doWork() throws InterruptedException {
        counter = 0;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                inc();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                inc();
            }
        });

        // запускаем
        thread1.start();
        thread2.start();

        // дожидаемся выполнения
        thread1.join();
        thread2.join();

        System.out.println("Результат вызова несинхронизированного метода = " + counter);
    }

    public void doSyncroWork() throws InterruptedException {
        counter = 0;
        Thread thread1 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                syncInc();
            }
        });

        Thread thread2 = new Thread(() -> {
            for (int i = 0; i < 1000000; i++) {
                syncInc();
            }
        });

        // запускаем
        thread1.start();
        thread2.start();

        // дожидаемся выполнения
        thread1.join();
        thread2.join();

        System.out.println("Результат вызова синхронизированного метода = " + counter);

    }

    public int getCounter() {
        return counter;
    }
}