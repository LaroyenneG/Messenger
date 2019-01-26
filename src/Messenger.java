import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.*;

public class Messenger implements SendingObject {

    private Map<Message, Long> messages;
    private DatagramSocket datagramSocket;

    public Messenger(DatagramSocket datagramSocket) {
        messages = new HashMap<>();
        this.datagramSocket = datagramSocket;
    }

    public void loadDataFile(String dataFilePath) {

        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFilePath));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] args = line.split(" ");

                if (args.length >= 4) {

                    StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 3; i < args.length; i++) {
                        stringBuilder.append(args[i]);
                    }

                    String address = args[0];
                    int port = Integer.parseInt(args[1]);
                    long time = Long.parseLong(args[2]);
                    String data = new String(stringBuilder);
                    InetAddress inetAddress = InetAddress.getByName(address);

                    messages.put(new Message(inetAddress, port, data), time);
                }
            }
            reader.close();
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void process() {

        List<Thread> threads = new LinkedList<>();

        Map<Long, List<Message>> longListMap = new HashMap<>();

        for (Message message : messages.keySet()) {

            long time = messages.get(message);

            List<Message> messageList = longListMap.getOrDefault(time, new ArrayList<>());
            messageList.add(message);

            longListMap.put(time, messageList);
        }

        for (Long time : longListMap.keySet()) {
            threads.add(new ThreadMessage(longListMap.get(time), time, this));
        }

        for (Thread thread : threads) {
            thread.start();
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public synchronized void sendMessage(Message message) throws IOException {

        byte[] data = message.getData().getBytes();

        DatagramPacket datagramPacket = new DatagramPacket(data, 0, data.length, message.getInetAddress(), message.getPort());

        datagramSocket.send(datagramPacket);
    }

    private static class ThreadMessage extends Thread {

        private final SendingObject sendingObject;
        private final List<Message> messages;
        private long time;


        public ThreadMessage(List<Message> messages, long time, SendingObject sendingObject) {
            this.messages = messages;
            this.time = time;
            this.sendingObject = sendingObject;
        }

        @Override
        public void run() {

            while (!isInterrupted()) {

                List<Message> removedMessage = new LinkedList<>();

                for (Message message : messages) {
                    try {
                        sendingObject.sendMessage(message);
                    } catch (IOException e) {
                        removedMessage.add(message);
                        e.printStackTrace();
                    }
                }

                messages.removeAll(removedMessage);

                if (messages.isEmpty()) {
                    interrupt();
                }

                try {
                    Thread.sleep(time);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    break;
                }
            }
        }
    }
}
