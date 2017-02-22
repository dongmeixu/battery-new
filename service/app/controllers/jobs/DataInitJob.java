package controllers.jobs;

import edu.ustb.security.domain.vo.matrix.CpkMatrix;
import edu.ustb.security.service.ecc.CpkMatrixsFactory;
import edu.ustb.security.service.ecc.impl.CpkCoresImpl;
import models.CpkSeedMatrix;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

import java.util.Date;

import static play.modules.jongo.BaseModel.getCollection;

/**
 * Created by Shaojie Ye on 2017/2/22.
 */
@OnApplicationStart
public class DataInitJob extends Job {
    private static CpkMatrix pkMatrix = null;
    private static CpkCoresImpl pkCores = null;
    private static CpkCoresImpl skCores = null;
    private static CpkMatrix skMatrix = null;
    @Override
    public void doJob() {
        CpkSeedMatrix cpkSeedMatrixdb = getCollection(CpkSeedMatrix.class).findOne("{CpkId:#}", "12345").as(CpkSeedMatrix.class);
        if (cpkSeedMatrixdb == null) {
            //生成种子矩阵
            pkMatrix = CpkMatrixsFactory.generateCpkMatrix();
            //实例化cpkCores核心类
            pkCores = new CpkCoresImpl(pkMatrix);
            // 种子矩阵序列化为仅包含公钥种子矩阵的JSON字符串
            String pkMatrixJson = pkMatrix.toPubJson();

            skMatrix = CpkMatrixsFactory.generateCpkMatrix();
            // 通过种子私钥得到CpkCores 算法核心类
            skCores = new CpkCoresImpl(skMatrix);
            // 种子矩阵序列化为仅包含私钥种子矩阵的JSON字符串
            String skMatrixJson = skMatrix.toSecJson();

            CpkSeedMatrix cpkSeedMatrix = new CpkSeedMatrix();
            cpkSeedMatrix.setPkMatrixJson(pkMatrixJson);
            cpkSeedMatrix.setSkMatrixJson(skMatrixJson);
            cpkSeedMatrix.setEnableTime(new Date());//expireDate也需添加
            cpkSeedMatrix.setCpkId("12345");
            getCollection(CpkSeedMatrix.class).save(cpkSeedMatrix);
        }
    }
}
