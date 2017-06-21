package View;

import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.nio.file.Paths;
import java.util.ArrayList;

/**
 * Created by linoy on 07/06/2017.
 */
public class MazeDisplayer extends Canvas {


    //represtation to the maze
    int [][] mazeData;
    private StringProperty wallFileNmae;
    private ArrayList<int[]> solarr;
    //for the carchter
    algorithms.mazeGenerators.Position start;
    algorithms.mazeGenerators.Position end;
    int cCol;
    int cRow;
    int goal_row;
    int goal_col;


    public MazeDisplayer(){
        wallFileNmae=new SimpleStringProperty();
    }

    public void setMazeData(Maze mazeData) {
        this.mazeData = mazeData.getMaze();
        start=mazeData.getStartPosition();
        cCol=start.getColumnIndex();
        cRow=start.getRowIndex();
        end =mazeData.getGoalPosition();
        goal_col=end.getColumnIndex();
        goal_row=end.getRowIndex();
        redraw();
    }
    public void setSolution(ArrayList<int[]> sol){
        solarr=sol;
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

            //delete the maze and redrow new maze with the charachter
            gc.clearRect(0,0,width,higth);
            for(int i=0;i<mazeData.length;i++) {
                for (int j = 0; j < mazeData[i].length; j++) {
                    if (mazeData[i][j] != 0) {
                        if (wall == null) {
                            gc.fillRect(j * wid, i * hig, wid, hig);
                        } else {
                            gc.drawImage(wall, j * wid, i * hig, wid, hig);
                        }
                    }
                    if(i== goal_col && j==goal_row){
                        Image goal=new Image("file:"+currPath+"/src/resources/bee_hive.jpg");
                        gc.drawImage(goal,goal_col*wid,goal_row*hig,wid,hig);
                    }

                }
            }
            Image charachter=new Image("file:"+currPath+"/src/resources/bee.jpg");
            gc.drawImage(charachter,cCol*wid,cRow*hig,wid,hig);
        }

    }
    public void drawSolution(){
        String currPath= Paths.get("").toAbsolutePath().toString();
        double width=getWidth();
        double higth=getHeight();
        Image circle=new Image("file:"+currPath+"/src/resources/circles.jpg");
        //number col
        double wid=width/mazeData[0].length;
        //num row
        double hig=higth/mazeData.length;
        GraphicsContext gc1 = getGraphicsContext2D();
        gc1.setFill(Color.GOLD);
        for(int a[] :solarr) {
            if(!(a[0]== goal_row && a[1]==goal_col ) && !(a[0]==cRow && a[1]==cCol))
                gc1.drawImage(circle,a[1]*wid, a[0]*hig,wid,hig);
        }


    }
    public void drawChar(){
        double width=getWidth();

        double higth=getHeight();
        //number col
        double wid=width/mazeData[0].length;
        //num row
        double hig=higth/mazeData.length;
        GraphicsContext gc=getGraphicsContext2D();
        String currPath= Paths.get("").toAbsolutePath().toString();
        Image charachter=new Image("file:"+currPath+"/src/resources/bee.jpg");
        gc.drawImage(charachter,cCol*wid,cRow*hig,wid,hig);
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

    public void clear(){
        GraphicsContext gc = getGraphicsContext2D();
        gc.clearRect(this.getScaleX(),this.getLayoutY(),this.getBaselineOffset(),this.computeAreaInScreen());
    }
    public void setCharacterPosition (int row,int col){
        cRow=row;
        cCol=col;
        redraw();
    }
    public int getcCol() {
        return cCol;
    }

    public void setcCol(int cCol) {
        this.cCol = cCol;
    }

    public int getcRow() {
        return cRow;
    }

    public void setcRow(int cRow) {
        this.cRow = cRow;
    }
}
