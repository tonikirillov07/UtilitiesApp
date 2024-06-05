package com.ds.utilitiesapp;

import com.ds.utilitiesapp.utils.Utils;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Objects;

import static com.ds.utilitiesapp.Constants.*;

public class Main extends Application {
    @Override
    public void start(@NotNull Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("main-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), WINDOW_WIDTH, WINDOW_HEIGHT);
            scene.setFill(Color.TRANSPARENT);
            scene.getStylesheets().add(Objects.requireNonNull(getClass().getResource("styles.css")).toExternalForm());
            stage.setTitle(WINDOW_TITLE);
            stage.getIcons().add(Utils.getImage("images/icon/window_icon.png"));
            stage.setScene(scene);
            stage.setOnCloseRequest(windowEvent -> close(stage));
            stage.show();
        }catch (Exception e){
            JOptionPane.showMessageDialog(null, e.toString(), "An exception has occurred", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void close(@NotNull Stage stage){
        stage.close();
        Platform.exit();
        System.exit(0);
    }

    public static void main(String[] args) {
        launch();
    }
}