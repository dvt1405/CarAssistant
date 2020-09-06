package ai.kitt.vnest.util.event;


import ai.kitt.vnest.App;

public class OtaUpdateEvent {
    App app;
    String path;

    public OtaUpdateEvent(App app2, String str) {
        this.app = app2;
        this.path = str;
    }

    public App getApp() {
        return this.app;
    }

    public String getPath() {
        return this.path;
    }
}
