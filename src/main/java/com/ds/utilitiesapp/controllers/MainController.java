package com.ds.utilitiesapp.controllers;

import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.utils.Utils;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.layout.*;

import static com.ds.utilitiesapp.Constants.WINDOW_HEIGHT;
import static com.ds.utilitiesapp.Constants.WINDOW_WIDTH;

public class MainController {
    @FXML
    private VBox mainVbox;

    @FXML
    private Label categoryLabel;

    @FXML
    private MenuButton categoryMenuButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    void initialize() {
        initBackground();

        MainPage mainPage = new MainPage(categoryLabel, menuBar, categoryMenuButton, mainVbox);
        mainPage.init();
    }

    private void initBackground() {
        try {
            BackgroundImage backgroundImage = new BackgroundImage(Utils.getImage("images/background.png"),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, new BackgroundSize(WINDOW_WIDTH, WINDOW_HEIGHT, false, false, true, true));
            mainVbox.setBackground(new Background(backgroundImage));
        } catch (Exception e) {
            ErrorDialog.show(e);
        }
    }
}
