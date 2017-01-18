package models;

import models.api.Jsonable;
import play.modules.jongo.BaseModel;

/**
 * 分支机构
 * Created by zxy on 2017/1/18.
 */

public class Branch implements Jsonable {
    String name;
    String province;
    String city;
    String dom;
    double longitude;
    double latitude;
    String contact;
    String phone;
    /**
     * 分支机构状态, 0: 停业, 1:正常
     */
    int status=1;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
