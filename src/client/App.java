package client;

import java.net.DatagramSocket;
import java.net.SocketException;

public class App {

    private static int TIME_TO_SLEEP = 1000;
    private static int MAX_FAIL_COUNT = 1000;

    public static void main(String[] args) {

        if (args.length != 3) {
            System.out.println("Usage : java Client <port> <data> <counter>");
            System.exit(-1);
        }

        int failCount = 0;

        while (failCount < MAX_FAIL_COUNT) {

            try {
                int port = Integer.parseInt(args[0]);
                int counter = Integer.parseInt(args[2]);

                DatagramSocket datagramSocket = new DatagramSocket(port);

                Client client = new Client(datagramSocket);

                client.setData(args[1]);

                ExecCommandAfterTimeOut execCommandAfterTimeOut = new ExecCommandAfterTimeOut(counter, "sudo reboot");
                execCommandAfterTimeOut.start();

                client.addReceivedDataListener(execCommandAfterTimeOut);

                client.process();

            } catch (SocketException e) {
                e.printStackTrace();
                failCount++;
            }


            try {
                Thread.sleep(TIME_TO_SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
                break;
            }
        }
    }
}
