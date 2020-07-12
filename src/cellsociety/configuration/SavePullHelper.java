package cellsociety.configuration;

import cellsociety.model.SimModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * @author Ben Burnett
 */

public class SavePullHelper {

    private static final String CSV_ROOT = "data/";
    private static final String CSV_EXT = ".csv";
    private static final String PROP_EXT = ".properties";
    private static final String SRC = "src/";
    private static final String SLASH = "/";
    private static final String FILE_SEPARATOR = ",";
    private static final String PROPERTIES_FOLDER = "cellsociety/controller/resources/";
    private static final String PROPERTIES_ROOT = PROPERTIES_FOLDER.replace(SLASH, ".");

    private String gameType;

    /**
     * Sets what type of game you're working with
     * @param GameType - the type of game the class works with
     */
    public SavePullHelper(String GameType) {
        gameType = GameType;
    }

    /**
     *  Helps us make the menu of possible properties file selections for the game
     * @return colOfLayouts - a collection of the layouts for a specific game
     * @throws FileNotFoundException
     */
    public Collection<String> getAllProperties() throws FileNotFoundException{
        Collection<String> collOfLayouts = new ArrayList<>();
        File dir = new File(SRC + PROPERTIES_FOLDER);
        for (String file : dir.list()) {
            String[] splitFile = file.split(SLASH);
            String fileWExtension = splitFile[splitFile.length - 1];
            if (!fileWExtension.contains(PROP_EXT)) continue;
            String fileName = fileWExtension.replace(PROP_EXT, "");
            System.out.println(fileName);
            ResourceBundle myResource = ResourceBundle.getBundle(PROPERTIES_ROOT + fileName);
            if (myResource.getString("GamesSupported").contains(gameType)) {
                collOfLayouts.add(fileName);
            }
        }
        return collOfLayouts;
    }

    /**
     * Saves the resource file and the current level configuration so that it can be accessed at a later date
     * @param saveLoc - the name of the properties and csv files that will be saved
     * @param author - the name of the person making these changes
     * @param description - a quick description of what the layout represent
     * @param xBound
     * @param yBound
     * @throws IOException
     */
    public void save(String saveLoc, String title, String author, String description, String textLoc,
                     SimModel sim, int xBound, int yBound) throws IOException {
        FileWriter csvWriter = new FileWriter(CSV_ROOT + saveLoc + CSV_EXT);
        csvWriter.append(Integer.toString(xBound));
        csvWriter.append(FILE_SEPARATOR);
        csvWriter.append(Integer.toString(yBound));
        csvWriter.append("\n");
        for (int x = xBound - 1; x >= 0 ; x --) {
            for (int y = 0; y < yBound; y++) {
                csvWriter.append(Integer.toString(sim.getValueAtLoc(x,y)));
                if (y < yBound - 1) csvWriter.append(FILE_SEPARATOR);
            }
            csvWriter.append("\n");
        }
        csvWriter.flush();
        csvWriter.close();
        FileWriter propWriter = new FileWriter(SRC + PROPERTIES_FOLDER + saveLoc + PROP_EXT);
        propWriteHelper(propWriter, "Title", title);
        propWriteHelper(propWriter, "Author", author);
        propWriteHelper(propWriter, "Description", description);
        propWriteHelper(propWriter, "CSVLocation", saveLoc);
        propWriteHelper(propWriter, "TextDisplayTitle", textLoc);
        propWriteHelper(propWriter, "GameRecommended", gameType);
        propWriteHelper(propWriter, "GamesSupported", gameType);
        propWriter.flush();
        propWriter.close();

        //TODO: need to figure out how to save a resource bundle
    }

    private void propWriteHelper(FileWriter file, String key, String value) throws IOException {
        file.append(key + "=");
        file.append(value);
        file.append("\n");
    }

}
