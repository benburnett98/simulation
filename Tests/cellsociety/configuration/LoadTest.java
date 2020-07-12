package cellsociety.configuration;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LoadTest {

    @Test
    void testGetProperties() throws FileNotFoundException {
        SavePullHelper propGetter = new SavePullHelper("ConfigurationTest");
        for (String prop : propGetter.getAllProperties()) {
            assertEquals("ConfigurationTest", prop);
        }
    }
}
