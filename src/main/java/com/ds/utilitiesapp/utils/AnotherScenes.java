package com.ds.utilitiesapp.utils;

import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.utils.actionListeners.IOnAction;
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
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
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
