package cmd;

import collection.UserToken;
import exceptions.MaxLenUserException;
import ioManager.ConsoleManager;

public class LoginCommand {
    public static UserToken login(){
        UserToken user = null;
        while (true) {
                System.out.print("Enter login:");
                String login = ConsoleManager.getInstance().readline();
                System.out.print("Enter password:");
                String password = ConsoleManager.getInstance().readPassword();
                user = new UserToken(login, password);
                if (login.equals("") || password.equals("")){
                    System.err.println("Поля не могут быть пустыми");
                    continue;
                }
                break;
        }
        return user;
    }
}
