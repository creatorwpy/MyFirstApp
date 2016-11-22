package library.mlibrary.util.db;

import java.io.File;

/**
 * Created by Harmy on 2016/4/8 0008.
 */
public class DbUtil {

    public static DbManager getDb(String dbname){
        return getDb(null,dbname,1,true,null,null,null);
    }

    public static DbManager getDb(File dir, String dbname){
        return getDb(dir,dbname,1,true,null,null,null);
    }

    public static DbManager getDb(File dir, String dbname, int version){
        return getDb(dir,dbname,version,true,null,null,null);
    }
    public static DbManager getDb(File dir, String dbname, int version, DbManager.DbUpgradeListener dbUpgradeListener){
        return getDb(dir,dbname,version,true,null,null,dbUpgradeListener);
    }
    public static DbManager getDb(File dir, String dbname, boolean allowTransaction){
        return getDb(dir,dbname,1,allowTransaction,null,null,null);
    }
    public static DbManager getDb(File dir, String dbname, DbManager.DbOpenListener dbOpenListener){
        return getDb(dir,dbname,1,true,null,dbOpenListener,null);
    }
    public static DbManager getDb(File dir, String dbname, DbManager.TableCreateListener tableCreateListener){
        return getDb(dir,dbname,1,true,tableCreateListener,null,null);
    }

    public static DbManager getDb(String dbname, int version){
        return getDb(null,dbname,version,true,null,null,null);
    }
    public static DbManager getDb(String dbname, int version, DbManager.DbUpgradeListener dbUpgradeListener){
        return getDb(null,dbname,version,true,null,null,dbUpgradeListener);
    }
    public static DbManager getDb(String dbname, int version, DbManager.TableCreateListener tableCreateListener){
        return getDb(null,dbname,version,true,tableCreateListener,null,null);
    }
    public static DbManager getDb(String dbname, int version, DbManager.DbOpenListener dbOpenListener){
        return getDb(null,dbname,version,true,null,dbOpenListener,null);
    }

    public static DbManager getDb(String dbname, int version, boolean allowTransaction){
        return getDb(null,dbname,version,allowTransaction,null,null,null);
    }

    public static DbManager getDb(String dbname, boolean allowTransaction){
        return getDb(null,dbname,1,allowTransaction,null,null,null);
    }
    public static DbManager getDb(String dbname, DbManager.TableCreateListener tableCreateListener){
        return getDb(null,dbname,1,true,tableCreateListener,null,null);
    }

    public static DbManager getDb(String dbname, DbManager.DbOpenListener dbOpenListener){
        return getDb(null,dbname,1,true,null,dbOpenListener,null);
    }


    public static DbManager getDb(File dir, String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener) {
        return getDb(dir,dbname,version,allowTransaction,tableCreateListener,dbOpenListener,null);
    }

    public static DbManager getDb(File dir, String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(dir,dbname,version,allowTransaction,tableCreateListener,null,dbUpgradeListener);
    }

    public static DbManager getDb(File dir, String dbname, int version, boolean allowTransaction, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(dir,dbname,version,allowTransaction,null,dbOpenListener,dbUpgradeListener);
    }

    public static DbManager getDb(File dir, String dbname, int version, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(dir,dbname,version,true,tableCreateListener,dbOpenListener,dbUpgradeListener);
    }

    public static DbManager getDb(File dir, String dbname, int version, DbManager.TableCreateListener tableCreateListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(dir,dbname,version,true,tableCreateListener,null,dbUpgradeListener);
    }

    public static DbManager getDb(File dir, String dbname, int version, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(dir,dbname,version,true,null,dbOpenListener,dbUpgradeListener);
    }

    public static DbManager getDb(String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(null,dbname,version,allowTransaction,tableCreateListener,dbOpenListener,dbUpgradeListener);
    }

    public static DbManager getDb(String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener) {
        return getDb(null,dbname,version,allowTransaction,tableCreateListener,dbOpenListener,null);
    }
    public static DbManager getDb(String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(null,dbname,version,allowTransaction,tableCreateListener,null,dbUpgradeListener);
    }
    public static DbManager getDb(String dbname, int version, boolean allowTransaction, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(null,dbname,version,allowTransaction,null,dbOpenListener,dbUpgradeListener);
    }
    public static DbManager getDb(String dbname, int version, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        return getDb(null,dbname,version,true,tableCreateListener,dbOpenListener,dbUpgradeListener);
    }
    public static DbManager getDb(File dir, String dbname, int version, boolean allowTransaction, DbManager.TableCreateListener tableCreateListener, DbManager.DbOpenListener dbOpenListener, DbManager.DbUpgradeListener dbUpgradeListener) {
        DbManager.DaoConfig config=new DbManager.DaoConfig();
        if(dir!=null){
            config.setDbDir(dir);
        }
        config.setDbVersion(version);
        config.setAllowTransaction(allowTransaction);
        if(tableCreateListener!=null){
            config.setTableCreateListener(tableCreateListener);
        }
        if(dbOpenListener!=null) {
            config.setDbOpenListener(dbOpenListener);
        }
        if(dbUpgradeListener!=null){
            config.setDbUpgradeListener(dbUpgradeListener);
        }
        config.setDbName(dbname);
        return getDb(config);
    }

    public static DbManager getDb(DbManager.DaoConfig daoConfig) {
        return DbManagerImpl.getInstance(daoConfig);
    }
}
