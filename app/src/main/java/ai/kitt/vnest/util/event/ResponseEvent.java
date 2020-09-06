package ai.kitt.vnest.util.event;

public class ResponseEvent {
    private boolean complete;
    private String response;

    public ResponseEvent(String str) {
        this.response = str;
        this.complete = true;
    }

    public ResponseEvent(String str, boolean z) {
        this.response = str;
        this.complete = z;
    }

    public boolean isCompleted() {
        return this.complete;
    }

    public String getResponse() {
        return this.response;
    }
}
