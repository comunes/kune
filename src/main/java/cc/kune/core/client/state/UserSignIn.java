package cc.kune.core.client.state;

import cc.kune.core.shared.dto.UserInfoDTO;

import com.gwtplatform.annotation.GenEvent;
import com.gwtplatform.annotation.Order;

@GenEvent
public class UserSignIn {
    @Order(1)
    UserInfoDTO userInfo;
}
