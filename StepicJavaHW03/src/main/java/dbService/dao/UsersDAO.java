package dbService.dao;

import base.UserProfile;
import dbService.dataSets.UsersDataSet;
import dbService.executor.Executor;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * @author v.chibrikov
 */
public class UsersDAO {

    private Executor executor;

    public UsersDAO(Connection connection) {
        this.executor = new Executor(connection);
    }

    public UsersDataSet get(String login) throws SQLException {
        return executor.execQuery("select * from users where login='" + login + "'", result -> {
            result.next();
            return new UsersDataSet(result.getLong(1), result.getString(2), result.getString(3));
        });
    }

    public long getUserId(String login) throws SQLException {
        return executor.execQuery("select id from users where login='" + login + "'", result -> {
            result.next();
            return result.getLong(1);
        });
    }

    public int getUsersCount() throws SQLException {
        return executor.execQuery("select count(id) from users", result -> {
            result.next();
            return result.getInt(1);
        });
    }

    public void insertUser(UserProfile profile) throws SQLException {
        executor.execUpdate("insert into users (login, password) values ('" + profile.getLogin() + "','" + profile.getPassword() + "')");
    }

    public void createTable() throws SQLException {
        executor.execUpdate("create table if not exists users (id bigint auto_increment, login varchar(256), password varchar(256), primary key (id))");
    }

    public void cleanup() throws SQLException {
        executor.execUpdate("drop table users");
    }
}
