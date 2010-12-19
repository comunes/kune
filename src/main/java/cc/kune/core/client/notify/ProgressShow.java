package cc.kune.core.client.notify;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class ProgressShow {
    @Order(1)
    String message;
}
