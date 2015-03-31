package nort.tools.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import nort.tools.NortConfig;
import nort.tools.clicker.CoordinatesClicker;

import org.json.JSONArray;
import org.json.JSONObject;

public class P0002CoordinatesClicker extends CoordinatesClicker {

  private static Image image;
  private static String outputFile;
  private final JSONArray outputJson = new JSONArray();

  public static void main(String[] args) {
    outputFile = NortConfig.get("p0002.outputFile");
    image = new Image(NortConfig.get("p0002.imageFile"), 800, 600, true, true);
    Application.launch(args);
  }

  @Override
  public void stop() throws Exception {
    super.stop();
    try {
      PrintWriter writer = new PrintWriter(outputFile, "UTF-8");
      writer.write(outputJson.toString());
      writer.close();
    } catch (FileNotFoundException | UnsupportedEncodingException e) {
      e.printStackTrace();
    }
  }

  @Override
  public EventHandler<MouseEvent> getEventHandler() {
    return new EventHandler<MouseEvent>() {

      private JSONArray points = new JSONArray();

      @Override
      public void handle(MouseEvent event) {
        int x = (int) event.getSceneX();
        int y = (int) event.getSceneY();
        if (event.getButton().equals(MouseButton.SECONDARY)) {
          // add color and start new area
          JSONObject area = new JSONObject();
          String color = getWebColor(x, y);
          System.out.println("Add color: " + color + " and finalize area");
          area.put("color", color);
          area.put("points", points);
          outputJson.put(area);
          points = new JSONArray();
        } else {
          // add point
          System.out.println(String.format("x:%s;y:%s", x, y));
          points.put(new JSONObject("{\"x\":" + x + ",\"y\":" + y + "}"));
        }
      }
    };
  }

  private String getWebColor(int x, int y) {
    Color color = image.getPixelReader().getColor(x, y);
    return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
  }

  @Override
  public Image getImage() {
    return image;
  }
}