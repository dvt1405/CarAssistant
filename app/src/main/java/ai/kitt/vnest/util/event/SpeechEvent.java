package ai.kitt.vnest.util.event;

public class SpeechEvent {
    boolean processed;
    String text;

    public SpeechEvent(String str) {
        this.text = str;
        this.processed = false;
    }

    public SpeechEvent(String str, boolean z) {
        this.text = str;
        this.processed = z;
    }

    public boolean isProcessed() {
        return this.processed;
    }

    public String getText() {
        return this.text;
    }
}
