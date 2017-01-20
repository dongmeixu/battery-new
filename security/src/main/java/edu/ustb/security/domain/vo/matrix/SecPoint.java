package edu.ustb.security.domain.vo.matrix;

/**
 * Created by sunyichao on 2017/1/20.
 * 私钥矩阵中的私钥对象
 */
public class SecPoint {
    private Integer axisX;//矩阵X轴
    private Integer axisY;//矩阵Y轴
    private String privateKey;//私钥32进制

    public SecPoint() {
    }

    public SecPoint(Integer axisX, Integer axisY, String privateKey) {
        this.axisX = axisX;
        this.axisY = axisY;
        this.privateKey = privateKey;
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }
}
