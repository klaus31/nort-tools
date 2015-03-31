package nort.tools.clicker;

import java.awt.Toolkit;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;

public abstract class SingleOrDoubleClickHandler implements EventHandler<MouseEvent> {

  private final static int clickInterval = (Integer) Toolkit.getDefaultToolkit().getDesktopProperty("awt.multiClickInterval");
  private MouseEvent eventToHandle;
  private boolean timerActive = false;

  @Override
  public void handle(MouseEvent event) {
    if (event.getClickCount() > 2) {
      return;
    }
    eventToHandle = event;
    if (!timerActive) {
      timerActive = true;
      Timer timer = new Timer();
      timer.schedule(new TimerTask() {

        @Override
        public void run() {
          if (eventToHandle.getClickCount() == 1) {
            handleSingleClick(eventToHandle);
          } else if (eventToHandle.getClickCount() == 2) {
            handleDoubleClick(eventToHandle);
          }
          timerActive = false;
        }
      }, clickInterval);
    }
  }

  public abstract void handleSingleClick(MouseEvent event);

  public abstract void handleDoubleClick(MouseEvent event);
}