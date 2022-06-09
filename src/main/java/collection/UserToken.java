package collection;

import exceptions.MaxLenUserException;

import java.io.Serializable;

public class UserToken implements Serializable {
    private final String login;
    private final String password;

    public UserToken(String login, String password){
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }
}
