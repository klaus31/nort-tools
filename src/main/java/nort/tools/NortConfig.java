package nort.tools;

import java.io.IOException;
import java.util.Properties;

public class NortConfig {

  private final Properties properties = new Properties();
  private static NortConfig me = null;

  private static NortConfig me() {
    if (me == null) {
      me = new NortConfig();
    }
    return me;
  }

  public static String get(String key) {
    if (!me().properties.containsKey(key)) {
      System.err.println("ensure config.properties exist and has key " + key);
      System.exit(1503311937);
    }
    return (String) me().properties.get(key);
  }

  private NortConfig() {
    try {
      properties.load(NortConfig.class.getResourceAsStream("config.properties"));
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
