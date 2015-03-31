package nort.tools.clicker;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

public class CoordinatesClickerP0000 extends CoordinatesClicker {

  private static Image image;
  private static String format;

  public static void main(String[] args) {
    image = new Image(args[0], 800, 600, true, true);
    format = args[1];
    Application.launch(args);
  }

  @Override
  public EventHandler<MouseEvent> getEventHandler() {
    return new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        final int OFFSET_X = 79;
        final int OFFSET_Y = 178;
        int x = (int) event.getSceneX() - OFFSET_X;
        int y = (int) event.getSceneY() - OFFSET_Y;
        System.out.println(String.format(format, x, y));
      }
    };
  }

  @Override
  Image getImage() {
    return image;
  }
}