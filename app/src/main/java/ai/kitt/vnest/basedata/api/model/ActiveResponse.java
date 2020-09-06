package ai.kitt.vnest.basedata.api.model;

public class ActiveResponse {
    //    {"code":"1","description":"OK","notchangesessionid":false}
    private String code, description;
    private boolean notchangesessionid;

    public ActiveResponse() {
    }

    public ActiveResponse(String code, String description, boolean notchangesessionid) {
        this.code = code;
        this.description = description;
        this.notchangesessionid = notchangesessionid;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isActivated() {
        return !getCode().endsWith("0");
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isNotchangesessionid() {
        return notchangesessionid;
    }

    public void setNotchangesessionid(boolean notchangesessionid) {
        this.notchangesessionid = notchangesessionid;
    }
}
