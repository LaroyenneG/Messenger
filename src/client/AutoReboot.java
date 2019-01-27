package client;

import java.io.IOException;

public class AutoReboot extends Thread implements ReceivedDataListener {

    private int counter;

    public AutoReboot(int counter) {
        this.counter = counter;
    }

    @Override
    public void actionReceived(ReceivedDataEvent event) {
        counter++;
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

            counter--;

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
