package com.ds.utilitiesapp.controllers;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.records.Record;
import com.ds.utilitiesapp.utils.Utils;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import static com.ds.utilitiesapp.Constants.WINDOW_HEIGHT;
import static com.ds.utilitiesapp.Constants.WINDOW_WIDTH;
import static com.ds.utilitiesapp.utils.Utils.addActionToNode;

public class MainController {
    @FXML
    private ImageView closeButtonImageView;

    @FXML
    private HBox headerHbox;

    @FXML
    private VBox mainVbox;

    @FXML
    private ImageView minimizeButtonImageView;

    @FXML
    private Label categoryLabel;

    @FXML
    private MenuButton categoryMenuButton;

    @FXML
    private MenuBar menuBar;

    @FXML
    private Label titleLabel;

    private double windowX, windowY;

    @FXML
    void initialize() {
        initHeader();
        initTitle();
        initBackground();

        MainPage mainPage = new MainPage(categoryLabel, menuBar, categoryMenuButton, mainVbox);
        mainPage.init();
    }

    private void initTitle() {
        try {
            titleLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 14d));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initBackground() {
        try {
            BackgroundImage backgroundImage = new BackgroundImage(Utils.getImage("images/background.png"),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.ROUND, BackgroundPosition.DEFAULT, new BackgroundSize(WINDOW_WIDTH, WINDOW_HEIGHT, false, false, true, true));
            mainVbox.setBackground(new Background(backgroundImage));
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initHeader() {
        try {
            titleLabel.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 16d));

            initControlButtons();
            initHeaderDrug();
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initHeaderDrug() {
        try {
            headerHbox.setOnMousePressed(event -> {
                windowX = event.getSceneX();
                windowY = event.getSceneY();
            });

            headerHbox.setOnMouseDragged(event -> {
                headerHbox.getScene().getWindow().setX(event.getScreenX() - windowX);
                headerHbox.getScene().getWindow().setY(event.getScreenY() - windowY);
            });
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    private void initControlButtons() {
        addActionToNode(closeButtonImageView, this::close);
        addActionToNode(minimizeButtonImageView, this::minimize);
    }

    private Stage getStage(){
        return (Stage) closeButtonImageView.getScene().getWindow();
    }

    private void minimize(){
        getStage().setIconified(true);
    }

    private void close(){
        try {
            getStage().close();
            Platform.exit();
            System.exit(0);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

}
