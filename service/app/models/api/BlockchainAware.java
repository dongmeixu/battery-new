package models.api;

import plugins.BlockchainPlugin;

import static plugins.BlockchainPlugin.getBlockchain;

/**
 * Blockchain aware interface
 * Created by zhaoxy on 20/01/2017.
 */
public interface BlockchainAware extends Jsonable {
    //todo: implementation
    default void saveToBlockchain(String token){
        getBlockchain().save(token,this);
    }
    default String queryFromBlockchain(String token,String filters,String params){
        return "";
    }
    default boolean isTrusted(String token){
        return true;
    }

    //todo: implementation
    static void saveToBlockchain(String token,Jsonable object){
        getBlockchain().save(token,object);
    }
    static <T>  T queryFromBlockchain(String token,Class<T> clazz,String filters,String params){
        return null;
    }
    static boolean isTrusted(String token,Jsonable jsonable){
        return true;
    }
}
