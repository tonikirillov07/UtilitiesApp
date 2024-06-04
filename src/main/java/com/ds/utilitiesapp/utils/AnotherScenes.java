package com.ds.utilitiesapp.utils;

import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.jetbrains.annotations.Nullable;

public final class AnotherScenes {
    public static @Nullable Object goToAnotherScene(String path, String name){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource(path));
            loader.load();

            Parent root = loader.getRoot();
            Stage new_stage = new Stage();
            new_stage.setScene(new Scene(root));
            new_stage.setTitle(name);
            new_stage.setResizable(true);

            new_stage.show();

            return loader.getController();
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }
}
