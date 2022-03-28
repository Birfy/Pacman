package pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class InputNamePane extends VBox {
    private TextField txName = new TextField();
    private Label lbOK = new Label("OK");

    public InputNamePane(String message) {
        Label lbMessage = new Label(message);
        setStyle("-fx-background-color: black");
        setAlignment(Pos.CENTER);

        lbMessage.setFont(new Font("Calibri", 20));
        lbMessage.setTextFill(Color.GOLD);
        txName.setPrefColumnCount(10);
        txName.setPrefHeight(30);
        txName.setAlignment(Pos.CENTER);
        txName.setFont(new Font("Calibre", 20));
        txName.setStyle("-fx-background-color: lightgoldenrodyellow");
        lbOK.setFont(new Font("Calibri", 30));
        lbOK.setTextFill(Color.GOLD);
        lbOK.setOnMouseEntered(e -> lbOK.setFont(new Font("Calibri", 35)));
        lbOK.setOnMouseExited(e -> lbOK.setFont(new Font("Calibri", 30)));

        getChildren().addAll(lbMessage, txName, lbOK);
    }

    public TextField getTxName() {
        return txName;
    }

    public Label getLbOK() {
        return lbOK;
    }
}
