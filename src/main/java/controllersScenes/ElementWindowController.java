package controllersScenes;

import client.Environment;
import collection.City;
import collection.Climate;
import collection.UserToken;
import exceptions.MaxLenUserException;
import ioManager.EmptyOut;
import ioManager.RequestElement;
import ioManager.WindowReader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import utils.ObservableResourceFactory;

import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Queue;

public class ElementWindowController {
    private City city = null;
    private boolean editChecker = true;
    private static Environment env = null;
    private ObservableResourceFactory resourceFactory = ObservableResourceFactory.getInstance();
    private static ObservableList<Climate> climates = FXCollections.observableArrayList();
    @FXML
    private Button addButton;
    @FXML
    private TextField agglomerationField;

    @FXML
    private Label agglomerationLabel;

    @FXML
    private TextField areaField;

    @FXML
    private Label areaLabel;

    @FXML
    private ChoiceBox<Climate> climateChoiceBox;

    @FXML
    private Label climateLabel;

    @FXML
    private TextField creationDateField;

    @FXML
    private Label creationDateLabel;

    @FXML
    private Button editButton;

    @FXML
    private TextField govAgeField;

    @FXML
    private Label govAgeLabel;

    @FXML
    private DatePicker govBirthField;

    @FXML
    private Label govBirthLabel;

    @FXML
    private Label govLabel;

    @FXML
    private TextField govNameField;

    @FXML
    private Label govNameLabel;

    @FXML
    private TextField idField;

    @FXML
    private Label idLabel;

    @FXML
    private CheckBox isThereGovChecker;

    @FXML
    private TextField metersAboveSeaField;

    @FXML
    private Label metersAboveSeaLabel;

    @FXML
    private TextField nameField;

    @FXML
    private Label nameLabel;

    @FXML
    private TextField ownerField;

    @FXML
    private Label ownerLabel;

    @FXML
    private TextField populationField;

    @FXML
    private Label populationLabel;

    @FXML
    private TextField timezoneField;

    @FXML
    private Label timezoneLabel;

    @FXML
    private TextField xField;

    @FXML
    private Label xLabel;

    @FXML
    private TextField yField;

    @FXML
    private Label yLabel;


    @FXML
    public void initialize() {
        fillFields();
        add();
        edit();
        clearReds();
        guiI18n();
        disablingCheckerBox();
        isThereGovChecker.setOnAction(e->disablingCheckerBox());
        climates.addAll(Climate.values());
        climates.add(null);
        climateChoiceBox.setItems(climates);
    }

    private void fillFields(){
        if (city!=null && editChecker)
        {
            idField.setText(Integer.toString(city.getId()));
            nameField.setText(city.getName());
            xField.setText(Long.toString(city.getCoordinates().getX()));
            yField.setText(Float.toString(city.getCoordinates().getY()));
            creationDateField.setText(city.getCreationDate().toString());
            areaField.setText(Integer.toString(city.getArea()));
            populationField.setText(Long.toString(city.getPopulation()));
            metersAboveSeaField.setText(Float.toString(city.getMetersAboveSeaLevel()));
            timezoneField.setText(Integer.toString(city.getTimezone()));
            agglomerationField.setText(Long.toString(city.getAgglomeration()));
            climateChoiceBox.setValue(city.getClimate());
            if (city.getGovernor()!=null) {
                govNameField.setText(city.getGovernor().getName());
                govAgeField.setText(Long.toString(city.getGovernor().getAge()));
                govBirthField.setValue(city.getGovernor().getBirthday().toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate());
            }
            ownerField.setText(city.getOwner());
            editChecker = !editChecker;
        }
    }
    private void clearReds(){
        nameField.setOnMouseClicked(e->nameField.setStyle(""));
        xField.setOnMouseClicked(e->xField.setStyle(""));
        yField.setOnMouseClicked(e->yField.setStyle(""));
        areaField.setOnMouseClicked(e->areaField.setStyle(""));
        populationField.setOnMouseClicked(e->populationField.setStyle(""));
        metersAboveSeaField.setOnMouseClicked(e->metersAboveSeaField.setStyle(""));
        timezoneField.setOnMouseClicked(e->timezoneField.setStyle(""));
        agglomerationField.setOnMouseClicked(e->agglomerationField.setStyle(""));
        govNameField.setOnMouseClicked(e->govNameField.setStyle(""));
        govBirthField.setOnMouseClicked(e->govNameField.setStyle(""));
    }
    private void add(){
        addButton.setOnAction(e->{
            City city = check();
            if (city!=null){}
                //env.getCommandMap().get("add").execute(e);
        });
    }
    private void edit(){
        editButton.setOnAction(e->{

        });
    }
    private City check(){
        Queue<String> queue = new ArrayDeque<>();
        queue.add(nameField.getText());
        queue.add(xField.getText());
        queue.add(yField.getText());
        queue.add(areaField.getText());
        queue.add(populationField.getText());
        queue.add(metersAboveSeaField.getText());
        queue.add(timezoneField.getText());
        queue.add(agglomerationField.getText());
        if (climateChoiceBox.getValue()==null)
            queue.add("");
        else
            queue.add(climateChoiceBox.getValue().toString());
        if (isThereGovChecker.isSelected()){
            queue.add(govNameField.getText());
            if (govBirthField.getValue()==null)
                queue.add("");
            else
            queue.add(govBirthField.getValue().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")));
        }
        else queue.add("");
        RequestElement reqEl = new RequestElement(new WindowReader(queue),new EmptyOut(),false);
        City o = reqEl.readElement(env.getUser());

        boolean flag = false;
        if (o.getName()==null || o.getName().equals("")) {
            nameField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getCoordinates()==null) {
            xField.setStyle("-fx-control-inner-background: #ed5f55;");
            yField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getCoordinates().getX()==null){
            xField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getCoordinates().getY()==null){
            yField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getArea()==null){
            areaField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getPopulation()==null){
            populationField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getMetersAboveSeaLevel()==null){
            metersAboveSeaField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getTimezone()==null){
            timezoneField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (o.getAgglomeration()==null){
            agglomerationField .setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        if (isThereGovChecker.isSelected() && o.getGovernor()==null){
            govNameField.setStyle("-fx-control-inner-background: #ed5f55;");
            flag = true;
        }
        else
            if (isThereGovChecker.isSelected() && o.getGovernor().getBirthday()==null)
            {
                govBirthField.setStyle("-fx-control-inner-background: #ed5f55;");
                flag = true;
            }
        if (flag){
            return null;
        }
        creationDateField.setText(o.getCreationDate().toString());
        ownerField.setText(o.getOwner());
        if (isThereGovChecker.isSelected())//TODO
            govAgeField.setText(Long.toString(o.getGovernor().getAge()));
        return o;
    }
    private void guiI18n(){
        idLabel.textProperty().bind(resourceFactory.getStringBinding("label.id"));
        nameLabel.textProperty().bind(resourceFactory.getStringBinding("label.name"));
        xLabel.textProperty().bind(resourceFactory.getStringBinding("label.x"));
        yLabel.textProperty().bind(resourceFactory.getStringBinding("label.y"));
        creationDateLabel.textProperty().bind(resourceFactory.getStringBinding("label.creationDate"));
        areaLabel.textProperty().bind(resourceFactory.getStringBinding("label.area"));
        populationLabel.textProperty().bind(resourceFactory.getStringBinding("label.population"));
        metersAboveSeaLabel.textProperty().bind(resourceFactory.getStringBinding("label.metersAboveSea"));
        timezoneLabel.textProperty().bind(resourceFactory.getStringBinding("label.timezone"));
        agglomerationLabel.textProperty().bind(resourceFactory.getStringBinding("label.agglomeration"));
        climateLabel.textProperty().bind(resourceFactory.getStringBinding("label.climate"));
        govLabel.textProperty().bind(resourceFactory.getStringBinding("label.governor"));
        govNameLabel.textProperty().bind(resourceFactory.getStringBinding("label.govsName"));
        govBirthLabel.textProperty().bind(resourceFactory.getStringBinding("label.govsBirth"));
        govAgeLabel.textProperty().bind(resourceFactory.getStringBinding("label.ageGov"));
        ownerLabel.textProperty().bind(resourceFactory.getStringBinding("label.owner"));
        isThereGovChecker.textProperty().bind(resourceFactory.getStringBinding("checkBox.isThereGov"));
        addButton.textProperty().bind(resourceFactory.getStringBinding("button.addElement"));
        editButton.textProperty().bind(resourceFactory.getStringBinding("button.editElement"));
    }
    private void disablingCheckerBox(){
        if (isThereGovChecker.isSelected()){
            govNameField.setDisable(false);
            govBirthField.setDisable(false);
            govAgeField.setDisable(false);
        }
        else{
            govNameField.setDisable(true);
            govBirthField.setDisable(true);
            govAgeField.setDisable(true);
        }
    }
    public void setCity(City o){
        city = o;
        editChecker = true;
    }

    public void setEnv(Environment env) {
        this.env = env;
    }
}
