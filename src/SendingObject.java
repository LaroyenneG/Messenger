import java.io.IOException;

public interface SendingObject {

    void sendMessage(Message message) throws IOException;
}
