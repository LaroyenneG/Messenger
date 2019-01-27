package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
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


    public void process() {

        final byte[] buffer = new byte[1204];

        while (!datagramSocket.isClosed()) {

            DatagramPacket datagramPacket = new DatagramPacket(buffer, 0, buffer.length);

            try {
                datagramSocket.receive(datagramPacket);
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            String data = new String(datagramPacket.getData());

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
