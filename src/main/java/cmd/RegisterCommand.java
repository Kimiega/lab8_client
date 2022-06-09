package cmd;

import client.Environment;
import collection.UserToken;
import connection.NetPackage;
import ioManager.ConsoleManager;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Logger;

public class RegisterCommand implements ICommand{
    static Logger LOGGER = Logger.getLogger(RegisterCommand.class.getName());

    @Override
    public String getName() {
        return "register";
    }

    @Override
    public String getDescription() {
        return "register | Регистрация пользователя для доступа к БД";
    }

    @Override
    public boolean isLocal() {
        return false;
    }


    @Override
    public void execute(Environment env, String arg, NetPackage netPackage) {

    }

    public static void register(HashMap<String, ICommand> commandMap) {
        ICommand cmd = new RegisterCommand();
        commandMap.put(cmd.getName(), cmd);
    }
}
