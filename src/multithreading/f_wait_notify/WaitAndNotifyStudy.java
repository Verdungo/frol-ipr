package multithreading.f_wait_notify;

import java.util.Scanner;

/**
 * ¬се тот же паттерн producer/consumer
 * с применением методов Object wait() и notify()
 */
public class WaitAndNotifyStudy {
    public static void main(String[] args) throws InterruptedException {
        WaitAndNotify waitAndNotify = new WaitAndNotify();

        Thread prodThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitAndNotify.produce();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    waitAndNotify.consume();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        prodThread.start();
        consThread.start();

        prodThread.join();
        consThread.join();
    }
}

class WaitAndNotify {
    public void produce() throws InterruptedException {
        // синхронизаци€ на "самом себе"
        synchronized (this) {
            System.out.println("Producer thread started...");
            this.wait();// отдаем лок другим потокам и ждем notify от других потоков
            System.out.println("Producer thread resumed...");
        }
    }

    public void consume() throws InterruptedException{
        Thread.sleep(2000);
        Scanner in = new Scanner(System.in);

        synchronized (this) {
            System.out.println("---Waiting for return key pressed...");
            in.nextLine();

            System.out.println("---Key pressed... notifying");
            notify();// возвращаем лок, продюсер продолжит работу после выхода из этого synch-блока

            System.out.println("---Waiting for another return key press...");
            in.nextLine(); // ждем, чтобы показать, что notify не вернет лок мгновенно

            System.out.println("---Key pressed... end of synchronized block, returnong lock");
        }
        Thread.sleep(1000);
        System.out.println("---Out of synchro: end of method");
    }
}