package plugins;

import models.api.Jsonable;
import play.Logger;
import play.PlayPlugin;

/**
 * block chain plugin
 * Created by zhaoxy on 18/01/2017.
 */
public class BlockchainPlugin extends PlayPlugin{
    //todo: implementation
    static volatile Blockchain blockchain;
    @Override
    public void onApplicationStart() {
        Logger.info("Starting BlockchainPlugin...");
        //todo:
        Logger.info("JongoPlugin started.");
    }

    @Override
    public void onApplicationStop() {
        Logger.info("BlockchainPlugin stopped.");
    }

    public static Blockchain getBlockchain() {
        if (blockchain != null)
            return blockchain;
        else {

            throw new RuntimeException("BlockchainPlugin not ready");
        }
    }

    public static class Blockchain {
        //todo: implementation
        public static  void save(String token,Jsonable obj){
            //todo:
        }

    }
}
