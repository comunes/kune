package org.ourproject.kune.workspace.client.socialnet;

public class UIStatus {

    private boolean visible;
    private boolean enabled;

    public UIStatus(final boolean visible, final boolean enabled) {
        this.visible = visible;
        this.enabled = enabled;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UIStatus other = (UIStatus) obj;
        if (enabled != other.enabled) {
            return false;
        }
        if (visible != other.visible) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (enabled ? 1231 : 1237);
        result = prime * result + (visible ? 1231 : 1237);
        return result;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }

    public void setVisible(final boolean visible) {
        this.visible = visible;
    }

    @Override
    public String toString() {
        return "(v: " + visible + ", e:" + enabled + ")";
    }
}
