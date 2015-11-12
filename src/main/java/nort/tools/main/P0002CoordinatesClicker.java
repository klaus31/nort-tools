package nort.tools.main;

import java.io.File;
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

  public static void main(final String[] args) {
    // launch it
    Application.launch(args);
  }

  private String outputFile;
  private JSONArray outputJson;
  private JSONArray points = new JSONArray();

  private String getWebColor(final int x, final int y) {
    Color color = image.getPixelReader().getColor(x, y);
    return String.format("#%02X%02X%02X", (int) (color.getRed() * 255), (int) (color.getGreen() * 255), (int) (color.getBlue() * 255));
  }

  @Override
  protected void handleMouseEvent(final MouseEvent event, final int x, final int y) {
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

  @Override
  public void setImage() {
    image = new Image(NortConfig.get("p0002.imageFile"), 1000, 750, true, true);
  }

  @Override
  public void start(final Stage stage) throws IOException {
    super.start(stage);
    stage.setFullScreen(true);
    outputFile = NortConfig.get("p0002.outputFile");
    File oFCheck = new File(outputFile);
    if (!oFCheck.canRead() || !oFCheck.canWrite()) {
      System.out.println("echo [] > " + oFCheck.getAbsolutePath() + " # first");
      System.exit(2);
    }
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
}