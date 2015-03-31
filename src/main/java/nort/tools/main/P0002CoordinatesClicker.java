package nort.tools.main;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import nort.tools.NortConfig;
import nort.tools.clicker.CoordinatesClicker;

import org.json.JSONArray;
import org.json.JSONObject;

public class P0002CoordinatesClicker extends CoordinatesClicker {

  private static String imageFile;
  private static String outputFile;
  private final JSONArray outputJson = new JSONArray();

  public static void main(String[] args) {
    imageFile = NortConfig.get("p0002.imageFile");
    outputFile = NortConfig.get("p0002.outputFile");
    ;
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
        System.out.println("hello");
        int x = (int) event.getSceneX();
        int y = (int) event.getSceneY();
        if (event.getButton().equals(MouseButton.SECONDARY)) {
          // add color and start new area
          JSONObject area = new JSONObject();
          area.put("color", "TODO"); // TODO farbe pixel ermitteln
          area.put("points", points);
          outputJson.put(area);
          points = new JSONArray();
        } else {
          // add point
          points.put(new JSONObject("{\"x\":" + x + ",\"y\":" + y + "}"));
        }
      }
    };
  }

  @Override
  public Image getImage() {
    return new Image(imageFile, 800, 600, true, true);
  }
}