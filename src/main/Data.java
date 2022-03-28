package main;

public interface Data {
    int COLUMN_SIZE = 22;
    int ROW_SIZE = 19;
    int MILLISECOND_REFRESH = 25;
    int REFRESH_COUNT = 11;
    int WIDTH_OF_NODES = 22;
    int PIXEL_PER_MOVE = WIDTH_OF_NODES / REFRESH_COUNT;
    int TIME_OF_MOVE = MILLISECOND_REFRESH * REFRESH_COUNT;
    int WIDTH_OF_PANE = ROW_SIZE * WIDTH_OF_NODES;
    int WIDTH_OF_STATUS_LABEL = 40;
    int LENGTH_OF_SHOWLIFE_LABEL = 135;
    int HEIGHT_OF_PANE = COLUMN_SIZE * WIDTH_OF_NODES + WIDTH_OF_STATUS_LABEL;
    int SCORES_PER_BEAN1 = 10;
    int SCORES_PER_BEAN2 = 100;
    int SCORES_PER_MONSTER = 100;
}