package com.ds.utilitiesapp.controllers;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.MainPage;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import com.ds.utilitiesapp.extendsNodes.SettingsOption;
import com.ds.utilitiesapp.extendsNodes.SettingsOptionButton;
import com.ds.utilitiesapp.utils.Utils;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.Objects;

import static com.ds.utilitiesapp.extendsNodes.SettingsOptionButton.DEFAULT_IMAGE_FIT_HEIGHT;
import static com.ds.utilitiesapp.extendsNodes.SettingsOptionButton.DEFAULT_IMAGE_FIT_WIDTH;

public class ContactsController {
    @FXML
    private Label contactsLabel;

    @FXML
    private VBox mainVbox;

    @FXML
    void initialize() {
        contactsLabel.setTextFill(Color.WHITE);
        contactsLabel.setFont(Font.loadFont(MainPage.class.getResourceAsStream(Constants.INTER_EXTRA_BOLD_FONT_INPUT_PATH), 32d));

        Utils.applyBackground(mainVbox, "images/help_page_background.png", 544d, 656d);

        initTiles();
    }

    private void initTiles() {
        try {
            SettingsOptionButton settingsOptionButtonTelegram = new SettingsOptionButton(SettingsOption.DEFAULT_WIDTH, SettingsOption.DEFAULT_HEIGHT, "Telegram", Utils.getImage("images/telegram.png"), DEFAULT_IMAGE_FIT_WIDTH, DEFAULT_IMAGE_FIT_HEIGHT);
            VBox.setMargin(settingsOptionButtonTelegram, new Insets(50d, 50d, 0d, 50d));

            SettingsOptionButton settingsOptionButtonMail = new SettingsOptionButton(SettingsOption.DEFAULT_WIDTH, SettingsOption.DEFAULT_HEIGHT, "Mail", Utils.getImage("images/mail.png"), DEFAULT_IMAGE_FIT_WIDTH, DEFAULT_IMAGE_FIT_HEIGHT);
            VBox.setMargin(settingsOptionButtonMail, new Insets(20d, 50d, 0d, 50d));

            SettingsOptionButton settingsOptionButtonPhone = new SettingsOptionButton(SettingsOption.DEFAULT_WIDTH, SettingsOption.DEFAULT_HEIGHT, "Телефон", Utils.getImage("images/phone.png"), DEFAULT_IMAGE_FIT_WIDTH, DEFAULT_IMAGE_FIT_HEIGHT);
            VBox.setMargin(settingsOptionButtonPhone, new Insets(20d, 50d, 0d, 50d));

            mainVbox.getChildren().addAll(settingsOptionButtonTelegram, settingsOptionButtonMail, settingsOptionButtonPhone);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

}
