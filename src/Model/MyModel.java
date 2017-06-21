package Model;

import Client.Client;
import Client.IClientStrategy;
import IO.MyDecompressorInputStream;
import Server.Server;
import Server.ServerStrategyGenerateMaze;
import Server.ServerStrategySolveSearchProblem;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;
import algorithms.search.AState;
import algorithms.search.Solution;
import javafx.scene.input.KeyCode;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by linoy on 07/06/2017.
 */
public class MyModel extends Observable implements IModel  {
    private ExecutorService threadPool = Executors.newFixedThreadPool(2);
    private Server solveSearchProblemServer;
    private Server mazeGeneratingServer;
    public Maze maze;
    private Solution mazeSolution;
    private int characterPositionRow;
    private int characterPositionColumn;
    public void startServers(){
        mazeGeneratingServer= new Server(5400, 1000, new ServerStrategyGenerateMaze());
        solveSearchProblemServer = new Server(5401, 1000, new ServerStrategySolveSearchProblem());
        solveSearchProblemServer.start();
        mazeGeneratingServer.start();
    }
    public void stopServers(){
        solveSearchProblemServer.stop();
        mazeGeneratingServer.stop();
        threadPool.shutdown();

    }
    public void loadMaze(File chosen)throws Exception{

        ObjectInputStream in = new ObjectInputStream(new FileInputStream(chosen.getAbsoluteFile()));
        byte[] mazearr =(byte[])in.readObject();
        maze = new Maze(mazearr);
        setChanged();
        notifyObservers("gen");
    }

    @Override
    public void generateMaze(int width, int height) {
        //threadPool.execute(()->{
            try {
                Client client = new Client(InetAddress.getLocalHost(), 5400, new IClientStrategy() {
                    public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                        try {
                            ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                            ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                            toServer.flush();
                            int[] mazeDimensions = new int[]{height, width};
                            toServer.writeObject(mazeDimensions);
                            toServer.flush();
                            byte[] compressedMaze = (byte[])fromServer.readObject();
                            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
                            byte[] decompressedMaze = new byte[100000];
                            is.read(decompressedMaze);
                            maze = new Maze(decompressedMaze);
                            maze.print();
                            characterPositionRow = maze.getStartPosition().getRowIndex();
                            characterPositionColumn = maze.getStartPosition().getColumnIndex();
                        } catch (Exception var10) {
                            var10.printStackTrace();
                        }

                    }
                });
                client.communicateWithServer();
            } catch (UnknownHostException var1) {
                var1.printStackTrace();
            }

        //});
        setChanged();
        notifyObservers("gen");

    }

    @Override
    public void moveCharacter(KeyCode movement) {
        switch (movement) {
            case UP:
                if(characterPositionRow-1>=0 && maze.getMaze()[characterPositionRow-1][characterPositionColumn]==0)
                    characterPositionRow--;
                break;
            case DOWN:
                if(characterPositionRow+1<maze.getMaze().length && maze.getMaze()[characterPositionRow+1][characterPositionColumn]==0)
                    characterPositionRow++;
                break;
            case RIGHT:
                if(characterPositionColumn+1<maze.getMaze()[0].length && maze.getMaze()[characterPositionRow][characterPositionColumn+1]==0)
                    characterPositionColumn++;
                break;
            case LEFT:
                if(characterPositionColumn-1>=0 && maze.getMaze()[characterPositionRow][characterPositionColumn-1]==0)
                    characterPositionColumn--;
                break;
        }
        setChanged();
        notifyObservers("keymove");
    }

    @Override
    public Maze getMaze() {
        return maze;
    }

    public Solution getSolution(){return mazeSolution;}

    @Override
    public int getCharacterPositionRow() {
        return characterPositionRow;
    }

    @Override
    public int getCharacterPositionColumn() {
        return characterPositionColumn;
    }


    public void SolveMaze() {
        //threadPool.execute(() -> {
        try {
            Client client = new Client(InetAddress.getLocalHost(), 5401, new IClientStrategy() {
                public void clientStrategy(InputStream inFromServer, OutputStream outToServer) {
                    try {
                        ObjectOutputStream toServer = new ObjectOutputStream(outToServer);
                        ObjectInputStream fromServer = new ObjectInputStream(inFromServer);
                        toServer.flush();
                        maze.setStartPosition(new Position(characterPositionRow,characterPositionColumn,"S"));
                        toServer.writeObject(maze);
                        toServer.flush();
                        mazeSolution = (Solution)fromServer.readObject();
                        System.out.println(String.format("Solution steps: %s", new Object[]{mazeSolution}));
                        ArrayList<AState> mazeSolutionSteps = mazeSolution.getSolutionPath();


                        for(int i = 0; i < mazeSolutionSteps.size(); ++i) {
                            System.out.println(String.format("%s. %s", new Object[]{Integer.valueOf(i), ((AState)mazeSolutionSteps.get(i)).toString()}));
                        }
                    } catch (Exception var10) {
                        var10.printStackTrace();
                    }

                }
            });
            client.communicateWithServer();
        } catch (UnknownHostException var1) {
            var1.printStackTrace();
            }
            setChanged();
            notifyObservers("solution");
        //});

    }
    public void resetSolution(){
        mazeSolution=null;
    }
}
