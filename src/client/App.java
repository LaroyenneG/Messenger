package client;

import java.io.IOException;
import java.net.DatagramSocket;

public class App {

    public static void main(String[] args) throws IOException {

        if (args.length != 3) {
            System.out.println("Usage : java Client <port> <data> <counter>");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);
        int counter = Integer.parseInt(args[2]);

        DatagramSocket datagramSocket = new DatagramSocket(port);

        Client client = new Client(datagramSocket);

        client.setData(args[1]);

        ExecCommandAfterTimeOut execCommandAfterTimeOut = new ExecCommandAfterTimeOut(counter, "");
        execCommandAfterTimeOut.start();

        client.addReceivedDataListener(execCommandAfterTimeOut);

        client.process();
    }
}
