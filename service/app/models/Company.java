package models;

import com.alibaba.fastjson.annotation.JSONField;
import models.api.BlockchainAware;
import models.api.Jsonable;
import play.data.validation.Required;
import play.modules.jongo.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 证书的数据模型
 * Created by xudongmei on 2016/12/13.
 */

public class Company extends BaseModel implements Jsonable,BlockchainAware {
    @Required
    public String companyId;
    /**
     * 企业类型 0：电池厂 1：汽车厂 2:4S店
      */
    @Required
    public Integer companyType;

    @Required
    public String companyName;
    /**
     * 企业所在省份
     */
    public String province;
    /**
     * 企业所在城市
     */
    public String city;
    /**
     * 企业所在地址
     */
    String dom;
    double longitude;
    double latitude;
    /**
     * 社会信用码,也作为登录系统的账户名
     */
    @Required
    public String creditCode;
    /**
     * 厂商代码（汽车厂不需要填写）
     */
    @Required
    public String vendorCode;

    public String contact;

    @Required
    public String phone;

    @Required
    public String email;
    /**
     * 营业执照证书图片的Base64格式
     */
    public String  licensePicture;

    List<Branch> branches = new ArrayList<>();
    /**
     * 申请证书的状态c
     * 0：未审核 1：审核通过 2：审核不通过
     */
    @Required
    public Integer status = 0;
    /**
     * 登录认证中心系统用的密码
     */
    @JSONField(serialize = false)
    public String password;
    /**
     * 交易用私钥证书
     */
    public String tradeSK;
    /**
     * 产品二维码用私钥证书
     */
    public String productSK;


    public String certRemark;

    public Integer getCompanyType() {
        return companyType;
    }

    public void setCompanyType(Integer companyType) {
        this.companyType = companyType;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getVendorCode() {
        return vendorCode;
    }

    public void setVendorCode(String vendorCode) {
        this.vendorCode = vendorCode;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLicensePicture() {
        return licensePicture;
    }

    public void setLicensePicture(String licensePicture) {
        this.licensePicture = licensePicture;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }



    public String getCertRemark() {
        return certRemark;
    }

    public void setCertRemark(String certRemark) {
        this.certRemark = certRemark;
    }

    public String getTradeSK() {
        return tradeSK;
    }

    public void setTradeSK(String tradeSK) {
        this.tradeSK = tradeSK;
    }

    public String getProductSK() {
        return productSK;
    }

    public void setProductSK(String productSK) {
        this.productSK = productSK;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDom() {
        return dom;
    }

    public void setDom(String dom) {
        this.dom = dom;
    }

    public List<Branch> getBranches() {
        return branches;
    }

    public void setBranches(List<Branch> branches) {
        this.branches = branches;
    }
}
