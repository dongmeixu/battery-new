package models;

import play.data.validation.Required;
import play.modules.jongo.BaseModel;

import java.util.Date;

/**
 * Created by xudongmei on 2017/1/14.
 */
public class SeedMatrix extends BaseModel {

    @Required
    private String certId; //对应的证书Id
    @Required
    private String matrix; //种子矩阵
    @Required
    private Date enableTime; //启用时间
    @Required
    private Date expireTime; //到期时间
    private static String version = "1.0.0";

    public String getCertId() {
        return certId;
    }

    public void setCertId(String certId) {
        this.certId = certId;
    }

    public String getMatrix() {
        return matrix;
    }

    public void setMatrix(String matrix) {
        this.matrix = matrix;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }

    public static String getVersion() {
        return version;
    }

    public static void setVersion(String version) {
        SeedMatrix.version = version;
    }
}
