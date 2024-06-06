package com.ds.utilitiesapp.extendsNodes;

import com.ds.utilitiesapp.Constants;
import com.ds.utilitiesapp.Main;
import com.ds.utilitiesapp.dialogs.ErrorDialog;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public abstract class SettingsOption extends HBox{
    private final double width;
    private final double height;
    public static final double DEFAULT_WIDTH = 308d;
    public static final double DEFAULT_HEIGHT = 49d;
    private Label label;

    public SettingsOption(double width, double height) {
        this.width = width;
        this.height = height;

        init();
    }

    public void init() {
        try {
            setPrefSize(width, height);
            setAlignment(Pos.CENTER_LEFT);
            getStyleClass().add("text-field-parent");
            setEffect(new DropShadow());
            setCursor(Cursor.HAND);
        }catch (Exception e){
            ErrorDialog.show(e);
        }
    }

    public Label createLabel(String text, Color color, Pos pos) {
        try{
            label = new Label(text);
            label.setFont(Font.loadFont(Main.class.getResourceAsStream(Constants.INTER_BOLD_ITALIC_FONT_INPUT_PATH), 16d));
            label.setTextFill(color);
            HBox.setMargin(label, new Insets(10d));

            HBox labelHbox = new HBox();
            HBox.setHgrow(labelHbox, Priority.ALWAYS);
            labelHbox.setAlignment(pos);
            labelHbox.getChildren().add(label);

            getChildren().add(labelHbox);

            return label;
        }catch (Exception e){
            ErrorDialog.show(e);
        }

        return null;
    }

    public Label getLabel() {
        return label;
    }

    public void add(Node node){
        getChildren().add(node);
    }
}
