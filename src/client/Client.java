package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Client {

    private DatagramSocket datagramSocket;

    private String data;

    private List<ReceivedDataListener> receivedDataListeners;

    public Client(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
        data = null;
        receivedDataListeners = new ArrayList<>();
    }

    private static String getDataInDataGramPacket(DatagramPacket datagramPacket) {

        return new String(datagramPacket.getData(), 0, datagramPacket.getLength());
    }

    private static void printReceiveLog(DatagramPacket datagramPacket) {

        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append('[');
        stringBuilder.append(new Date());
        stringBuilder.append("] ");
        stringBuilder.append(datagramPacket.getAddress().getHostName());
        stringBuilder.append(':');
        stringBuilder.append(datagramPacket.getPort());
        stringBuilder.append(" ==> ");

        stringBuilder.append(getDataInDataGramPacket(datagramPacket));

        System.out.println(stringBuilder);
    }

    public void process() {

        final byte[] buffer = new byte[1204];

        while (!datagramSocket.isClosed()) {

            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);

            try {
                datagramSocket.receive(datagramPacket);
                printReceiveLog(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            String data = getDataInDataGramPacket(datagramPacket);

            if (this.data == null || this.data.equals(data)) {
                fireReceivedDataListener(new ReceivedDataEvent(this));
            }
        }
    }


    private void fireReceivedDataListener(ReceivedDataEvent event) {
        for (ReceivedDataListener receivedDataListener : receivedDataListeners) {
            receivedDataListener.actionReceived(new ReceivedDataEvent(this));
        }
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void addReceivedDataListener(ReceivedDataListener listener) {
        receivedDataListeners.add(listener);
    }

    public void removeReceivedDataListener(ReceivedDataListener listener) {
        receivedDataListeners.remove(listener);
    }
}
