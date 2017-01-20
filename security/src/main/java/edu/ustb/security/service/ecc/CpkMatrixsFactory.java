package edu.ustb.security.service.ecc;

import edu.ustb.security.domain.vo.ecc.ECKey;
import edu.ustb.security.domain.vo.ecc.ECPoint;
import edu.ustb.security.domain.vo.ecc.Key;
import edu.ustb.security.domain.vo.ecc.elliptic.EllipticCurve;
import edu.ustb.security.domain.vo.ecc.elliptic.InsecureCurveException;
import edu.ustb.security.domain.vo.ecc.elliptic.secp256r1;
import edu.ustb.security.domain.vo.matrix.*;

import java.math.BigInteger;

/**
 * Created by sunyichao on 2017/1/14.
 * 生成种子矩阵工厂类
 */
public class CpkMatrixsFactory {
    /**
     * 生成种子矩阵
     *
     * @return 种子矩阵对象
     */
    public static CpkMatrix generateCpkMatrix() {
        EllipticCurve ellipticCurve = null;
        try {
            ellipticCurve = new EllipticCurve(new secp256r1());
        } catch (InsecureCurveException e) {
            e.printStackTrace();
        }
        return generateCpkMatrix(ellipticCurve);
    }

    /**
     * 生成种子矩阵
     *
     * @param ellipticCurve 指定曲线
     * @return 种子矩阵对象
     */
    public static CpkMatrix generateCpkMatrix(EllipticCurve ellipticCurve) {
        if (ellipticCurve != null) {
            CpkMatrix cpkMatrix = new CpkMatrix();
            PubPoint[] pubPoints = new PubPoint[1024];
            SecPoint[] secPoints = new SecPoint[1024];
            int k = 0;
            for (int i = 0; i < 32; i++) {
                for (int j = 0; j < 32; j++) {
                    Key key = new ECKey(ellipticCurve);
                    BigInteger sk = key.getSk();
                    ECPoint pk = key.getPk();
                    pubPoints[k] = new PubPoint(i, j, pk.getx().toString(32), pk.gety().toString(32));
                    secPoints[k] = new SecPoint(i, j, sk.toString(32));
                    k++;
                }
            }
            cpkMatrix.setPubPoints(pubPoints);
            cpkMatrix.setSecPoints(secPoints);
            return cpkMatrix;
        }
        return null;
    }



}
