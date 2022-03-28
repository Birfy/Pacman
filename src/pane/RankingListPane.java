package pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import main.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class RankingListPane extends VBox implements Data {
    private ArrayList<String> names = new ArrayList<>();
    private ArrayList<Integer> scores = new ArrayList<>();
    private Label lbBack = new Label("Menu");

    public RankingListPane() {
        getScoresFromFile();

        Label lbRankingList = new Label("Ranking List");
        Label[] lbNames = new Label[5];
        for (int i = 0; i < names.size(); i++) {
            lbNames[i] = new Label(names.get(i) + " : " + scores.get(i));
            lbNames[i].setFont(new Font("Calibri", 30));
            lbNames[i].setTextFill(Color.GOLD);
        }

        lbRankingList.setFont(new Font("Calibri", 60));
        lbRankingList.setTextFill(Color.GOLD);

        lbBack.setFont(new Font("Calibri", 30));
        lbBack.setTextFill(Color.GOLD);
        lbBack.setOnMouseEntered(e -> lbBack.setFont(new Font("Calibri", 35)));
        lbBack.setOnMouseExited(e -> lbBack.setFont(new Font("Calibri", 30)));

        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");
        getChildren().add(lbRankingList);
        for (int i = 0; i < names.size(); i++)
            getChildren().add(lbNames[i]);
        getChildren().add(lbBack);
    }

    public void writeRecord(String name, int score) {
        PrintWriter output = null;
        try {
            output = new PrintWriter(new File("resource/rankinglist.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert output != null;
        for (int i = 0; i < names.size(); i++) {
            if (score >= scores.get(i)) {
                names.add(i, name);
                scores.add(i, score);
                if (names.size() > 5) {
                    names.remove(names.size() - 1);
                    scores.remove(scores.size() - 1);
                }
                break;
            }
        }

        for (int i = 0; i < names.size(); i++) {
            output.println(names.get(i));
            output.println(scores.get(i));
        }
        output.close();
    }

    private void getScoresFromFile() {
        Scanner input = null;
        try {
            input = new Scanner(new File("resource/rankinglist.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        assert input != null;
        for (int i = 0; i < 5; i++) {
            if (input.hasNextLine()) {
                names.add(input.nextLine());
                scores.add(Integer.parseInt(input.nextLine()));
            } else
                break;
        }
    }

    public ArrayList<Integer> getScores() {
        return scores;
    }

    public Label getLbBack() {
        return lbBack;
    }
}