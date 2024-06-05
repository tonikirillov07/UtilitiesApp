package com.ds.utilitiesapp.utils;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.dialogs.InfoDialog;
import com.ds.utilitiesapp.extendsNodes.ExtendedTextField;
import com.ds.utilitiesapp.utils.actionListeners.IOnAction;
import com.ds.utilitiesapp.utils.settings.SettingsManager;
import javafx.scene.Node;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class Utils {
    @Contract("_ -> new")
    public static @Nullable Image getImage(String path){
        try {
            return new Image(Objects.requireNonNull(Main.class.getResourceAsStream(path)));
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public static void addActionToNode(@NotNull Node node, IOnAction onAction){
        try {
            node.setOnMouseClicked(mouseEvent -> {
                if (mouseEvent.getButton() == MouseButton.PRIMARY)
                    onAction.onAction();
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public static void defaultCategoryMenuItemsAction(@NotNull MenuItem menuItem, @NotNull MenuButton menuButton){
        menuButton.setText(menuItem.getText());
    }

    public static @NotNull List<ExtendedTextField> getEmptyFieldsFromArray(ExtendedTextField[] textFields){
        List<ExtendedTextField> textInputControlList = new ArrayList<>();

        Arrays.stream(textFields).toList().forEach(textInputControl -> {
            if (textInputControl == null)
                return;

            if (textInputControl.getText().trim().isEmpty())
                textInputControlList.add(textInputControl);
        });

        return textInputControlList;
    }

    public static void saveCurrentFile(String pathToSave){
        try {
            File file = new File(pathToSave);

            if(file.exists())
                file.delete();

            File currentDatabase = new File(Objects.requireNonNull(SettingsManager.getValue(Constants.CURRENT_DATABASE_FILE_KEY)));
            Files.copy(currentDatabase.toPath(), file.getParentFile().toPath().resolve(file.getName()));

            InfoDialog.show("Ваш файл успешно сохранен в " + file.getAbsolutePath());
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private static @NotNull FileChooser createFileChooser(String title, List<FileChooser.ExtensionFilter> extensionFilterList){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle(title);
        fileChooser.getExtensionFilters().addAll(extensionFilterList);

        return fileChooser;
    }

    public static @Nullable File openFileDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showOpenDialog(stage);
    }

    public static @Nullable File openSaveDialog(String title, Stage stage, List<FileChooser.ExtensionFilter> extensionFilterList){
        return createFileChooser(title, extensionFilterList).showSaveDialog(stage);
    }

    public static double convertRubToDollars(double rubsValue){
        return rubsValue * 0.011d;
    }
}
