package ai.kitt.vnest.util.event;

public class ContactSearchEvent {
    String query;

    public ContactSearchEvent(String str) {
        this.query = str;
    }

    public String getQuery() {
        return this.query;
    }
}
