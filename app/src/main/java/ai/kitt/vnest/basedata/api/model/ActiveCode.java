package ai.kitt.vnest.basedata.api.model;

public class ActiveCode {
    private String phone, activationCode, imei, deviceId;

    public ActiveCode(String phone, String activationCode, String imei, String deviceId) {
        this.phone = phone;
        this.activationCode = activationCode;
        this.imei = imei;
        this.deviceId = deviceId;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getActivationCode() {
        return activationCode;
    }

    public void setActivationCode(String activationCode) {
        this.activationCode = activationCode;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
