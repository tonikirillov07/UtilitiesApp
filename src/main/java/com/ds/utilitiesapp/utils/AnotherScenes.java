package com.ds.utilitiesapp.utils;

import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

public final class AnotherScenes {
    public static @Nullable Object goToAnotherScene(String path, String name){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(Main.class.getResource(path));
            fxmlLoader.load();

            Parent root = fxmlLoader.getRoot();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(name);
            stage.setResizable(true);

            stage.show();

            return fxmlLoader.getController();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
