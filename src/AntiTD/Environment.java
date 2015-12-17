package AntiTD;

import AntiTD.database.*;
import AntiTD.tiles.CrossroadSwitch;
import AntiTD.tiles.Level;
import AntiTD.tiles.Tile;
import AntiTD.towers.BasicTower;
import AntiTD.towers.FrostTower;
import AntiTD.towers.Tower;
import AntiTD.troops.Troop;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @author Tobias Estefors
 * Class that acts as an operator for the whole game. Have responsability to
 * update the gameboard and gamestate each timetick wich is calculated by the class.
 * Is used to increment/restart levels and checks if the game is finished.
 * Each new level, Enviroment sets up the neighbors for each tile and places the towers on the
 * board.
 *
 */
public class Environment extends JPanel implements Runnable,Observer {

    private Database db;
    private boolean onlineMode;

    private int victoryScore;
    private final int minimumCredits=174;
    private int finalScore=0;
    private int credits;
    private int mapNr=0;
    private int restartMoney;
    private boolean playMusic = true;


    private ArrayList<CrossroadSwitch> switches;
    private ArrayList<Level> levels;
    private ArrayList<Tower> towers = new ArrayList<>();

    private Handler handler;

    private BufferedImage basicTower;


    private BufferedImage frostTower;

    private Tile[][] map;

    private GUI gui;

    private  Executor runner= Executors.newFixedThreadPool(4);;

    private  boolean gameRunning;
    private boolean paused;
    private boolean gameOver;

    private Thread thread;

    private Level level;
    private Sounds sounds = new Sounds();


    public Environment(GUI gui, File fp){
        super(new BorderLayout());
        thread=null;
        this.gui=gui;
        gameOver=false;
        handler=new Handler(0,this);
        ReadXML xmlReader = new ReadXML(fp);
        levels=xmlReader.getLevels();
        level=levels.get(mapNr);
        map=level.getMap();
        setUpNeighbors();
        credits= level.getStartingCredits();
        victoryScore=level.getVictoryPoints();
        try {
            basicTower= ImageIO.read( this.getClass().getResourceAsStream("/sprites/basic.png"));
            frostTower= ImageIO.read( this.getClass().getResourceAsStream("/sprites/frost.gif"));
            switches=level.setUpCrossroad();
            level.setUpConnection();
        } catch (IOException e) {
            e.printStackTrace();
        }
        initTowers();
        restartMoney=credits;

        for(CrossroadSwitch cSwitch:switches){
            addMouseListener(cSwitch);
        }
        setLayout(new GridLayout(1, 1));
        setPreferredSize(new Dimension(map.length * 70, map[0].length * 70));
        try {
            db = new Database();
            onlineMode = true;
        } catch (DatabaseConnectionIsBusyException | NoDatabaseDriverInstalledException e) {
            onlineMode = false;
        }
    }

    /**
     * Iterates over all tiles and add a neighbourarray for each tile
     */
    private void setUpNeighbors() {
        for (int y = 0; y < map.length; y++) {
            for (int x = 0; x < map[0].length; x++) {
                ArrayList<Tile> neighbors = new ArrayList<Tile>(8);
                for (int row = -1; row <= 1; row++) {
                    for (int col = -1; col <= 1; col++) {
                        if ( row+col == -1 || row+col == 1 ) {
                            if((((y+row)<map.length) && (0<=(y+row))) &&
                                    ((x+col>=0)&&((x+col)<map[0].length))) {
                                if (map[y + row][x + col].isMoveable()) {
                                    neighbors.add(map[y + row][x + col]);
                                }
                            }
                        }
                    }
                }
                Tile[] neighborsArr = new Tile[neighbors.size()];
                for (int index = 0; index < neighbors.size(); index++) {
                    neighborsArr[index] = neighbors.get(index);
                }
                map[y][x].setNeighbors(neighborsArr);
            }
        }
    }

    /**
     * Returns a list of the highscore stored in the database
     * @return An arraylist containing highscores
     * @throws NoDatabaseConnectionException
     */
    public synchronized ArrayList<DBModel> getHighScores() throws NoDatabaseConnectionException{
        if (onlineMode) {
            return db.getHighscores();
        } else {
            throw new NoDatabaseConnectionException();
        }
    }

    /**
     * Getter for currentlevel used by environment
     * @return an instance of current level
     */
    Level getLevel(){
        return level;
    }

    /**
     * Starts the environment thread if its not already running
     */
    public synchronized void start(){
        paused=false;
        gameRunning=true;
        if (thread==null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    /**
     * Stops the environment thread if its running.
     */
    public synchronized void stop(){
        try{
            if(gameRunning) {
                thread.join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if game is paused or not
     * @return true if game is paused, else false
     */
    public boolean isPaused(){
        return paused;
    }

    /**
     * Flags for the gameloop that it can run
     */
    public void startGame(){
        gameRunning=true;
    }

    /**
     * Checks if the game is over
     * @return true if game is over, else false
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Clears and paints the gameboard with current gamestate
     * @param g the graphics of the board
     */
    public void paintComponent( Graphics g){
        setTileSize();
        g.clearRect(0, 0, getWidth(), getHeight());
       for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[0].length; j++) {
                map[i][j].landOn(g);
            }
        }
        handler.render(g);
    }

    /**
     * Sets the tile size to fit the window's size
     */
    private void setTileSize(){
        Tile[][] map=level.getMap();
        for(int i=0; i < map.length;i++){
            for(int j=0; j < map[i].length;j++){
                map[i][j].setSize(new Dimension(getWidth() / map.length, getHeight() / map[i].length));
            }
        }
    }

    /**
     * Updates the gamestate 60 times per second by calculating how often each update occurs
     * and makes the thread sleep for a specified time each timetick
     */
    public void run() {
        long lastTime = System.currentTimeMillis();
        double amountOfTicksPerSecond = 60;
        long ns = Math.round(1000.0 / amountOfTicksPerSecond);
        double delta = 0;
        int ticks=0;
        while(gameRunning){
            long now = System.currentTimeMillis();
            long wait = ns - (now - lastTime);
            lastTime = now;
            wait = wait < 0 ? 0 : wait;
            finishedLevel();
            try {
                thread.sleep(wait);
                if (! isPaused()) {
                    finalScore++;
                    gui.updateScore();
                    runner.execute(new Runnable() {
                        public void run() {
                            handler.tick();
                        }
                    });
                    repaint();
                    ticks++;
                    if(ticks>60){
                        int value=handler.getAliveTroops().size();
                        credits+=(value*2);
                        ticks=0;
                    }


                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Checks if game is currenty running
     * @return true if game is running, else false
     */
    public  boolean isRunning(){
        return gameRunning;
    }

    /**
     * Adds a troop to the handlers liost
     * @param troop the troop to be added
     */
    public void addTroop(Troop troop){
        handler.addObject(troop);
    }
    /**
     * Adds a tower to the handlers liost
     * @param tower the troop to be added
     */
    public void addTower(Tower tower){
        handler.addObject(tower);
        towers.add(tower);
    }

    /**
     * Returns a list of current alive troops
     * @return an arraylist of troops
     */
    public ArrayList<Troop> getTroops(){
        return handler.getAliveTroops();
    }

    /**
     * Sets the current gamestate to paused
     */
    public  void pauseGame(){
        paused=true;
    }

    /**
     * Resumes the game
     */
    public  void resumeGame(){
        paused=false;
    }

    /**
     * Changes the level to the next level.
     * @param restart if the map is to be restarted
     * @param gameOver true if game is finished, else false
     * @param wonMap true if the map was won and should continue to the next
     */
    private void incrementLevel(boolean restart, boolean gameOver, boolean wonMap){
        pauseGame();
        int currentMap=mapNr;
        mapNr++;
        if(mapNr>levels.size()-1 || restart){
            restart = !gameOver;
            int reply;
            if(restart){
                reply=0;
            }
            else{
                reply=JOptionPane.showConfirmDialog(null, "GG! \n Would you like to play again?",
                        "GG EZ!", JOptionPane.YES_NO_OPTION);
            }
            if (reply == JOptionPane.YES_OPTION) {
                if(sounds.isPlaying())
                 sounds.pauseMusic();
                gui.resumeMainSound();
                mapNr=restart ? currentMap : 0;
                mapNr=wonMap ? currentMap+1 :mapNr;
                handler.resetScore();
                resetTeleport();
                restart=true;
            }
            else {
                System.exit(0);
            }
        }
        for(CrossroadSwitch switc: switches){
            removeMouseListener(switc);
        }
        level=levels.get(mapNr);
        victoryScore=(level.getVictoryPoints()+handler.getVictoryScore());
        map=level.getMap();
        credits+=level.getStartingCredits();
        restartMoney=credits;
        credits= restart ? level.getStartingCredits() : restartMoney;
        setUpNeighbors();
        ArrayList<CrossroadSwitch>switches=level.setUpCrossroad();
        level.setUpConnection();
        for(CrossroadSwitch cSwitch:switches){
            addMouseListener(cSwitch);
        }
        initTowers();
        resumeGame();
        gameRunning=true;

    }

    /**
     * Restarts the game from level 0
     * @param restart true if game is to be restarted
     */
    public void restartLevel(boolean restart){
        handler.resetGame();
        incrementLevel(restart,false,false);
    }

    /**
     * Is called every timetick to check if current map is finished and depends on the current gamestate choses
     * what is to be happening next
     */
    private void finishedLevel(){
        if(handler.getVictoryScore() >= victoryScore){
            handler.resetGame();
            if((mapNr+1)>levels.size()-1) {
                if(playMusic){
                    sounds.music("music/gameover.wav",false);
                }
                gui.pauseMainSound();
                if (onlineMode) {
                    try {
                        DBModel dbEntry = db.getHighscore(gui.getPlayerName());
                        if (dbEntry.getScore() < handler.getVictoryScore()) {
                            db.insertOrUpdateHighscore(gui.getPlayerName(), handler.getVictoryScore());
                        }

                    } catch (DatabaseEntryDoesNotExistsException e) {
                        db.insertOrUpdateHighscore(gui.getPlayerName(), handler.getVictoryScore());
                    }
                }

                int reply = JOptionPane.showConfirmDialog(null, "GG! \n Would you like to play again?",
                        "GG EZ!", JOptionPane.YES_NO_OPTION);
                if (reply == JOptionPane.YES_OPTION) {
                    sounds.pauseMusic();
                    gui.resumeMainSound();
                    mapNr = -1;
                    incrementLevel(true,false,true);

                } else {
                    System.exit(0);
                }
            }
            else {
                handler.resetGame();
                incrementLevel(false, false,false);
            }
        }
        else if(!handler.hasAliveTroops() && (credits <= minimumCredits)){
            gui.pauseMainSound();
            if(gameRunning)
                sounds.music("/music/gameover.wav",false);
            handler.resetGame();
            incrementLevel(true, true,false);
        }
    }

    /**
     * Pause the in-game music
     */
    public void pauseEnvSound(){
        playMusic = false;
    }

    /**
     * Resumes the in-game music
     */
    public void resumeEnvSound(){
        playMusic = true;
    }

    /**
     * Returns the current score for the game.
     * @return an integer of current score
     */
    public int getScore() {
        return handler.getVictoryScore();
    }

    /**
     * Returns the value of the current money the player has
     * @return an integer containing the money
     */
    public int getMoney(){
        return credits;
    }

    /**
     * Used by the gui to buy units to the game. Does nothing if the player dont have enough credits
     * @param amount of credits spent for the troop to be buyed
     * @return true if the unit could be bought, else false
     */
    public boolean buyUnit(int amount){
        if((credits-amount)>0) {
            credits -= amount;
            return true;
        }
        return false;
    }

    /**
     * Initates the towers to the gameboard
     */
    private void initTowers(){
        towers.clear();
        Tile[][] currentMap = level.getMap();
        for (int i = 0; i < currentMap.length; i++) {
            for (int j = 0; j < currentMap[i].length; j++) {
                if (currentMap[i][j].isBuildable()) {
                    if((i+j)%3==0) {
                        if ((j % 2) == 0) {
                            addTower(new FrostTower(frostTower, currentMap[i][j], getTroops(), handler));
                        } else {
                            addTower(new BasicTower(basicTower, currentMap[i][j], getTroops(), handler));
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns a list of all towers
     * @return an arraylist of all towers
     */
    public ArrayList getTowers(){
        return towers;
    }

    /**
     * Resets the teleports so they wont be there when the game is restarted or incremented.
     */
    private void resetTeleport(){
        for(int i=0; i < levels.size(); i++){
            Tile[][] map=levels.get(i).getMap();
            for(int j=0; j < map.length; j++){
                for(int k=0; k < map[0].length;k++){
                    map[j][k].resetTeleport();
                }
            }
        }
    }

    /**
     * Updates the game money each timetick
     */
    @Override
    public void update(Observable o, Object arg) {
        credits+=(int)arg;
    }

    /**
     * Pauses the music for all gamesounds
     * @param paused true if should be paused, else false
     */
    public void setPaused(boolean paused){
        handler.setIsPaused(paused);
    }
}
