
package org.ourproject.kune.platf.client.tool;

public interface ToolTrigger {

    public interface TriggerListener {
	void onStateChanged(String encoded);
    }

    String getName();

    String getLabel();

    void setListener(TriggerListener listener);

}
