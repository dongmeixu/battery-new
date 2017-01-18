package plugins;

import play.Logger;
import play.PlayPlugin;

/**
 * block chain plugin
 * Created by zhaoxy on 18/01/2017.
 */
public class BlockchainPlugin extends PlayPlugin{
    //todo: implemention
    @Override
    public void onApplicationStart() {
        Logger.info("Starting BlockchainPlugin...");
    }

    @Override
    public void onApplicationStop() {
        Logger.info("BlockchainPlugin stopped.");
    }
}
