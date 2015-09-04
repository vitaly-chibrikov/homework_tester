package frontend;

import base.AccountService;
import base.DBService;
import base.UserProfile;
import dbService.DBException;

/**
 * @author v.chibrikov
 */
public class AccountServiceImpl implements AccountService {
    private final DBService dbService;

    public AccountServiceImpl(DBService dbService) {
        this.dbService = dbService;
    }

    @Override
    public void singUp(String login, String password) {
        try {
            dbService.addUser(new UserProfile(login, password));
        } catch (DBException e) {
            System.out.println("Can't sing in: " + e.getMessage());
        }
    }

    @Override
    public boolean singIn(String login, String password) {
        try {
            UserProfile profile = dbService.getUser(login);
            return profile != null && profile.getPassword().equals(password);
        } catch (DBException e) {
            System.out.println("Can't sing in: " + e.getMessage());
            return false;
        }
    }
}
