package pane;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Data;

import java.nio.file.Paths;

public class StatusPane extends HBox implements Data {
    private int life;
    private int scores;
    private ImageView lifeImage;
    private Label scoresLabel;
    private Label lbBack;
    private Label lbSave;

    public StatusPane(int life, int scores) {
        this.life = life;
        this.scores = scores;
        lifeImage = new ImageView(new Image(Paths.get("resource/life" + life + ".jpeg").toUri().toString()));

        scoresLabel = new Label("SCORES: " + scores);
        scoresLabel.setPrefHeight(WIDTH_OF_STATUS_LABEL);
        scoresLabel.setFont(new Font("Calibri", 20));
        scoresLabel.setTextFill(Color.YELLOW);
        scoresLabel.setAlignment(Pos.CENTER);
        lbBack = new Label("BACK");
        setMargin(lbBack, new Insets(0, 15, 0, 15));
        lbBack.setFont(new Font("Calibri", 20));
        lbBack.setTextFill(Color.YELLOW);
        lbBack.setAlignment(Pos.CENTER);
        lbBack.setOnMouseEntered(e -> lbBack.setFont(new Font("Calibri", 25)));
        lbBack.setOnMouseExited(e -> lbBack.setFont(new Font("Calibri", 20)));
        lifeImage.setFitWidth(LENGTH_OF_SHOWLIFE_LABEL);
        lifeImage.setFitHeight(WIDTH_OF_STATUS_LABEL);
        lbSave = new Label("SAVE");
        setMargin(lbBack, new Insets(0, 15, 0, 15));
        lbSave.setFont(new Font("Calibri", 20));
        lbSave.setTextFill(Color.YELLOW);
        lbSave.setAlignment(Pos.CENTER);
        lbSave.setOnMouseEntered(e -> lbSave.setFont(new Font("Calibri", 25)));
        lbSave.setOnMouseExited(e -> lbSave.setFont(new Font("Calibri", 20)));
        getChildren().addAll(lifeImage, scoresLabel, lbBack, lbSave);
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");
    }

    public int getLife() {
        return life;
    }

    public int getScores() {
        return scores;
    }

    public void addScore(int score) {
        scores += score;
        scoresLabel.setText("SCORES: " + scores);
    }

    public void minusLife() {
        life--;
        if (life > 0)
            lifeImage.setImage(new Image(Paths.get("resource/life" + life + ".jpeg").toUri().toString()));
    }

    public Label getLbBack() {
        return lbBack;
    }

    public Label getLbSave() {
        return lbSave;
    }
}