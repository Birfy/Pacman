package pane;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import object.Monster;
import main.Data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class SettingPane extends VBox implements Data {
    TextField tfPacmanX = new TextField();
    TextField tfPacmanY = new TextField();
    TextField tfMonsterX = new TextField();
    TextField tfMonsterY = new TextField();
    TextField tfMonsterType = new TextField();
    Label lbOK = new Label("OK");
    Label lbAdd = new Label("+");
    Label lbSubtract = new Label("-");
    HBox hBoxMonsters;
    ArrayList<Monster> monsters = new ArrayList<>();

    public SettingPane() {
        setAlignment(Pos.CENTER);
        setStyle("-fx-background-color: black");

        Label lbSetting = new Label("Setting");
        Label lbPacman = new Label("Pacman ");
        Label lbMonster = new Label("Monster");

        lbSetting.setFont(new Font("Calibri", 40));
        lbSetting.setTextFill(Color.GOLD);

        lbPacman.setFont(new Font("Calibri", 20));
        lbPacman.setTextFill(Color.GOLD);

        lbMonster.setFont(new Font("Calibri", 20));
        lbMonster.setTextFill(Color.GOLD);

        lbAdd.setFont(new Font("Calibri", 40));
        lbAdd.setTextFill(Color.GOLD);
        lbAdd.setOnMouseEntered(e -> lbAdd.setFont(new Font("Calibri", 45)));
        lbAdd.setOnMouseExited(e -> lbAdd.setFont(new Font("Calibri", 40)));

        lbSubtract.setFont(new Font("Calibri", 40));
        lbSubtract.setTextFill(Color.GOLD);
        lbSubtract.setOnMouseEntered(e -> lbSubtract.setFont(new Font("Calibri", 45)));
        lbSubtract.setOnMouseExited(e -> lbSubtract.setFont(new Font("Calibri", 40)));

        lbOK.setFont(new Font("Calibri", 30));
        lbOK.setTextFill(Color.GOLD);
        lbOK.setOnMouseEntered(e -> lbOK.setFont(new Font("Calibri", 35)));
        lbOK.setOnMouseExited(e -> lbOK.setFont(new Font("Calibri", 30)));

        tfPacmanX.setPrefColumnCount(10);
        tfPacmanX.setPrefWidth(30);
        tfPacmanX.setAlignment(Pos.CENTER);
        tfPacmanX.setFont(new Font("Calibre", 12));
        tfPacmanX.setStyle("-fx-background-color: lightgoldenrodyellow");
        tfPacmanX.setPromptText("X");

        tfPacmanY.setPrefColumnCount(10);
        tfPacmanY.setPrefWidth(30);
        tfPacmanY.setAlignment(Pos.CENTER);
        tfPacmanY.setFont(new Font("Calibre", 12));
        tfPacmanY.setStyle("-fx-background-color: lightgoldenrodyellow");
        tfPacmanY.setPromptText("Y");

        tfMonsterX.setPrefColumnCount(10);
        tfMonsterX.setPrefWidth(30);
        tfMonsterX.setAlignment(Pos.CENTER);
        tfMonsterX.setFont(new Font("Calibre", 12));
        tfMonsterX.setStyle("-fx-background-color: lightgoldenrodyellow");
        tfMonsterX.setPromptText("X");

        tfMonsterY.setPrefColumnCount(10);
        tfMonsterY.setPrefWidth(30);
        tfMonsterY.setAlignment(Pos.CENTER);
        tfMonsterY.setFont(new Font("Calibre", 12));
        tfMonsterY.setStyle("-fx-background-color: lightgoldenrodyellow");
        tfMonsterY.setPromptText("Y");

        tfMonsterType.setPrefColumnCount(10);
        tfMonsterType.setPrefWidth(60);
        tfMonsterType.setAlignment(Pos.CENTER);
        tfMonsterType.setFont(new Font("Calibre", 12));
        tfMonsterType.setStyle("-fx-background-color: lightgoldenrodyellow");
        tfMonsterType.setPromptText("Type");

        HBox hBoxPacman = new HBox(10, lbPacman, tfPacmanX, tfPacmanY);
        HBox hBoxMonster = new HBox(10, lbMonster, tfMonsterX, tfMonsterY, tfMonsterType);
        hBoxMonsters = new HBox(10, lbAdd, lbSubtract);
        hBoxPacman.setAlignment(Pos.CENTER);
        hBoxMonster.setAlignment(Pos.CENTER);
        hBoxMonsters.setAlignment(Pos.CENTER);

        try (Scanner input = new Scanner(new File("resource/setting.config"))) {
            tfPacmanX.setText(input.nextLine());
            tfPacmanY.setText(input.nextLine());
            int monsterNum = Integer.parseInt(input.nextLine());
            for (int i = 0; i < monsterNum; i++) {
                int x = Integer.parseInt(input.nextLine());
                int y = Integer.parseInt(input.nextLine());
                int type = Integer.parseInt(input.nextLine());
                monsters.add(new Monster(y, x, type, i));
                hBoxMonsters.getChildren().add(monsters.get(monsters.size() - 1).getImage());
                VBox vBoxPosition = new VBox();
                Label lbX = new Label(x + "");
                Label lbY = new Label(y + "");
                lbX.setTextFill(Color.GOLD);
                lbY.setTextFill(Color.GOLD);
                vBoxPosition.setAlignment(Pos.CENTER);
                vBoxPosition.getChildren().addAll(lbX, lbY);
                hBoxMonsters.getChildren().add(vBoxPosition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        lbAdd.setOnMouseClicked(e -> {
            try {
                int x = Integer.parseInt(tfMonsterX.getText());
                int y = Integer.parseInt(tfMonsterY.getText());
                int type = Integer.parseInt(tfMonsterType.getText());
                if (x >= 0 && x < ROW_SIZE && y >= 0 && y < COLUMN_SIZE && (type == 0 || type == 1)) {
                    monsters.add(new Monster(y, x, type, monsters.size()));
                    hBoxMonsters.getChildren().add(monsters.get(monsters.size() - 1).getImage());
                    VBox vBoxPosition = new VBox();
                    Label lbX = new Label(x + "");
                    Label lbY = new Label(y + "");
                    lbX.setTextFill(Color.GOLD);
                    lbY.setTextFill(Color.GOLD);
                    vBoxPosition.setAlignment(Pos.CENTER);
                    vBoxPosition.getChildren().addAll(lbX, lbY);
                    hBoxMonsters.getChildren().add(vBoxPosition);
                }
            } catch (NumberFormatException ex) {
                ex.printStackTrace();
            } finally {
                tfMonsterX.setText("");
                tfMonsterY.setText("");
                tfMonsterType.setText("");
            }
        });

        lbSubtract.setOnMouseClicked(e -> {
            if (hBoxMonsters.getChildren().size() > 2) {
                hBoxMonsters.getChildren().remove(hBoxMonsters.getChildren().size() - 1);
                hBoxMonsters.getChildren().remove(hBoxMonsters.getChildren().size() - 1);
                monsters.remove(monsters.size() - 1);
            }
        });

        setSpacing(15);
        getChildren().addAll(lbSetting, hBoxPacman, hBoxMonster, hBoxMonsters, lbOK);
    }

    public Label getLbOK() {
        return lbOK;
    }

    public TextField getTfPacmanX() {
        return tfPacmanX;
    }

    public TextField getTfPacmanY() {
        return tfPacmanY;
    }

    public void saveSetting() {
        ArrayList<String> stringInput = new ArrayList<>();
        try (Scanner input = new Scanner(new File("resource/setting.config"))) {
            while(input.hasNextLine())
                stringInput.add(input.nextLine());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (int i = 0; i < stringInput.size(); i++) {
            if (stringInput.get(i).length() > 3) {
                for (int j = 0; j < i; j++)
                    stringInput.remove(0);
                break;
            }
        }

        try (PrintWriter output = new PrintWriter(new File("resource/setting.config"))) {
            output.println(tfPacmanX.getText());
            output.println(tfPacmanY.getText());
            output.println(monsters.size());
            for (Monster monster : monsters) {
                output.println(monster.getX());
                output.println(monster.getY());
                output.println(monster.getType());
            }
            output.println(3);
            output.println(0);
            stringInput.forEach(output::println);
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
