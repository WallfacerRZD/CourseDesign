package experiment1.server.dao;

import experiment1.server.entity.User;
import experiment1.server.exception.UserDaoException;

import java.util.Set;

/**
 * @author WallfacerRZD
 * @date 2018/11/5 17:14
 */
public interface UserDao {
    /**
     * 查询已注册的所有用户
     *
     * @return 已注册的用户集合
     * @throws UserDaoException 查询用户时抛出的异常
     */
    Set<User> selectAllUsers() throws UserDaoException;

    /**
     * 插入一个新用户
     *
     * @param user 新用户对象
     * @throws UserDaoException 插入用户时抛出的异常
     */
    void insertOneUser(User user) throws UserDaoException;
}
