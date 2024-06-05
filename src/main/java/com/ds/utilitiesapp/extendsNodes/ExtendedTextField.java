package com.ds.utilitiesapp.extendsNodes;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.utils.InputTypes;
import com.ds.utilitiesapp.utils.actionListeners.IOnAction;
import com.ds.utilitiesapp.utils.actionListeners.IOnTextTyping;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ExtendedTextField extends HBox {
    private final double width, height;
    private final String placeholder;
    private String defaultValue;
    private TextInputControl textField;
    private final Image fieldIcon;
    public static final double DEFAULT_WIDTH = 315d;
    public static final double DEFAULT_HEIGHT = 37d;
    private IOnTextTyping onTextTyping;

    public ExtendedTextField(double width, double height, String placeholder, Image fieldIcon) {
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.fieldIcon = fieldIcon;

        init();
    }

    public ExtendedTextField(double width, double height, String placeholder, String defaultValue, Image fieldIcon) {
        this.width = width;
        this.height = height;
        this.placeholder = placeholder;
        this.defaultValue = defaultValue;
        this.fieldIcon = fieldIcon;

        init();
    }

    public void setOnTextTyping(IOnTextTyping onTextTyping) {
        this.onTextTyping = onTextTyping;
    }

    public void setInputType(InputTypes inputType){
        if(inputType == InputTypes.NUMERIC) {
            getTextField().setOnKeyTyped(keyEvent -> {
                if(!getCharactersList().stream().allMatch(Character::isDigit)){
                    removeAllButDigits();
                }

                if(onTextTyping != null)
                    onTextTyping.onTextTyping(getText());
            });
        }
    }

    private void removeAllButDigits() {
        List<Character> characterList = getCharactersList();
        characterList.removeIf(predicate -> !Character.isDigit(predicate));

        StringBuilder stringBuilder = new StringBuilder();
        characterList.forEach(stringBuilder::append);

        getTextField().setText(stringBuilder.toString());
    }

    public void setError(){
        addShadowEffect(Color.RED);

        TranslateTransition translateTransition = new TranslateTransition(Duration.millis(100), this);
        translateTransition.setFromX(0);
        translateTransition.setByX(25);
        translateTransition.setCycleCount(2);
        translateTransition.setAutoReverse(true);
        translateTransition.setOnFinished(actionEvent -> addShadowEffect(Color.BLACK));
        translateTransition.play();
    }

    public void addOnEnterKeyPressed(IOnAction onAction){
        setOnKeyPressed(keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER)
                onAction.onAction();
        });
    }

    private @NotNull List<Character> getCharactersList(){
        List<Character> characterList = new ArrayList<>();
        for (char character : getText().toCharArray()) {
            characterList.add(character);
        }

        return characterList;
    }

    private void addShadowEffect(Color color){
        DropShadow dropShadow = new DropShadow();
        dropShadow.setColor(color);
        setEffect(dropShadow);
    }

    private void init() {
        setPrefSize(width, height);
        setAlignment(Pos.CENTER);
        getStyleClass().add("text-field-parent");
        addShadowEffect(Color.BLACK);

        addTextField();
        addFieldIcon();
    }

    private void addFieldIcon() {
        ImageView imageView = new ImageView();
        imageView.setImage(fieldIcon);
        imageView.setFitWidth(32);
        imageView.setFitHeight(32);
        HBox.setMargin(imageView, new Insets(10, 10, 10, 10));

        getChildren().add(imageView);
    }

    private void addTextField() {
        textField = new TextField();
        textField.setPromptText(placeholder);
        textField.setPrefHeight(height / 1.16d);
        textField.setFocusTraversable(false);
        textField.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 16));
        HBox.setHgrow(textField, Priority.ALWAYS);

        if(defaultValue != null)
            if (!defaultValue.isEmpty())
                textField.setText(defaultValue);

        textField.setOnKeyTyped(keyEvent -> {
            if(onTextTyping != null)
                onTextTyping.onTextTyping(getText());
        });

        getChildren().add(textField);
    }

    public void setText(String text){
        getTextField().setText(text);
    }

    public boolean isEmpty(){
        return getText().isEmpty();
    }

    public String getText(){
        return textField.getText().trim();
    }

    public TextInputControl getTextField() {
        return textField;
    }
}
