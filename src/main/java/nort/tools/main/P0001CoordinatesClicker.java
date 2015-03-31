package nort.tools.main;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import nort.tools.NortConfig;
import nort.tools.clicker.CoordinatesClicker;

public class P0001CoordinatesClicker extends CoordinatesClicker {

  private static String imageIn;
  private static String csvFormat;
  private PrintWriter writer = null;

  private String getCsvPath(int num) {
    return String.format(csvFormat, num);
  }

  public static void main(String[] args) {
    imageIn = NortConfig.get("p0001.imageIn");
    csvFormat = NortConfig.get("p0001.csvFormat");
    Application.launch(args);
  }

  private File getCsvFile(int num) {
    return new File(getCsvPath(num));
  }

  @Override
  public void start(Stage stage) throws IOException {
    super.start(stage);
    int i = 0;
    while (getCsvFile(i).exists()) {
      i++;
    }
    try {
      writer = new PrintWriter(getCsvPath(i), "UTF-8");
      System.out.println(i + ". file written to " + getCsvPath(i));
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    writer.close();
  }

  @Override
  public EventHandler<MouseEvent> getEventHandler() {
    return new EventHandler<MouseEvent>() {

      @Override
      public void handle(MouseEvent event) {
        int x = (int) event.getSceneX();
        int y = (int) event.getSceneY();
        writer.write(x + "," + y + System.lineSeparator());
      }
    };
  }

  @Override
  public Image getImage() {
    return new Image(imageIn, 800, 600, true, true);
  }
}