package cellsociety.controller;

import cellsociety.configuration.*;
import cellsociety.model.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * @author Ben Burnett
 */

public class Controller {

    private static final String PROPERTIES_ROOT = "cellsociety.controller.resources.";

    private static final double burnProb = .35;
    private static final int threshold = 4;
    private static final int reproductionTurns = 3;
    private static final double happyNeighbor = .5;

    private SimModel sim;
    private Builder setup;
    private String mySimType;
    private ResourceBundle myResource;
    private String myResourceLoc;

    /**
     * The controller for all the simulations - (besides SavePullHelper, which gets the list of files for the user
     * to choose from) this is the only class the view deals with -  all calls to the model are made through here
     * @param simType - the type of simulation the backend selected by the User
     * @param resourceLoc - the location of the resource folder selected by the User
     * @throws FileNotFoundException
     */
    public Controller(String simType, String resourceLoc) throws FileNotFoundException {
        mySimType = simType;
        constructionHelper(resourceLoc);
    }

    public Controller (File resource) throws FileNotFoundException {
        String filePath = resource.getPath();
        String[] directories = filePath.split("/");
        String resourceName = directories[directories.length - 1].replace(".properties", "");
        constructionHelper(resourceName);
        mySimType = myResource.getString("GameRecommended");
    }

    private void constructionHelper(String resourceName) throws FileNotFoundException {
        myResourceLoc = resourceName;
        myResource = ResourceBundle.getBundle(PROPERTIES_ROOT + resourceName);
        setup = new Builder(myResource.getString("CSVLocation"));
        makeSim();
    }

    /**
     * Determines which type of simulation the program is supposed to be running
     */
    private void makeSim() {
       
        if (mySimType.equals("GameOfLife")) {
            sim = new GameOfLife(setup.getCells(), setup.getXBound(), setup.getYBound());
            return;
        }
        if (mySimType.equals("Segregation")) {
            sim = new Segregation(setup.getCells(), setup.getXBound(), setup.getYBound(), badInput(happyNeighbor));
            return;
        }
        if (mySimType.equals("Fire")) {
            sim = new Fire(setup.getCells(), setup.getXBound(), setup.getYBound(), badInput(burnProb));
            return;
        }
        if (mySimType.equals("WaTor")) {
            sim = new WaTor(setup.getCells(), setup.getXBound(), setup.getYBound(),
                    (int) badInput(reproductionTurns));
            return;
        }
        if (mySimType.equals("Percolation")) {
            sim = new Percolation(setup.getCells(), setup.getXBound(), setup.getYBound());
            return;
        }
        if (mySimType.equals("RockPaperScissors")) {
            sim = new RockPaperScissors(setup.getCells(), setup.getXBound(), setup.getYBound(),
                    (int) badInput(threshold));
            return;
        }
        throw new SimNotSupportedException(mySimType);
    }

    private double badInput(double ifError) {
        try {
            return Double.parseDouble(myResource.getString("ExtraInfo"));
        } catch (MissingResourceException e) {
            return ifError;
        }
    }

    /**
     * Makes a call to the model to tell it to change the cell at that x,y and then returns what
     * the model has gotten
     * Use : this method is called by the view when the user clicks on a specific cell
     * @param x - x coordinate of the cell you're trying to change
     * @param y - y coordinate of the cell you're trying to change
     * @return the value the model has updated the cell at that position to
     */
    public int changeCell(int x, int y) {
        return sim.changeCell(x,y);
    }

    /**
     * Tells the model to move to the next state and update its values
     * Use : called by the view every step in the animation
     */
    public void change() {
        sim.change();
    }

    /**
     *
     * @param saveLoc
     * @param title
     * @param author
     * @param description
     * @throws IOException
     */
    public void save(String saveLoc, String title, String author, String description) throws IOException {
        SavePullHelper saver = new SavePullHelper(mySimType);
        String textLoc = myResource.getString("TextDisplayFile");
        saver.save(saveLoc, title, author, description, textLoc, sim, setup.getXBound(), setup.getYBound());
    }

    /**
     * How the represention of cells
     * @param x
     * @param y
     * @return
     */
    public int getValueAtLoc(int x, int y) {
        return sim.getValueAtLoc(x,y);
    }

    public int getXBound() {
        return setup.getXBound();
    }

    public int getYBound() {
        return setup.getYBound();
    }

    public String getAuthor() throws KeyNotFoundException {
        return checkKey("Author");
    }

    public String getDesc() throws KeyNotFoundException {
        return checkKey("Description");
    }

    public String getTitle() throws KeyNotFoundException {
        return checkKey("Title");
    }

    public String getText() throws KeyNotFoundException {
        return checkKey("TextDisplayFile");
    }

    public String getGameType() {
        return mySimType;
    }

    private String checkKey(String key) throws KeyNotFoundException {
        try {
            return myResource.getString("key");
        } catch (MissingResourceException e) {
            throw new KeyNotFoundException(key, myResourceLoc);
        }
    }

}