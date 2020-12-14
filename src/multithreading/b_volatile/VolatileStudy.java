package multithreading.b_volatile;

import java.util.Scanner;

public class VolatileStudy {
    public static void main(String[] args) {
        MyThreadWithVolatile myThread = new MyThreadWithVolatile();
        myThread.start();

        // поток выполняется, в то время как основной стоит и ждет пользовательского ввода
        Scanner scanner = new Scanner(System.in);
        scanner.nextLine();

        myThread.shutdown();
    }
}

class MyThreadWithVolatile extends Thread {
    //volatile тут нужен для "некеширования" переменной
    private volatile boolean running = true;

    @Override
    public void run() {
        while (running) {
            System.out.println("Hello");
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
                interrupt();
            }
        }
    }

    public void shutdown() {
        running = false;
    }
}