package nort.tools.clicker;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class CoordinatesClicker extends Application {

  abstract EventHandler<MouseEvent> getEventHandler();

  abstract Image getImage();

  @Override
  public void start(Stage stage) throws IOException {
    // load the image
    Image image = getImage();
    // simple displays ImageView the image as is
    ImageView iv1 = new ImageView();
    iv1.setImage(image);
    iv1.setOnMouseClicked(this.getEventHandler());
    Group root = new Group();
    Scene scene = new Scene(root);
    scene.setFill(Color.BLACK);
    HBox box = new HBox();
    box.getChildren().add(iv1);
    root.getChildren().add(box);
    stage.setTitle("ImageView");
    stage.setWidth(415);
    stage.setHeight(200);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
  }
}