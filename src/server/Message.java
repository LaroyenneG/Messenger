package server;

import java.net.InetAddress;
import java.util.Objects;

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


    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Message message = (Message) o;

        return port == message.port &&
                Objects.equals(inetAddress, message.inetAddress) &&
                Objects.equals(data, message.data);
    }
}
