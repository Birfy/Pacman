package pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Data;

import java.nio.file.Paths;

public class DiePane extends VBox implements Data {
    private ImageView imagePlayAgain;
    private Label lbBack = new Label("Menu");

    public DiePane() {
        ImageView imageDie = new ImageView(new Image(Paths.get("resource/game over.jpg").toUri().toString()));
        imagePlayAgain = new ImageView(new Image(Paths.get("resource/play again.jpg").toUri().toString()));

        imageDie.setFitHeight(350);
        imageDie.setFitWidth(WIDTH_OF_PANE);
        imagePlayAgain.setFitHeight(60);
        imagePlayAgain.setFitWidth(300);
        lbBack.setFont(new Font("Calibri", 40));
        lbBack.setTextFill(Color.GOLD);
        lbBack.setOnMouseEntered(e -> lbBack.setFont(new Font("Calibri", 45)));
        lbBack.setOnMouseExited(e -> lbBack.setFont(new Font("Calibri", 40)));

        setStyle("-fx-background-color: black");
        getChildren().addAll(imageDie, imagePlayAgain, lbBack);
        setAlignment(Pos.CENTER);
    }

    public ImageView getImagePlayAgain() {
        return imagePlayAgain;
    }

    public Label getLbBack() {
        return lbBack;
    }
}
