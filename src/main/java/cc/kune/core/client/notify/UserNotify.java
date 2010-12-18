package cc.kune.core.client.notify;

import org.ourproject.common.client.notify.NotifyLevel;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class UserNotify {
	@Order(1)
	NotifyLevel level;
	@Order(2)
	String message;
}