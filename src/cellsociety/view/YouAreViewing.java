package cellsociety.view;

import cellsociety.controller.Controller;
import cellsociety.controller.KeyNotFoundException;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.util.ResourceBundle;

public class YouAreViewing extends Pane {

    private ResourceBundle myResource = ResourceBundle.getBundle("cellsociety.view.resources.View");

    public YouAreViewing(Controller control) {
        this.setPrefSize(400,0);
        this.setTranslateX(400);
        this.setTranslateY(650);
        try {
            Text author = new Text(myResource.getString("Simauthor")+control.getAuthor());
            author.setId("author");
            Text description = new Text(myResource.getString("Simdetail")+control.getDesc());
            description.setId("description");
            Text simtitle = new Text(myResource.getString("Simtitle")+control.getTitle());
            simtitle.setId("simtitle");
            this.getChildren().addAll(author, description, simtitle);
        }
        catch(KeyNotFoundException e) {

        }

    }
}
