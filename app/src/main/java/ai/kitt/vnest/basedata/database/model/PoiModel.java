package ai.kitt.vnest.basedata.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import ai.kitt.vnest.basedata.entity.Poi;

import org.jetbrains.annotations.NotNull;

@Entity
public class PoiModel {
    @ColumnInfo
    private long timeMillis;
    @ColumnInfo
    private String img;
    @ColumnInfo
    private String address;
    @ColumnInfo
    @PrimaryKey
    @NotNull
    private String gps;
    @ColumnInfo
    private String title;
    @ColumnInfo
    private String url;
    @ColumnInfo
    private String phone;
    @ColumnInfo
    private String category;
    @ColumnInfo
    private String brand;
    @ColumnInfo
    private String hash;
    @ColumnInfo
    private String email;
    @ColumnInfo
    private double distance;

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void from(Poi poi, long time) {
        this.address = poi.getAddress();
        this.brand = poi.getBrand();
        this.category = poi.getCategory();
        this.distance = poi.getDistance();
        this.email = poi.getEmail();
        this.gps = poi.getGps().getLatitude() + "," + poi.getGps().getLongitude();
        this.hash = poi.getHash();
        this.img = poi.getImg();
        this.phone = poi.getPhone();
        this.title = poi.getTitle();
        this.url = poi.getUrl();
        this.timeMillis = time;
    }
}
