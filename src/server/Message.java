package server;

import java.net.InetAddress;

public class Message {

    private InetAddress inetAddress;
    private int port;
    private String data;

    public Message(InetAddress inetAddress, int port, String data) {
        this.inetAddress = inetAddress;
        this.port = port;
        this.data = data;
    }

    public InetAddress getInetAddress() {
        return inetAddress;
    }

    public String getData() {
        return data;
    }

    public int getPort() {
        return port;
    }
}
