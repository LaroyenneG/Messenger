package client;

import java.util.EventListener;

public interface ReceivedDataListener extends EventListener {

    void actionReceived(ReceivedDataEvent event);
}
