package edu.ustb.security.domain.vo.matrix;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

/**
 * Created by sunyichao on 2017/1/20.
 */
public class CpkMatrix {
    private int matrixField;//矩阵应用领域
    private int ecType;//矩阵依赖曲线
    private PubPoint[] pubPoints;
    private SecPoint[] secPoints;

    public CpkMatrix() {
    }

    public CpkMatrix(int matrixField, int ecType, PubPoint[] pubPoints, SecPoint[] secPoints) {
        this.matrixField = matrixField;
        this.ecType = ecType;
        this.pubPoints = pubPoints;
        this.secPoints = secPoints;
    }

    public int getMatrixField() {
        return matrixField;
    }

    public void setMatrixField(int matrixField) {
        this.matrixField = matrixField;
    }

    public int getEcType() {
        return ecType;
    }

    public void setEcType(int ecType) {
        this.ecType = ecType;
    }

    public PubPoint[] getPubPoints() {
        return pubPoints;
    }

    public void setPubPoints(PubPoint[] pubPoints) {
        this.pubPoints = pubPoints;
    }

    public SecPoint[] getSecPoints() {
        return secPoints;
    }

    public void setSecPoints(SecPoint[] secPoints) {
        this.secPoints = secPoints;
    }

    /**
     * 矩阵对象转化为Json字符串
     *
     * @return CpkMatrixs Json
     */
    public String toJson() {
        return JSON.toJSONString(this);
    }

    /**
     * 种子矩阵导出仅包含公钥矩阵Json字符串
     *
     * @return 仅包含公钥矩阵Json字符串
     */
    public String toPubJson() {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().add("secPoints");
        return JSON.toJSONString(this, simplePropertyPreFilter);
    }

    /**
     * 种子矩阵导出仅包含私钥矩阵Json字符串
     *
     * @return 仅包含私钥矩阵Json字符串
     */
    public String toSecJson() {
        SimplePropertyPreFilter simplePropertyPreFilter = new SimplePropertyPreFilter();
        simplePropertyPreFilter.getExcludes().add("pubPoints");
        return JSON.toJSONString(this, simplePropertyPreFilter);
    }

    /**
     * 从CpkMatrixs Json 反序列化得到matrixs对象
     *
     * @param cpkMatrixsJson matrixs对象
     * @return
     */
    public static CpkMatrix fromJson(String cpkMatrixsJson) {
        return JSON.parseObject(cpkMatrixsJson, CpkMatrix.class);
    }
}
