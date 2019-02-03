package server;

import java.net.DatagramSocket;
import java.net.SocketException;

public class App {

    private static int TIME_TO_SLEEP = 1000;
    private static int MAX_FAIL_COUNT = 1000;

    public static void main(String[] args) {

        if (args.length != 2) {
            System.out.println("Usage : java Server <port> <file>");
            System.exit(-1);
        }

        int failCount = 0;

        while (failCount < MAX_FAIL_COUNT) {

            try {
                int port = Integer.parseInt(args[0]);

                DatagramSocket datagramSocket = new DatagramSocket(port);

                Server server = new Server(datagramSocket);

                server.loadDataFile(args[1]);

                server.process();
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
