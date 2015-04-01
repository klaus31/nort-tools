package nort.tools.main;

import java.io.IOException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public abstract class CoordinatesClicker1 extends Application {

  protected Image image;

  public abstract EventHandler<MouseEvent> getEventHandler();

  // this.image = new Image....
  public abstract void setImage();

  @Override
  public void start(Stage stage) throws IOException {
    // load the image
    setImage();
    // image
    ImageView iv1 = new ImageView();
    iv1.setImage(image);
    iv1.setOnMouseClicked(this.getEventHandler());
    // scene
    StackPane box = new StackPane();
    box.getChildren().add(iv1);
    Scene scene = new Scene(box);
    scene.setFill(Color.BLACK);
    // stage
    stage.setTitle("ImageView");
    stage.setWidth(415);
    stage.setHeight(200);
    stage.setScene(scene);
    stage.sizeToScene();
    stage.show();
  }
}