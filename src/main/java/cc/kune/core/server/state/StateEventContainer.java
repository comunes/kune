package cc.kune.core.server.state;

import java.util.List;
import java.util.Map;

public class StateEventContainer extends StateContainer {

  private List<Map<String, String>> appointments;

  public StateEventContainer() {
  }

  public List<Map<String, String>> getAppointments() {
    return appointments;
  }

  public void setAppointments(final List<Map<String, String>> appointments) {
    this.appointments = appointments;
  }

}
