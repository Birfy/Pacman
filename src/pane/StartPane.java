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

public class StartPane extends VBox implements Data {
    private Label lbStart = new Label("START");
    private Label lbRead = new Label("READ");
    private Label lbRanking = new Label("RANKING");
    private Label lbSetting = new Label("SETTING");

    public StartPane() {
        ImageView imageStart = new ImageView(new Image(Paths.get("resource/labelstart.png").toUri().toString()));
        ImageView imageGameShow = new ImageView(new Image(Paths.get("resource/gameshow.gif").toUri().toString()));

        imageStart.setFitWidth(400);
        imageStart.setFitHeight(120);
        imageGameShow.setFitWidth(WIDTH_OF_PANE);
        imageGameShow.setFitHeight(80);

        lbStart.setTextFill(Color.GOLD);
        lbStart.setFont(new Font("Calibri", 30));
        lbStart.setOnMouseEntered(e -> lbStart.setFont(new Font("Calibri", 35)));
        lbStart.setOnMouseExited(e -> lbStart.setFont(new Font("Calibri", 30)));

        lbRead.setTextFill(Color.GOLD);
        lbRead.setFont(new Font("Calibri", 30));
        lbRead.setOnMouseEntered(e -> lbRead.setFont(new Font("Calibri", 35)));
        lbRead.setOnMouseExited(e -> lbRead.setFont(new Font("Calibri", 30)));

        lbRanking.setTextFill(Color.GOLD);
        lbRanking.setFont(new Font("Calibri", 30));
        lbRanking.setOnMouseEntered(e -> lbRanking.setFont(new Font("Calibri", 35)));
        lbRanking.setOnMouseExited(e -> lbRanking.setFont(new Font("Calibri", 30)));

        lbSetting.setTextFill(Color.GOLD);
        lbSetting.setFont(new Font("Calibri", 30));
        lbSetting.setOnMouseEntered(e -> lbSetting.setFont(new Font("Calibri", 35)));
        lbSetting.setOnMouseExited(e -> lbSetting.setFont(new Font("Calibri", 30)));

        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");

        getChildren().addAll(imageStart, imageGameShow, lbStart, lbRead, lbRanking, lbSetting);
    }

    public Label getLbStart() {
        return lbStart;
    }

    public Label getLbRead() {
        return lbRead;
    }

    public Label getLbRanking() {
        return lbRanking;
    }

    public Label getLbSetting() {
        return lbSetting;
    }
}
