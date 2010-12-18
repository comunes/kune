package cc.kune.core.client.notify;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class Alert {
	@Order(1)
	String title;
	@Order(2)
	String message;
}
