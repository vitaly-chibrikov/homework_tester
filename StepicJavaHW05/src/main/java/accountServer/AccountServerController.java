package accountServer;

/**
 * @author v.chibrikov
 */
public class AccountServerController implements AccountServerControllerMBean {
    private final AccountServer accountServer;

    public AccountServerController(AccountServer accountServer) {
        this.accountServer = accountServer;
    }

    @Override
    public int getUsersLimit() {
        return accountServer.getUsersLimit();
    }

    @Override
    public void setUsersLimit(int bla) {
        accountServer.setUsersLimit(bla);
    }
}
