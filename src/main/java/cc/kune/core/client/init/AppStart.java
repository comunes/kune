package cc.kune.core.client.init;

import cc.kune.core.shared.dto.InitDataDTO;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class AppStart {
    @Order(1)
    InitDataDTO initData;
}
