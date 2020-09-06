package ai.kitt.vnest.basedata.api.model;

public class UpdateInfo {
    private String deviceId, bks;

    public UpdateInfo(String deviceId,String bks) {
        this.bks = bks;
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getBks() {
        return bks;
    }

    public void setBks(String bks) {
        this.bks = bks;
    }
}
