package cc.kune.core.shared.dto;

import java.util.List;
import java.util.Map;

public class StateEventContainerDTO extends StateContainerDTO {

  private List<Map<String, String>> appointments;

  public StateEventContainerDTO() {
  }

  public List<Map<String, String>> getAppointments() {
    return appointments;
  }

  public void setAppointments(final List<Map<String, String>> appointments) {
    this.appointments = appointments;
  }

}
