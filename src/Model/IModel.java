package Model;

import algorithms.mazeGenerators.Maze;
import javafx.scene.input.KeyCode;

/**
 * Created by idanr on 18/06/2017.
 */
public interface IModel {
    void generateMaze(int width, int height);
    void moveCharacter(KeyCode movement);
    Maze getMaze();
    int getCharacterPositionRow();
    int getCharacterPositionColumn();

}
