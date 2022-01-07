package ru.job4j.concurrent;

public class Wget {
    public static void main(String[] args) {



        for (int i = 0; i <= 100; i++) {
            Thread thread = new Thread(
                    () -> {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
            );
            thread.start();
            System.out.println("\rLoading " + i + "%");



        }
    }
}