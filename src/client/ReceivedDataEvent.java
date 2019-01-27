package client;

import java.util.EventObject;

public class ReceivedDataEvent extends EventObject {

    public ReceivedDataEvent(Object source) {
        super(source);
    }
}
