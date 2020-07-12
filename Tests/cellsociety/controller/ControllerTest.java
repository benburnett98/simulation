package cellsociety.controller;

import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    @Test
    void testGetters() throws FileNotFoundException {
        Controller control = new Controller("GameOfLife", "ConfigTest");
        String title;
        try {
            title = control.getTitle();
        } catch (KeyNotFoundException e) {
            title = "";
        }
        String author;
        try {
            author = control.getAuthor();
        } catch (KeyNotFoundException e) {
            author = "";
        }
        String desc;
        try {
            desc = control.getDesc();
        } catch (KeyNotFoundException e) {
            desc= "";
        }
        String text;
        try {
            text = control.getText();
        } catch (KeyNotFoundException e) {
            text = "";
        }
        assertEquals("TestTitle", title);
        assertEquals("TestAuthor", author);
        assertEquals("TestDescription", desc);
        assertEquals("TestText", text);
        assertEquals(3, control.getXBound());
        assertEquals(3, control.getYBound());
        assertEquals(3, control.getXBound());
        assertEquals(1, control.getValueAtLoc(0,2));
        assertEquals(0, control.changeCell(0,2));
        assertEquals(0, control.getValueAtLoc(2,0));
        assertEquals(1, control.changeCell(2,0));
    }
}