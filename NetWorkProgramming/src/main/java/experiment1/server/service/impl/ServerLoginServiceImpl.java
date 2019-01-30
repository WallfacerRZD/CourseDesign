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
 * @date 2018/11/11 14:16
 */
@Service
public class ServerLoginServiceImpl implements ServerService {
    private final UserDao userDao;

    @Autowired
    public ServerLoginServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public ResponseMsg serve(User user) {
        assert (user != null);
        ResponseMsg responseMsg;
        try {
            Set<User> registeredUsers = userDao.selectAllUsers();
            responseMsg = registeredUsers.contains(user) ?
                    new ResponseMsg(
                            ResponseMsg.Command.LOGIN,
                            ResponseMsg.Status.SUCCEED,
                            "登录成功, 你好 " + user.getAccount()
                    ) :
                    new ResponseMsg(
                            ResponseMsg.Command.LOGIN,
                            ResponseMsg.Status.FAILED,
                            "登录失败, 用户未注册或密码错误"
                    );
        } catch (UserDaoException e) {
            responseMsg = new ResponseMsg(
                    ResponseMsg.Command.LOGIN,
                    ResponseMsg.Status.FAILED,
                    "服务器内部错误"
            );
        }
        return responseMsg;
    }

}
