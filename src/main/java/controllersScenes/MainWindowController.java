package controllersScenes;

import client.Environment;
import collection.*;
import com.sun.prism.ResourceFactory;
import exceptions.MaxLenUserException;
import ioManager.ConsoleManager;
import ioManager.RequestElement;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import utils.ObservableResourceFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class MainWindowController {
    private static ObservableList<City> cities = FXCollections.observableArrayList();
    private static Environment env = null;
    private static ObservableList<String> langs = FXCollections.observableArrayList();
    private ObservableResourceFactory  resourceFactory = ObservableResourceFactory.getInstance();

        @FXML
        private Tab tableTab;
        @FXML
        private Tab mapTab;
        @FXML
        private Tab aboutTab;

        @FXML
        private TableView<City> tableView;

        @FXML
        private TableColumn<City, Long> agglomerationColumn;

        @FXML
        private TableColumn<City, Integer> areaColumn;

        @FXML
        private TableColumn<City, Climate> climateColumn;

        @FXML
        private TableColumn<City, Date> creationDateColumn;

        @FXML
        private TableColumn<City, Long> govAgeColumn;

        @FXML
        private TableColumn<City, LocalDate> govBirthColumn;

        @FXML
        private TableColumn<City, String> govNameColumn;

        @FXML
        private TableColumn<City, Integer> idColumn;

        @FXML
        private Button langButton;

        @FXML
        private Button loginButton;

        @FXML
        private Button enterElButton;

        @FXML
        private TableColumn<City, Float> metersAboveSeaColumn;

        @FXML
        private TableColumn<City, String> nameColumn;

        @FXML
        private TableColumn<City, String> ownerColumn;

        @FXML
        private TableColumn<City, Long> populationColumn;

        @FXML
        private TableColumn<City, Integer> timezoneColumn;

        @FXML
        private Label userLoggedInAsLabel;

        @FXML
        private Label userLoginLabel;

        @FXML
        private Label userNotAuthLabel;

        @FXML
        private ImageView loginButtonIcon;

        @FXML
        private TableColumn<City, Long> xColumn;

        @FXML
        private TableColumn<City, Float> yColumn;

        @FXML
        private TableColumn<City, Coordinates> coordinatesColumn;

        @FXML
        private TableColumn<City, Human> governorColumn;
        @FXML
        private ChoiceBox<String> langChoiceBox;

    @FXML
    public void initialize() {
        setAssociatingTable();
        guiI18n();
        languageBox();
        langButton.setOnAction(event -> {
            resourceFactory.setLocale(Locale.ENGLISH);
        });
        enterElButton.setOnAction(e->{
            createElementWindow();
        });

        loginButton.setOnAction(e->{
            //TODO if signed in then do another thing
            //RequestElement reqEl = new RequestElement(ConsoleManager.getInstance(),ConsoleManager.getInstance(),true);
            City city = new City("lol",new Coordinates(323L,3232.32f),new Date(),5,555555L,3232.32f,2,
                    43243L,null,null,"Adminos");
            City city2 = new City("lol",new Coordinates(323L,3232.32f),new Date(),5,555555L,3232.32f,2,
                    43243L,null,null,"Adminososi");
            cities.addAll(city,city2);

            createLoginWindow();
        });
        tableView.setItems(cities);

    }

    private void setAssociatingTable(){
        idColumn.setCellValueFactory(new PropertyValueFactory<City,Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<City,String>("name"));
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<City,Coordinates>("coordinates"));
        xColumn.setCellValueFactory(ceil ->new SimpleObjectProperty<Long>(ceil.getValue().getCoordinates().getX()));
        yColumn.setCellValueFactory(ceil ->new SimpleObjectProperty<Float>(ceil.getValue().getCoordinates().getY()));
        creationDateColumn.setCellValueFactory(new PropertyValueFactory<City,Date>("creationDate"));
        areaColumn.setCellValueFactory(new PropertyValueFactory<City,Integer>("area"));
        populationColumn.setCellValueFactory(new PropertyValueFactory<City,Long>("population"));
        metersAboveSeaColumn.setCellValueFactory(new PropertyValueFactory<City,Float>("metersAboveSeaLevel"));
        timezoneColumn.setCellValueFactory(new PropertyValueFactory<City,Integer>("timezone"));
        agglomerationColumn.setCellValueFactory(new PropertyValueFactory<City,Long>("agglomeration"));
        climateColumn.setCellValueFactory(new PropertyValueFactory<City,Climate>("climate"));
        governorColumn.setCellValueFactory(new PropertyValueFactory<City,Human>("governor"));
        governorColumn.setCellValueFactory(c -> new SimpleObjectProperty<Human>(c.getValue().getGovernor()));
        govNameColumn.setCellValueFactory(ceil ->(ceil.getValue().getGovernor()!=null) ? new SimpleStringProperty(ceil.getValue().getGovernor().getName()):new SimpleStringProperty(null));
        govAgeColumn.setCellValueFactory(ceil ->(ceil.getValue().getGovernor()!=null) ? new SimpleObjectProperty<Long>(ceil.getValue().getGovernor().getAge()):new SimpleObjectProperty<Long>(null));
        govBirthColumn.setCellValueFactory(ceil ->(ceil.getValue().getGovernor()!=null) ? new SimpleObjectProperty<LocalDate>(ceil.getValue().getGovernor().getBirthday().toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate()):new SimpleObjectProperty<LocalDate>(null));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<City,String>("owner"));
        tableView.setRowFactory(
                tableView -> {
                    final TableRow<City> row = new TableRow<>();
                    final ContextMenu rowMenu = new ContextMenu();
                    MenuItem editItem = new MenuItem();
                    editItem.textProperty().bind(resourceFactory.getStringBinding("menubutton.edit"));
                    MenuItem removeItem = new MenuItem();
                    removeItem.textProperty().bind(resourceFactory.getStringBinding("menubutton.delete"));
                    editItem.setOnAction(event -> editCM(row.getItem()));
                    removeItem.setOnAction(event -> removeCM(row.getItem()));
                    rowMenu.getItems().addAll(editItem, removeItem);

                    // only display context menu for non-empty rows:
                    row.contextMenuProperty().bind(
                            Bindings.when(row.emptyProperty())
                                    .then((ContextMenu) null)
                                    .otherwise(rowMenu));
                    return row;
                });

    }

    private void guiI18n(){
        idColumn.textProperty().bind(resourceFactory.getStringBinding("column.id"));
        nameColumn.textProperty().bind(resourceFactory.getStringBinding("column.name"));
        coordinatesColumn.textProperty().bind(resourceFactory.getStringBinding("column.coords"));
        xColumn.textProperty().bind(resourceFactory.getStringBinding("column.x"));
        yColumn.textProperty().bind(resourceFactory.getStringBinding("column.y"));
        creationDateColumn.textProperty().bind(resourceFactory.getStringBinding("column.creationDate"));
        areaColumn.textProperty().bind(resourceFactory.getStringBinding("column.area"));
        populationColumn.textProperty().bind(resourceFactory.getStringBinding("column.population"));
        metersAboveSeaColumn.textProperty().bind(resourceFactory.getStringBinding("column.metersAboveSea"));
        timezoneColumn.textProperty().bind(resourceFactory.getStringBinding("column.timezone"));
        agglomerationColumn.textProperty().bind(resourceFactory.getStringBinding("column.agglomeration"));
        climateColumn.textProperty().bind(resourceFactory.getStringBinding("column.climate"));
        governorColumn.textProperty().bind(resourceFactory.getStringBinding("column.gov"));
        govNameColumn.textProperty().bind(resourceFactory.getStringBinding("column.govName"));
        govAgeColumn.textProperty().bind(resourceFactory.getStringBinding("column.govAge"));
        govBirthColumn.textProperty().bind(resourceFactory.getStringBinding("column.govBirth"));
        ownerColumn.textProperty().bind(resourceFactory.getStringBinding("column.owner"));
        tableTab.textProperty().bind(resourceFactory.getStringBinding("tab.table"));
        mapTab.textProperty().bind(resourceFactory.getStringBinding("tab.map"));
        aboutTab.textProperty().bind(resourceFactory.getStringBinding("tab.about"));
        userLoggedInAsLabel.textProperty().bind(resourceFactory.getStringBinding(env.getUser()!=null ? "label.loggedInAs" : "label.notLoggedIn"));


    }
    private void languageBox(){
        Arrays.stream(resourceFactory.getLocales()).forEach(l -> langs.add(l.getDisplayLanguage(l)));
        langs.add(resourceFactory.getStringBinding("choiceBox.chooseLang").getValue());
        langChoiceBox.setItems(langs);
        langChoiceBox.setValue(resourceFactory.getStringBinding("choiceBox.chooseLang").getValue());
        langChoiceBox.setOnAction(event -> {
            langs.remove(resourceFactory.getStringBinding("choiceBox.chooseLang").getValue());
            resourceFactory.setLocale(Arrays.stream(resourceFactory.getLocales()).filter(e->e.getDisplayLanguage(e).equals(langChoiceBox.getSelectionModel().getSelectedItem())).findFirst().get());

        });
    }

    private void removeCM(City c){
        if (env.getUser() == null)
            alertDialog(resourceFactory.getStringBinding("alert.notAuth").get(), Alert.AlertType.WARNING);
        else if (!c.getOwner().equals(env.getUser().getLogin()))
            alertDialog(resourceFactory.getStringBinding("alert.notOwner").get(), Alert.AlertType.WARNING);
        else {
            cities.remove(c);
        }
        //TODO and if another user
    }
    private void editCM(City c){
        createElementWindow();
        env.getElementWindowController().setCity(c);
        //TODO and if another user
        return;
    }
    private void alertDialog(String message, Alert.AlertType alertType){
        Alert a = new Alert(alertType);
         a.setContentText(message);
         a.setTitle(resourceFactory.getStringBinding("alert.warning").get());
         a.setHeaderText(resourceFactory.getStringBinding("alert.warning").get());
         a.show();
    }
    private void createElementWindow(){
        if (env.getElementWindowStage()==null || !env.getElementWindowStage().isShowing()) {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenesFXML/ElementWindow.fxml"), resourceFactory.getResources());
                env.setElementWindowController(loader.getController());
                root = loader.load();
                env.setElementWindowStage(new Stage());
                env.getElementWindowStage().setTitle("element window");
                env.getElementWindowStage().setScene(new Scene(root, 600, 400));
                env.getElementWindowStage().setMinWidth(600);
                env.getElementWindowStage().setMinHeight(780);
                env.getElementWindowStage().show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else env.getElementWindowStage().toFront();
    }
    private void createLoginWindow(){
        if (env.getAuthWindowStage() == null || !env.getAuthWindowStage().isShowing()) {
            Parent root;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("scenesFXML/LoginWindow.fxml"),resourceFactory.getResources());
                env.setAuthWindowController(loader.getController());
                root = loader.load();
                env.setAuthWindowStage(new Stage());
                env.getAuthWindowStage().setTitle("Auth form");
                env.getAuthWindowStage().setScene(new Scene(root, 600, 400));
                env.getAuthWindowStage().setMinWidth(600);
                env.getAuthWindowStage().setMinHeight(400);
                env.getAuthWindowStage().show();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        else env.getAuthWindowStage().toFront();
    }

    public static void setEnv(Environment env) {
        MainWindowController.env = env;
    }
}