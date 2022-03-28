package main;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import object.*;
import pane.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.nio.file.Paths;
import java.util.Scanner;

public class Main extends Application implements Data {
    protected static MapObject[][] map = new MapObject[COLUMN_SIZE][ROW_SIZE];
    protected static Pacman pacman;
    protected static Monster[] monster;
    protected static Pane mapPane = new Pane();
    protected static StatusPane showStatus;
    protected static BorderPane gamePane;
    protected static Stage primaryStage;
    protected static int pacmanX;
    protected static int pacmanY;
    protected static int[] monsterType;
    protected static int[] monsterX;
    protected static int[] monsterY;
    protected static int monsterNum;

    public static void initializeMap(boolean readSave) {
        if (readSave) {
            InputNamePane namePane = new InputNamePane("Please input your name");
            Stage stage = new Stage();
            Scene scene = new Scene(namePane, 200, 200);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.setTitle("Input your name");
            stage.getIcons().add(new Image(Paths.get("resource/cherry.jpg").toUri().toString()));
            stage.show();

            stage.setOnCloseRequest(e -> showStartPane());
            final String[] name = new String[1];
            namePane.getLbOK().setOnMouseClicked(e -> {
                name[0] = "save/" + namePane.getTxName().getText().trim() + ".txt";
                stage.close();
                readSetting(name[0]);
                playTheGame();
            });
        } else {
            readSetting("resource/setting.config");
            playTheGame();
        }
    }

    public static void readSetting(String fileName) {
        try (Scanner fileReader = new Scanner(new File(fileName))) {
            pacmanX = Integer.parseInt(fileReader.nextLine());
            pacmanY = Integer.parseInt(fileReader.nextLine());
            pacman = new Pacman(pacmanY, pacmanX);
            monsterNum = Integer.parseInt(fileReader.nextLine());
            monster = new Monster[monsterNum];
            monsterX = new int[monsterNum];
            monsterY = new int[monsterNum];
            monsterType = new int[monsterNum];
            for (int i = 0; i < monsterNum; i++) {
                monsterX[i] = Integer.parseInt(fileReader.nextLine());
                monsterY[i] = Integer.parseInt(fileReader.nextLine());
                monsterType[i] = Integer.parseInt(fileReader.nextLine());
                monster[i] = new Monster(monsterY[i], monsterX[i], monsterType[i], i);
            }

            showStatus = new StatusPane(Integer.parseInt(fileReader.nextLine()), Integer.parseInt(fileReader.nextLine()));
            for (int i = 0; i < COLUMN_SIZE; i++) {
                String inputLine = fileReader.nextLine();
                for (int j = 0; j < ROW_SIZE; j++)
                    if ((inputLine.charAt(j) >= '0' && inputLine.charAt(j) <= '9'))
                        map[i][j] = new Wall(i, j, inputLine.charAt(j) - '0');
                    else
                        switch (inputLine.charAt(j)) {
                            case '.':
                                map[i][j] = new Bean(i, j, 1);
                                break;
                            case 'a':
                                map[i][j] = new Bean(i, j, 2);
                                break;
                            case 'n':
                                map[i][j] = new Bean(i, j, 0);
                                break;
                        }
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public static boolean isWin() {
        for (int i = 0; i < COLUMN_SIZE; i++)
            for (int j = 0; j < ROW_SIZE; j++)
                if (map[i][j] instanceof Bean && !(map[i][j].getType() == 0))
                    return false;
        return true;
    }

    public static void recoverMap() {
        monster[0].getImage().requestFocus();
        for (int i = 0; i < monsterNum; i++) {
            monster[i].getTimeline().stop();
            monster[i].getImage().setVisible(false);
            monster[i].setIsDead(false);
        }
        if (pacman.getMoveTimeline() != null)
            pacman.getMoveTimeline().stop();
        if (pacman.getTimeline() != null)
            pacman.getTimeline().stop();
        pacman.setImage(-1);

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000), e -> {
            pacman.setX(pacmanX);
            pacman.setY(pacmanY);
            pacman.getImage().setX(pacmanX * WIDTH_OF_NODES);
            pacman.getImage().setY(pacmanY * WIDTH_OF_NODES);
            pacman.setImage(0);
            showStatus.minusLife();

            for (int i = 0; i < monsterNum; i++) {
                monster[i].setX(monsterX[i]);
                monster[i].setY(monsterY[i]);
                monster[i].getImage().setX(monsterX[i] * WIDTH_OF_NODES);
                monster[i].getImage().setY(monsterY[i] * WIDTH_OF_NODES);
                monster[i].getImage().setVisible(true);
                monster[i].moveMonster();
            }
            pacman.setIsMove(-1);
            pacman.getImage().requestFocus();
        }));
        timeline.setCycleCount(1);
        timeline.play();
    }

    public static void showStartPane() {
        StartPane startPane = new StartPane();
        Scene scene = new Scene(startPane, WIDTH_OF_PANE, HEIGHT_OF_PANE);
        primaryStage.setScene(scene);
        startPane.getLbStart().setOnMouseClicked(e ->initializeMap(false));
        startPane.getLbRead().setOnMouseClicked(e -> initializeMap(true));
        startPane.getLbRanking().setOnMouseClicked(e -> showRankingList());
        startPane.getLbSetting().setOnMouseClicked(e -> showSettingPane());
    }

    public static void playTheGame() {
        for (int i = 0; i < COLUMN_SIZE; i++)
            for (int j = 0; j < ROW_SIZE; j++)
                mapPane.getChildren().add(map[i][j].getImage());

        for (int i = 0; i < monsterNum; i++)
            mapPane.getChildren().add(monster[i].getImage());

        mapPane.getChildren().addAll(pacman.getImage());

        for (int i = 0; i < monsterNum; i++)
            monster[i].moveMonster();

        pacman.getImage().setOnKeyPressed(e -> {
            int direction = -1;
            switch (e.getCode()) {
                case UP:
                    direction = 0;
                    break;
                case RIGHT:
                    direction = 1;
                    break;
                case DOWN:
                    direction = 2;
                    break;
                case LEFT:
                    direction = 3;
                    break;
            }
            if (pacman.getIsMove() != direction)
                pacman.moveUntilWall(direction);
        });

        gamePane = new BorderPane();
        gamePane.setCenter(mapPane);
        gamePane.setBottom(showStatus);
        showStatus.getLbBack().setOnMouseClicked(e -> {
            stopMoving();
            showStartPane();
        });
        showStatus.getLbSave().setOnMouseClicked(e -> {
            if (pacman.getBigStrengthTime() != null)
                pacman.getBigStrengthTime().pause();
            if (pacman.getTimeline() != null)
                pacman.getTimeline().pause();
            for (int i = 0; i < monsterNum; i++) {
                if (monster[i].getTimeline() != null)
                    monster[i].getTimeline().pause();
                if (monster[i].getTimelineDie() != null)
                    monster[i].getTimelineDie().pause();
            }
            save();
        });

        Scene scene = new Scene(gamePane, WIDTH_OF_PANE, HEIGHT_OF_PANE);
        primaryStage.setScene(scene);
        pacman.getImage().requestFocus();
    }

    public static void save() {
        InputNamePane namePane = new InputNamePane("Please input your name");
        Stage stage = new Stage();
        Scene scene = new Scene(namePane, 200, 200);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Save your game");
        stage.getIcons().add(new Image(Paths.get("resource/cherry.jpg").toUri().toString()));
        stage.show();

        stage.setOnCloseRequest(e -> {
            if (pacman.getBigStrengthTime() != null)
                pacman.getBigStrengthTime().play();
            if (pacman.getTimeline() != null)
                pacman.getTimeline().play();
            for (int i = 0; i < monsterNum; i++) {
                if (monster[i].getTimeline() != null && !monster[i].getIsDead())
                    monster[i].getTimeline().play();
                if (monster[i].getTimelineDie() != null)
                    monster[i].getTimelineDie().play();
            }
        });

        final String[] name = new String[1];
        namePane.getLbOK().setOnMouseClicked(e -> {
            name[0] = namePane.getTxName().getText().trim();
            stage.close();
            try (PrintWriter saveWriter = new PrintWriter(new File("save/" + name[0] + ".txt"))) {
                saveWriter.println(pacmanX);
                saveWriter.println(pacmanY);
                saveWriter.println(monsterNum);
                for (int i = 0; i < monsterNum; i++) {
                    saveWriter.println(monsterX[i]);
                    saveWriter.println(monsterY[i]);
                    saveWriter.println(monsterType[i]);
                }
                saveWriter.println(showStatus.getLife());
                saveWriter.println(showStatus.getScores());
                for (int i = 0; i < COLUMN_SIZE; i++) {
                    for (int j = 0; j < ROW_SIZE; j++)
                        saveWriter.print(map[i][j].getTypeChar());
                    saveWriter.println();
                }

            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
            showStartPane();
        });
    }

    public static void die() {
        stopMoving();
        Sound.dieSound();

        DiePane diePane = new DiePane();
        Scene scene = new Scene(diePane, WIDTH_OF_PANE, HEIGHT_OF_PANE);
        primaryStage.setScene(scene);

        diePane.getImagePlayAgain().setOnMouseClicked(e -> initializeMap(false));
        diePane.getLbBack().setOnMouseClicked(e -> showStartPane());
        recordScore();
    }

    public static void win() {
        stopMoving();
        Sound.winSound();

        WinPane winPane = new WinPane();
        Scene scene = new Scene(winPane, WIDTH_OF_PANE, HEIGHT_OF_PANE);
        primaryStage.setScene(scene);

        winPane.getLbPlayAgain().setOnMouseClicked(e -> initializeMap(false));
        winPane.getLbBack().setOnMouseClicked(e -> showStartPane());

        recordScore();
    }

    public static void recordScore() {
        RankingListPane rankingListPane = new RankingListPane();
        int score = showStatus.getScores();
        for (int i = 0; rankingListPane.getScores().size() == 0 || i < rankingListPane.getScores().size(); i++) {
            if (rankingListPane.getScores().size() < 5 || rankingListPane.getScores().get(i) <= score) {
                InputNamePane namePane = new InputNamePane("You break the record!\nPlease input your name.");
                Stage stage = new Stage();
                Scene scene = new Scene(namePane, 200, 200);
                stage.setScene(scene);
                stage.setTitle("Congratulations");
                stage.getIcons().add(new Image(Paths.get("resource/cherry.jpg").toUri().toString()));

                stage.show();
                final String[] name = new String[1];
                namePane.getLbOK().setOnMouseClicked(e -> {
                    name[0] = namePane.getTxName().getText().trim();
                    stage.close();
                    rankingListPane.writeRecord(name[0], score);
                });
                break;
            }
        }
    }

    public static void showRankingList() {
        RankingListPane rankingListPane = new RankingListPane();
        Scene scene = new Scene(rankingListPane, WIDTH_OF_PANE, HEIGHT_OF_PANE);
        primaryStage.setScene(scene);

        rankingListPane.getLbBack().setOnMouseClicked(e -> showStartPane());
    }

    public static void stopMoving() {
        if (pacman.getTimeline() != null)
            pacman.getTimeline().stop();
        if (pacman.getMoveTimeline() != null)
            pacman.getMoveTimeline().stop();
        for (int i = 0; i < monsterNum; i++) {
            if (monster[i].getMoveTimeline() != null)
                monster[i].getMoveTimeline().stop();
            if (monster[i].getTimeline() != null)
                monster[i].getTimeline().stop();
        }
    }

    public static void showSettingPane() {
        SettingPane settingPane = new SettingPane();
        Stage stage = new Stage();
        Scene scene = new Scene(settingPane, 400, 300);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setTitle("Set the game");
        stage.getIcons().add(new Image(Paths.get("resource/cherry.jpg").toUri().toString()));
        stage.show();

        settingPane.getLbOK().setOnMouseClicked(e -> {
            int x = Integer.parseInt(settingPane.getTfPacmanX().getText());
            int y = Integer.parseInt(settingPane.getTfPacmanY().getText());
            if (x >= 0 && x < ROW_SIZE && y >= 0 && y < COLUMN_SIZE) {
                settingPane.saveSetting();
                stage.close();
            }
        });
    }

    @Override
    public void start(Stage primaryStage) {
        Main.primaryStage = primaryStage;

        showStartPane();

        Main.primaryStage.setTitle("Pacman");
        Main.primaryStage.setResizable(false);
        Main.primaryStage.getIcons().add(new Image(Paths.get("resource/cherry.jpg").toUri().toString()));
        Main.primaryStage.show();

        Sound.backGroundMusic();
    }
}