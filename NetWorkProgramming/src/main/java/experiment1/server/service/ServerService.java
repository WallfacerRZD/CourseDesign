package experiment1.server.service;

import experiment1.server.entity.ResponseMsg;
import experiment1.server.entity.User;

/**
 * @author WallfacerRZD
 * @date 2018/11/11 14:21
 */
public interface ServerService {
    ResponseMsg serve(User user);
}
