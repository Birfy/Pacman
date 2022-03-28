package pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Data;

public class WinPane extends VBox implements Data {
    private Label lbPlayAgain = new Label("PlayAgain");
    private Label lbBack = new Label("Menu");

    public WinPane() {
        Label lbWin = new Label("YOU WIN!");
        lbWin.setFont(new Font("Calibri", 60));
        lbWin.setTextFill(Color.GOLD);
        lbPlayAgain.setFont(new Font("Calibri", 30));
        lbPlayAgain.setTextFill(Color.GOLD);
        lbPlayAgain.setOnMouseEntered(e -> lbPlayAgain.setFont(new Font("Calibri", 35)));
        lbPlayAgain.setOnMouseExited(e -> lbPlayAgain.setFont(new Font("Calibri", 30)));
        lbBack.setFont(new Font("Calibri", 30));
        lbBack.setTextFill(Color.GOLD);
        lbBack.setOnMouseEntered(e -> lbBack.setFont(new Font("Calibri", 35)));
        lbBack.setOnMouseExited(e -> lbBack.setFont(new Font("Calibri", 30)));

        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");
        getChildren().addAll(lbWin, lbPlayAgain, lbBack);
    }

    public Label getLbPlayAgain() {
        return lbPlayAgain;
    }

    public Label getLbBack() {
        return lbBack;
    }
}