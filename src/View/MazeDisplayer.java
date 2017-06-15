package View;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.nio.file.Paths;

/**
 * Created by linoy on 07/06/2017.
 */
public class MazeDisplayer extends Canvas {


    //represtation to the maze
    int [][] mazeData;
    private StringProperty wallFileNmae;

    public MazeDisplayer(){
        wallFileNmae=new SimpleStringProperty();
    }

    public void setMazeData(int[][] mazeData) {
        this.mazeData = mazeData;
        redraw();
    }

    //paint on the canvas
    public void redraw(){
        if(mazeData!=null){
            double width=getWidth();
            double higth=getHeight();
            //number col
            double wid=width/mazeData[0].length;
            //num row
            double hig=higth/mazeData.length;

            GraphicsContext gc=getGraphicsContext2D();
            Image wall=null;
               // wall = new Image(new FileInputStream(wallFileNmae.get()));
              //  wall=new Image("file:"+wallFileNmae.get());
            String currPath= Paths.get("").toAbsolutePath().toString();

            wall=new Image("file:"+currPath+"/src/resources/wall.jpg");
           //  wall=new Image("file:"+currPath+wallFileNmae.get());

            // wall = new Image(new FileInputStream("/resources/wall.jpg"));

            for(int i=0;i<mazeData.length;i++) {
                for (int j = 0; j < mazeData[i].length; j++) {
                    if (mazeData[i][j] != 0) {
                        if (wall == null) {
                            gc.fillRect(j * wid, i * hig, wid, hig);
                        } else {
                            gc.drawImage(wall, j * wid, i * hig, wid, hig);
                        }
                    }
                }
            }
        }

    }

    public String getWallFileNmae() {
        return wallFileNmae.get();
    }

    public String wallFileNmaeProperty() {
        return wallFileNmae.get();
    }

    public void setWallFileNmae(String wallFileNmae) {
        this.wallFileNmae.set(wallFileNmae);
    }
}
