package frontend;

import base.AccountService;
import base.UserProfile;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author v.chibrikov
 */
public class AccountServiceImpl implements AccountService {
    private final Map<String, UserProfile> signedUpUsers = new ConcurrentHashMap<>();

    @Override
    public void singUp(String login, String password) {
        signedUpUsers.put(login, new UserProfile(login, password));
    }

    @Override
    public boolean singIn(String login, String password) {
        UserProfile profile = signedUpUsers.get(login);
        return profile != null && profile.getPassword().equals(password);
    }
}
