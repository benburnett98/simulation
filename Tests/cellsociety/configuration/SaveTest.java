package cellsociety.configuration;

import cellsociety.model.GameOfLife;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import static org.junit.jupiter.api.Assertions.*;

class SaveTest {

    private static final String PROPERTIES_ROOT = "cellsociety.controller.resources.";
    private static final String PROPERTIES_FOLDER = "src/" + PROPERTIES_ROOT.replace(".", "/");
    private static final String LAYOUTS_ROOT = "data/";

    private String saveLoc = "test";
    private String title = "TestTitle";
    private String author = "Ben Burnett";
    private String description = "This is a test";
    private String gameType = "Test";
    private String textLoc = "location";

    @Test
    void testSaveFiles() throws IOException {
        File properties = new File(PROPERTIES_FOLDER + saveLoc + ".properties");
        properties.delete();
        File csv = new File(LAYOUTS_ROOT + saveLoc + ".csv");
        csv.delete();
        SavePullHelper saver = new SavePullHelper(gameType);
        Builder myBuilder = new Builder("3Alive");
        GameOfLife mySim = new GameOfLife(myBuilder.getCells(), myBuilder.getXBound(), myBuilder.getYBound());
        saver.save(saveLoc, title, author, description, textLoc, mySim, myBuilder.getXBound(), myBuilder.getYBound());
        try {
            ResourceBundle savedResource = ResourceBundle.getBundle(PROPERTIES_ROOT + saveLoc);
            assertEquals(title, savedResource.getString("Title"));
            assertEquals(author, savedResource.getString("Author"));
            assertEquals(description, savedResource.getString("Description"));
            assertEquals(textLoc, savedResource.getString("TextDisplayFile"));
            assertEquals(saveLoc, savedResource.getString("CSVLocation"));
            assertEquals(gameType, savedResource.getString("GameRecommended"));
            assertEquals(gameType, savedResource.getString("GamesSupported"));
            properties = new File(PROPERTIES_FOLDER + saveLoc + ".properties");
            properties.delete();
            csv = new File(LAYOUTS_ROOT + saveLoc + ".csv");
            csv.delete();
        } catch(MissingResourceException e) {
            properties = new File(PROPERTIES_FOLDER + saveLoc + ".properties");
            properties.delete();
            csv = new File(LAYOUTS_ROOT + saveLoc + ".csv");
            csv.delete();
            assertEquals("The test","is failing");
        }

    }
}