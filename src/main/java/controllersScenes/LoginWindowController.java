package controllersScenes;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import utils.ObservableResourceFactory;

public class LoginWindowController {
    private ObservableResourceFactory resourceFactory = ObservableResourceFactory.getInstance();

    @FXML
    private Button loginButtonLF;

    @FXML
    private TextField loginFieldLF;

    @FXML
    private TextField loginFieldRF;

    @FXML
    private VBox loginForm;

    @FXML
    private Label loginFormLabel;

    @FXML
    private PasswordField passwordField1RF;

    @FXML
    private PasswordField passwordField2RF;

    @FXML
    private PasswordField passwordFieldLF;

    @FXML
    private Button registerButtonRF;

    @FXML
    private VBox registerForm;

    @FXML
    private Label registerFormLabel;

    @FXML
    private Button toLoginButton;

    @FXML
    private Button toRegisterButton;

    @FXML
    private Button logoutButton;

    @FXML
    private VBox userForm;

    @FXML
    private Label usernameLabel;

    @FXML
    public void initialize() {
        guiI18n();
        toLoginButton.setOnAction(e->{
            loginForm.setVisible(true);
            registerForm.setVisible(false);
            toLoginButton.setVisible(false);
            toRegisterButton.setVisible(true);
        });
        toRegisterButton.setOnAction(event -> {
            loginForm.setVisible(false);
            registerForm.setVisible(true);
            toLoginButton.setVisible(true);
            toRegisterButton.setVisible(false);
        });
        logoutButton.setOnAction(event -> logout());
        loginButtonLF.setOnAction(event -> login());
        registerButtonRF.setOnAction(event -> register());
    }
    private void logout(){
        userForm.setVisible(false);
        loginForm.setVisible(true);
        toRegisterButton.setVisible(true);//TODO
    }
    private void login(){
        userForm.setVisible(true);
        loginForm.setVisible(false);
        toRegisterButton.setVisible(false);
    }
    private void register(){
        loginForm.setVisible(true);
        registerForm.setVisible(false);
        toLoginButton.setVisible(false);
        toRegisterButton.setVisible(true);
    }
    private void guiI18n(){
        loginFormLabel.textProperty().bind(resourceFactory.getStringBinding("label.loginForm"));
        registerFormLabel.textProperty().bind(resourceFactory.getStringBinding("label.registerForm"));
        toLoginButton.textProperty().bind(resourceFactory.getStringBinding("button.toLogin"));
        toRegisterButton.textProperty().bind(resourceFactory.getStringBinding("button.toRegister"));
        loginButtonLF.textProperty().bind(resourceFactory.getStringBinding("button.login"));
        registerButtonRF.textProperty().bind(resourceFactory.getStringBinding("button.register"));
        loginFieldLF.promptTextProperty().bind(resourceFactory.getStringBinding("fieldPrompt.login"));
        loginFieldRF.promptTextProperty().bind(resourceFactory.getStringBinding("fieldPrompt.login"));
        passwordFieldLF.promptTextProperty().bind(resourceFactory.getStringBinding("fieldPrompt.password"));
        passwordField1RF.promptTextProperty().bind(resourceFactory.getStringBinding("fieldPrompt.password"));
        passwordField2RF.promptTextProperty().bind(resourceFactory.getStringBinding("fieldPrompt.passwordAgain"));
        logoutButton.textProperty().bind(resourceFactory.getStringBinding("button.logout"));
    }
}
