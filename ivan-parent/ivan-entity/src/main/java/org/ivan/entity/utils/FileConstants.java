package org.ivan.entity.utils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
/**
 * <P>ClassName: FileConstants</P>
 * <P>@Description: TODO</P>
 * <P>Company:</P>
 *
 * @author pengzhihao
 * 2016-5-14下午3:34:34
 */
public class FileConstants {

    //根目录
    private static String nfsShareRoot = ReadPro.getValue("nfs_share_root");

    //总目录
    private static String nfsShareDir = ReadPro.getValue("nfs_share_dir");

    //点游模块目录
    private static String nfsShareDianyou = ReadPro.getValue("nfs_share_dianyou");

    //点游模块的图片文件目录
    private static String nfsShareDianyouImg = ReadPro.getValue("nfs_share_dianyou_img");

    private static FileConstants instance;

    private Map<String, String> nfsShareDirMap;

    /**
     * 点游模块模块的图片文件目录类型
     */
    public static final String NFS_SHARE_TYPE_20001 = "20001";

    //私有构造
    private FileConstants() {

    }

    public static FileConstants getInstance() {
        if (instance == null) {
            instance = new FileConstants();
            //初始化
            instance.init();
        }
        return instance;
    }

    private void init() {
        nfsShareDirMap = new HashMap<String, String>();
        nfsShareDirMap.put("20001", getNfsShareDianyouImg());   //点游模块的图片文件目录类型
    }

    private static String getDateDir() {
        return DateToString(new Date()) + File.separator;
    }

    private static String DateToString(Date date) {
        String dateString = null;
        if (date != null) {
            try {
                dateString = getDateFormat("yyyyMMdd").format(date);
            } catch (Exception ignored) {

            }
        }
        return dateString;
    }

    private static SimpleDateFormat getDateFormat(String parttern)
            throws RuntimeException {
        return new SimpleDateFormat(parttern);
    }

    /**
     * NFS共享根目录
     * @return String
     */
    public static String getNfsShareRoot() {
        return nfsShareRoot;
    }


    /**
     * NFS共享文件存放总目录
     * @return String
     */
    private static String getNfsShareDir() {
        return nfsShareDir;
    }

    /**
     * NFS共享文件存放总目录  + Date时间目录
     * @return String
     */
    private static String getNfsShare() {
        return getNfsShareDir() + getDateDir();
    }


    /**
     * NFS共享点游模块图片文件存放目录  + Date时间目录
     *
     * @return String
     */
    private static String getNfsShareDianyouImg() {
        return getNfsShareDir() + nfsShareDianyou + nfsShareDianyouImg + getDateDir();
    }

    /**
     * 返回文件目录
     * @param type 类型
     * @return String
     */
    public String getFileDir(String type) {
        if (StringUtils.isBlank(type)) {
            return getNfsShare();
        }
        String fileDir = getNfsShareDirMap().get(type);
        if (StringUtils.isBlank(fileDir)) {
            fileDir = getNfsShare();
        }
        return fileDir;
    }

    private Map<String, String> getNfsShareDirMap() {
        return nfsShareDirMap;
    }

}
