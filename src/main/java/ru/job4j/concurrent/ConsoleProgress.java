package ru.job4j.concurrent;

public class ConsoleProgress implements Runnable {

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            String[] process = {"__", "  \\", "   |", "    /", "    __"};
            for (String s : process) {
                System.out.print("\r load: " + s);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                      Thread.currentThread().interrupt();
                }
            }
        }
    }
    public static void main(String[] args) throws InterruptedException {
        Thread progress = new Thread(new ConsoleProgress());
        progress.start();
        Thread.sleep(1000);
        progress.interrupt();
    }
}
