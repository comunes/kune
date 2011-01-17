package cc.kune.common.client.errors;

import com.google.gwt.core.client.GWT;

public class NotImplementedException extends RuntimeException {

    private static final long serialVersionUID = -1327164226202923181L;

    public NotImplementedException() {
        super();
        GWT.log("NotImplementedException");
    }

    public NotImplementedException(final String text) {
        super(text);
        GWT.log(text);
    }

    public NotImplementedException(final String text, final Throwable cause) {
        super(text, cause);
        GWT.log(text, cause);
    }

    public NotImplementedException(final Throwable cause) {
        super(cause);
        GWT.log("NotImplementedException", cause);
    }

}