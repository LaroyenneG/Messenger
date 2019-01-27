package server;

import java.io.IOException;
import java.net.DatagramSocket;

public class App {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage : java Server <port> <file>");
            System.exit(-1);
        }

        System.out.close();

        int port = Integer.parseInt(args[0]);

        DatagramSocket datagramSocket = new DatagramSocket(port);

        Server server = new Server(datagramSocket);

        server.loadDataFile(args[1]);

        server.process();
    }
}
