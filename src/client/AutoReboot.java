package client;

import java.io.IOException;

public class AutoReboot extends Thread implements ReceivedDataListener {

    private static final int MAX_VALUE = 10;

    private int counter;

    public AutoReboot(int counter) {
        this.counter = counter;
    }

    @Override
    public void actionReceived(ReceivedDataEvent event) {
        increment();
    }

    private synchronized void increment() {

        if (counter < MAX_VALUE) {
            counter += MAX_VALUE / 2;
        }
    }

    private synchronized void decrement() {

        if (counter >= 0) {
            counter--;
        }
    }

    @Override
    public void run() {

        while (!isInterrupted()) {

            if (counter <= 0) {
                try {
                    Runtime.getRuntime().exec("sudo reboot");
                    System.exit(-1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            decrement();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
