package nort.tools.main;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import nort.tools.NortConfig;

public class P0000CoordinatesClicker extends CoordinatesClicker1 {

  private static String format;

  public static void main(String[] args) {
    format = NortConfig.get("p0001.format");
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
  public void setImage() {
    image = new Image(NortConfig.get("p0000.image"), 800, 600, true, true);
  }
}