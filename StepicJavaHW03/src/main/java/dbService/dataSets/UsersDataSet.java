package dbService.dataSets;

/**
 * @author v.chibrikov
 */
@SuppressWarnings("UnusedDeclaration")
public class UsersDataSet {
    private final long id;
    private final String login;
    private final String password;

    public UsersDataSet(long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public long getId() {
        return id;
    }
}
