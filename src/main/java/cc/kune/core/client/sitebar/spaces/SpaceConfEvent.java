package cc.kune.core.client.sitebar.spaces;

import com.google.gwt.event.shared.EventHandler;
import com.google.gwt.event.shared.GwtEvent;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.event.shared.HasHandlers;

public class SpaceConfEvent extends GwtEvent<SpaceConfEvent.SpaceConfHandler> {

    public interface HasSpaceConfHandlers extends HasHandlers {
        HandlerRegistration addSpaceConfHandler(SpaceConfHandler handler);
    }

    public interface SpaceConfHandler extends EventHandler {
        public void onSpaceConf(SpaceConfEvent event);
    }

    private static final Type<SpaceConfHandler> TYPE = new Type<SpaceConfHandler>();

    public static void fire(final HasHandlers source, final cc.kune.core.client.sitebar.spaces.Space space,
            final String token) {
        source.fireEvent(new SpaceConfEvent(space, token));
    }

    public static Type<SpaceConfHandler> getType() {
        return TYPE;
    }

    private cc.kune.core.client.sitebar.spaces.Space space;
    private String token;

    protected SpaceConfEvent() {
        // Possibly for serialization.
    }

    public SpaceConfEvent(final cc.kune.core.client.sitebar.spaces.Space space, final String token) {
        this.space = space;
        this.token = token;
    }

    @Override
    protected void dispatch(final SpaceConfHandler handler) {
        handler.onSpaceConf(this);
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
        final SpaceConfEvent other = (SpaceConfEvent) obj;
        if (space == null) {
            if (other.space != null) {
                return false;
            }
        } else if (!space.equals(other.space)) {
            return false;
        }
        if (token == null) {
            if (other.token != null) {
                return false;
            }
        } else if (!token.equals(other.token)) {
            return false;
        }
        return true;
    }

    @Override
    public Type<SpaceConfHandler> getAssociatedType() {
        return TYPE;
    }

    public cc.kune.core.client.sitebar.spaces.Space getSpace() {
        return space;
    }

    public String getToken() {
        return token;
    }

    @Override
    public int hashCode() {
        int hashCode = 23;
        hashCode = (hashCode * 37) + (space == null ? 1 : space.hashCode()) + (token == null ? 1 : token.hashCode());
        return hashCode;
    }

    @Override
    public String toString() {
        return "SpaceConfEvent[" + space + "/" + token + "]";
    }
}
