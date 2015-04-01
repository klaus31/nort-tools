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
import javafx.stage.Screen;
import javafx.stage.Stage;

public abstract class CoordinatesClicker2 extends Application {

  private int offsetLeft;
  private int offsetTop;
  protected Image image;

  // this.image = new Image....
  public abstract void setImage();

  protected abstract void handleMouseEvent(MouseEvent event, int x, int y);

  public EventHandler<MouseEvent> getEventHandler() {
    return new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        int x = (int) event.getSceneX() - offsetLeft;
        int y = (int) event.getSceneY() - offsetTop;
        handleMouseEvent(event, x, y);
      }
    };
  }

  @Override
  public void start(Stage stage) throws IOException {
    // load the image
    setImage();
    // scene
    ImageView iv1 = new ImageView();
    iv1.setImage(image);
    StackPane pane = new StackPane();
    pane.getChildren().add(iv1);
    Scene scene = new Scene(pane);
    scene.setFill(Color.BLACK);
    scene.setOnMouseClicked(this.getEventHandler());
    // stage
    stage.setTitle("ImageView");
    stage.setScene(scene);
    stage.setFullScreen(true);
    // set boud
    offsetLeft = (int) ((Screen.getPrimary().getBounds().getWidth() - image.getWidth()) / 2);
    offsetTop = (int) ((Screen.getPrimary().getBounds().getHeight() - image.getHeight()) / 2);
    // show
    stage.show();
  }
}