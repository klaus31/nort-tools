package nort.tools.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import nort.tools.NortConfig;

import org.apache.commons.io.IOUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class P0002CoordinatesClicker extends CoordinatesClicker2 {

  private String outputFile;
  private JSONArray outputJson;
  private JSONArray points = new JSONArray();

  public static void main(String[] args) {
    // launch it
    Application.launch(args);
  }

  @Override
  public void start(Stage stage) throws IOException {
    super.start(stage);
    stage.setFullScreen(true);
    outputFile = NortConfig.get("p0002.outputFile");
    // set json
    String jsonText = "[]";
    try {
      jsonText = IOUtils.toString(new FileInputStream(outputFile));
    } catch (IOException e) {
    }
    outputJson = new JSONArray(jsonText);
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

  private String getWebColor(int x, int y) {
    Color color = image.getPixelReader().getColor(x, y);
    return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
  }

  @Override
  public void setImage() {
    image = new Image(NortConfig.get("p0002.imageFile"), 1000, 750, true, true);
  }

  @Override
  protected void handleMouseEvent(MouseEvent event, int x, int y) {
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
}