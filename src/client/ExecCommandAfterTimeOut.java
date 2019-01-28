package client;

import java.io.IOException;

public class ExecCommandAfterTimeOut extends Thread implements ReceivedDataListener {

    private final int maxValue;

    private int counter;

    private final String comand;

    public ExecCommandAfterTimeOut(int counter, String command) {
        this.counter = counter;
        maxValue = counter;
        this.comand = command;
    }

    @Override
    public void actionReceived(ReceivedDataEvent event) {
        increment();
    }

    private synchronized void increment() {

        if (counter < maxValue) {
            counter += maxValue / 2;
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
                    Runtime.getRuntime().exec(comand);
                    break;
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
