package multithreading.a_threads;

public class ThreadsStudy {

    public static void main(String[] args) throws InterruptedException {
        //вариант 1 - наследник Thread
        MyThread myThread1 = new MyThread("MyThread");
        //вариант 2 - имлементация Runnable
        Thread myThread2 = new Thread(new MyRunnable("MyRunnable"));
        myThread1.start();
        myThread2.start();

        System.out.println("----------------------------- Сразу после запуска потоков");

        Thread.sleep(2000);

        System.out.println("----------------------------- Просто подождали две секунды");
    }
}

class MyThread extends Thread {

    public MyThread(String name) {
        super(name);
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            String msg = i + ": from thread '" + this.getName() + "'";
            System.out.println(msg);
        }
    }
}

class MyRunnable implements Runnable {

    private String name;

    public MyRunnable(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        for (int i = 0; i < 200; i++) {
            String msg = i + ": from thread '" + name + "'";
            System.out.println(msg);
        }
    }
}
