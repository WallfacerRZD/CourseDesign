package experiment1.server.service.impl;

import experiment1.server.dao.UserDao;
import experiment1.server.entity.ResponseMsg;
import experiment1.server.entity.User;
import experiment1.server.exception.UserDaoException;
import experiment1.server.service.ServerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * @author WallfacerRZD
 * @date 2018/11/5 16:40
 */
@Service
public class ServerRegistrationServiceImpl implements ServerService {
    private final UserDao userDao;

    @Autowired
    public ServerRegistrationServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseMsg serve(User user) {
        assert (user != null);
        try {
            Set<User> registeredUsers = userDao.selectAllUsers();
            if (registeredUsers.contains(user)) {
                return new ResponseMsg(
                        ResponseMsg.Command.REGISTRATION,
                        ResponseMsg.Status.FAILED,
                        user.getAccount() + "  已注册了"
                );
            }
            userDao.insertOneUser(user);
            return new ResponseMsg(
                    ResponseMsg.Command.REGISTRATION,
                    ResponseMsg.Status.SUCCEED,
                    "注册成功, 你好 " + user.getAccount()
            );
        } catch (UserDaoException e) {
            return new ResponseMsg(
                    ResponseMsg.Command.REGISTRATION,
                    ResponseMsg.Status.FAILED,
                    "服务器内部错误"
            );
        }
    }
}
