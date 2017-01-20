package edu.ustb.security.domain.vo.matrix;

/**
 * Created by sunyichao on 2017/1/20.
 * 公钥矩阵中的公钥对象
 */
public class PubPoint {
    private Integer axisX;//矩阵X轴
    private Integer axisY;//矩阵Y轴
    private String publicKeyX;//公钥x坐标32进制
    private String publicKeyY;//公钥y坐标32进制

    public PubPoint() {
    }

    public PubPoint(Integer axisX, Integer axisY, String publicKeyX, String publicKeyY) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.publicKeyX = publicKeyX;
        this.publicKeyY = publicKeyY;
    }

    public Integer getAxisX() {
        return axisX;
    }

    public void setAxisX(Integer axisX) {
        this.axisX = axisX;
    }

    public Integer getAxisY() {
        return axisY;
    }

    public void setAxisY(Integer axisY) {
        this.axisY = axisY;
    }

    public String getPublicKeyX() {
        return publicKeyX;
    }

    public void setPublicKeyX(String publicKeyX) {
        this.publicKeyX = publicKeyX;
    }

    public String getPublicKeyY() {
        return publicKeyY;
    }

    public void setPublicKeyY(String publicKeyY) {
        this.publicKeyY = publicKeyY;
    }
}
