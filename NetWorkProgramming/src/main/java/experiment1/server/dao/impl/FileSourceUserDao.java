package experiment1.server.dao.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import experiment1.server.dao.UserDao;
import experiment1.server.entity.User;
import experiment1.server.exception.UserDaoException;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

/**
 * 从文件系统中访问用户信息
 *
 * @author WallfacerRZD
 * @date 2018/11/5 17:14
 */
@Repository
public class FileSourceUserDao implements UserDao {
    private final String USER_DATA = "USER_DATA.json";

    @Override
    public Set<User> selectAllUsers() throws UserDaoException {
        Set<User> userSet = new HashSet<>();
        JsonArray users = readUsersFromJson();
        for (JsonElement user : users) {
            JsonObject jsonObject = (JsonObject) user;
            userSet.add(new User(
                    jsonObject.get("account").getAsString(),
                    jsonObject.get("password").getAsString()
            ));
        }
        return userSet;
    }

    @Override
    public void insertOneUser(User user) throws UserDaoException {
        appendOneUser(user);
    }

    private JsonArray readUsersFromJson() throws UserDaoException {
        File file = new File(USER_DATA);
        boolean fileExist = file.exists();
        if (!fileExist) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileReader jsonFile = new FileReader(file)) {
            JsonParser jsonParser = new JsonParser();
            return fileExist ?
                    (JsonArray) jsonParser.parse(jsonFile) :
                    new JsonArray();
        } catch (IOException e) {
            throw new UserDaoException("打开json文件失败", e);
        }
    }

    private void appendOneUser(User user) throws UserDaoException {
        JsonArray registeredUsers = readUsersFromJson();
        JsonObject newUser = new JsonObject();
        newUser.addProperty("account", user.getAccount());
        newUser.addProperty("password", user.getPassword());
        registeredUsers.add(newUser);
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(USER_DATA)))) {
            writer.write(registeredUsers.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
