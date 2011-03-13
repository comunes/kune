package cc.kune.core.shared.dto;

import com.google.gwt.user.client.rpc.IsSerializable;

public class WaveClientParams implements IsSerializable {
    private String clientFlags;
    private String sessionJSON;
    private boolean useSocketIO;

    public WaveClientParams() {
    }

    public WaveClientParams(final String sessionJSON, final String clientFlags, final boolean useSocketIO) {
        this.sessionJSON = sessionJSON;
        this.clientFlags = clientFlags;
        this.useSocketIO = useSocketIO;
    }

    public String getClientFlags() {
        return clientFlags;
    }

    public String getSessionJSON() {
        return sessionJSON;
    }

    public boolean useSocketIO() {
        return useSocketIO;
    }

    public void setClientFlags(final String clientFlags) {
        this.clientFlags = clientFlags;
    }

    public void setSessionJSON(final String sessionJSON) {
        this.sessionJSON = sessionJSON;
    }

    public void setUseSocketIO(final boolean useSocketIO) {
        this.useSocketIO = useSocketIO;
    }

}