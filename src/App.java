import java.io.IOException;
import java.net.DatagramSocket;

public class App {

    public static void main(String[] args) throws IOException {

        if (args.length != 2) {
            System.out.println("Usage : java Messenger <port> <file>");
            System.exit(-1);
        }

        int port = Integer.parseInt(args[0]);

        DatagramSocket datagramSocket = new DatagramSocket(port);

        Messenger messenger = new Messenger(datagramSocket);

        messenger.loadDataFile(args[1]);

        messenger.process();
    }
}
