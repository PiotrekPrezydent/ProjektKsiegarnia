package com.example.projektksiegarnia;

import constants.Scenes;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * glowna klasa uruchomieniowa aplikacji
 * dziedziczy po klasie {@link Application} i definiuje metodę startowa dla JavaFx
 */
public class Boot extends Application {
    public static void main(String[] args) {
        //launch is calling method start
        launch();
    }

    /**
     * metoda Startowa aplikacji
     * @param stage główna scena aplikacji
     */
    @Override
    public void start(Stage stage) {
        SceneManager.PrimaryStage = stage;
        SceneManager.PrimaryStage.setTitle("ProjektKsiegarnia");

        //this have to be called here because after exiting start method we're losing all references to stage
        SceneManager.LoadScene(Scenes.Boot);
    }
}