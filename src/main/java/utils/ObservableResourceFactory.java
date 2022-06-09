package utils;

import exceptions.ResourceException;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.HashMap;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ObservableResourceFactory {
    private static ObservableResourceFactory instance;
    private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();
    private Locale[] locales;
    private String chosenLang;

    public ObservableResourceFactory(ResourceBundle resourceBundle,Locale[] locales){
        setResources(resourceBundle);
        this.locales = locales;
        instance = this;
    }
    private ObservableResourceFactory(){
    }

    public Locale[] getLocales() {
        return locales;
    }
    public void setLocale(Locale locale){
        try {
            setResources(ResourceBundle.getBundle(resources.get().getBaseBundleName(), locale));
        } catch (MissingResourceException e)
        {
            throw new ResourceException(e.getKey());
        }
    }
    public static ObservableResourceFactory getInstance(){
        if (instance == null)
            instance = new ObservableResourceFactory();
        return instance;
    }

    public ObjectProperty<ResourceBundle> resourcesProperty() {
        return resources;
    }
    public final ResourceBundle getResources() {
        return resourcesProperty().get();
    }
    public final void setResources(ResourceBundle resources) {
        resourcesProperty().set(resources);
    }

    public StringBinding getStringBinding(String key) {
        return new StringBinding() {
            { bind(resourcesProperty()); }
            @Override
            public String computeValue() {
                return getResources().getString(key);
            }
        };
    }
}