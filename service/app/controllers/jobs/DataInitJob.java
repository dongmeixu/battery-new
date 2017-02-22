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
            //�������Ӿ���
            pkMatrix = CpkMatrixsFactory.generateCpkMatrix();
            //ʵ����cpkCores������
            pkCores = new CpkCoresImpl(pkMatrix);
            // ���Ӿ������л�Ϊ��������Կ���Ӿ����JSON�ַ���
            String pkMatrixJson = pkMatrix.toPubJson();

            skMatrix = CpkMatrixsFactory.generateCpkMatrix();
            // ͨ������˽Կ�õ�CpkCores �㷨������
            skCores = new CpkCoresImpl(skMatrix);
            // ���Ӿ������л�Ϊ������˽Կ���Ӿ����JSON�ַ���
            String skMatrixJson = skMatrix.toSecJson();

            CpkSeedMatrix cpkSeedMatrix = new CpkSeedMatrix();
            cpkSeedMatrix.setPkMatrixJson(pkMatrixJson);
            cpkSeedMatrix.setSkMatrixJson(skMatrixJson);
            cpkSeedMatrix.setEnableTime(new Date());//expireDateҲ�����
            cpkSeedMatrix.setCpkId("12345");
            getCollection(CpkSeedMatrix.class).save(cpkSeedMatrix);
        }
    }
}
