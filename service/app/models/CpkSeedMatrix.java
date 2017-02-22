package models;

import models.api.Jsonable;
import play.data.validation.Required;
import play.modules.jongo.BaseModel;

import java.util.Date;

/**
 * Created by Shaojie Ye on 2017/2/22.
 */
public class CpkSeedMatrix extends BaseModel implements Jsonable {
    @Required

    private String CpkId;
    @Required
    private Date enableTime; //����ʱ��
    @Required
    private String pkMatrixJson; //��Կ���Ӿ���
    @Required
    private String skMatrixJson; //˽Կ���Ӿ���

    private Date expireTime; //����ʱ��

    public String getCpkId() {
        return CpkId;
    }

    public void setCpkId(String cpkId) {
        CpkId = cpkId;
    }

    public Date getEnableTime() {
        return enableTime;
    }

    public void setEnableTime(Date enableTime) {
        this.enableTime = enableTime;
    }

    public String getPkMatrixJson() {
        return pkMatrixJson;
    }

    public void setPkMatrixJson(String pkMatrixJson) {
        this.pkMatrixJson = pkMatrixJson;
    }

    public String getSkMatrixJson() {
        return skMatrixJson;
    }

    public void setSkMatrixJson(String skMatrixJson) {
        this.skMatrixJson = skMatrixJson;
    }

    public Date getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(Date expireTime) {
        this.expireTime = expireTime;
    }


    private static String version = "1.0.0";
}
