package org.ivan.entity.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.dd.plist.NSDictionary;
import com.dd.plist.PropertyListParser;


public class Plist {
    /**
     * 解压IPA文件，只获取IPA文件的Info.plist文件存储指定位置
     *
     * @param file           zip文件
     * @param unzipDirectory 解压到的目录
     * @throws Exception  异常
     */
    private static Map<String, Object> getZipInfo(File file, String unzipDirectory)
            throws Exception {
        Map<String, Object> resultRec = new HashMap<String, Object>();
        // 定义输入输出流对象
        InputStream input = null;
        OutputStream output = null;
        File result = null;


        File unzipFile = null;
        ZipFile zipFile = null;
        try {
            // 创建zip文件对象
            zipFile = new ZipFile(file, Charset.forName("UTF-8"));
            // 创建本zip文件解压目录
            String name = file.getName().substring(0, file.getName().lastIndexOf("."));
            unzipFile = new File(unzipDirectory + "/" + name);
            if (unzipFile.exists()) {
                unzipFile.delete();
            }
            unzipFile.mkdir();
            // 得到zip文件条目枚举对象
            Enumeration<? extends ZipEntry> zipEnum = zipFile.entries();
            // 定义对象
            ZipEntry entry = null;
            String entryName = null;
            String names[] = null;
            int length;
            // 循环读取条目
            while (zipEnum.hasMoreElements()) {
                // 得到当前条目
                entry = zipEnum.nextElement();
                entryName = new String(entry.getName());
                // 用/分隔条目名称
                names = entryName.split("\\/");
                length = names.length;
                for (int v = 0; v < length; v++) {
                    if (entryName.endsWith(".app/Info.plist")) { // 为Info.plist文件,则输出到文件
                        input = zipFile.getInputStream(entry);
                        result = new File(unzipFile.getAbsolutePath() + "/Info.plist");
                        output = new FileOutputStream(result);
                        byte[] buffer = new byte[1024 * 8];
                        int readLen = 0;
                        while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                            output.write(buffer, 0, readLen);
                        }
                        if (input != null)
                            input.close();
                        if (output != null) {
                            output.close();
                        }
//                        break;
                        resultRec.put("info", result);

                    }

                    if (entryName.endsWith("iTunesArtwork")) { // 为Info.plist文件,则输出到文件
                        input = zipFile.getInputStream(entry);
                        result = new File(unzipFile.getAbsolutePath() + "/icon.png");
                        output = new FileOutputStream(result);
                        byte[] buffer = new byte[1024 * 8];
                        int readLen = 0;
                        while ((readLen = input.read(buffer, 0, 1024 * 8)) != -1) {
                            output.write(buffer, 0, readLen);
                        }
                        if (input != null)
                            input.close();
                        if (output != null) {
                            output.close();
                        }
//                        break;
                        resultRec.put("icon", result);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            // 必须关流，否则文件无法删除
            if (zipFile != null) {
                zipFile.close();
            }
            // 如果有必要删除多余的文件
            if (file.exists()) {
                file.delete();
            }
            //删除解压出来的结果包
//            if(result.exists()){
//                result.delete();
//            }
        }

        return resultRec;
    }

    /**
     * 通过IPA文件获取Info信息
     * 这个方法可以重构，原因是指获取了部分重要信息，如果想要获取全部，那么应该返回一个Map<String,Object>
     * 对于plist文件中的数组信息应该序列化存储在Map中，那么只需要第三发jar提供的NSArray可以做到！
     */
    public static Map<String, String> getIpaInfoMap(File ipa) throws Exception {

        Map<String, String> map = new HashMap<String, String>();
        Map<String, Object> fileRec = getIpaInfo(ipa);
        if (null != fileRec.get("info")) {
            // 第三方jar包提供
            NSDictionary rootDict = (NSDictionary) PropertyListParser.parse((File) fileRec.get("info"));

            String name = null;
            if (rootDict.objectForKey("CFBundleDisplayName")!=null) {
                name = rootDict.objectForKey("CFBundleDisplayName").toString();
            } else if (rootDict.objectForKey("CFBundleName")!=null) {
                name = rootDict.objectForKey("CFBundleName").toString();
            }
//
            String versionCode = rootDict.objectForKey("CFBundleVersion").toString();

            String version = rootDict.objectForKey("CFBundleShortVersionString").toString();

            String packageName = rootDict.objectForKey("CFBundleIdentifier").toString();
           // rootDict.clear();

            map.put("name", name);
            map.put("version", version);
            map.put("versionCode", versionCode);
            map.put("packageName", packageName);
        }

        if (null != fileRec.get("icon")) {
            File file = (File) fileRec.get("icon");
            map.put("icon", file.getPath());
        }
        return map;
    }

    /**
     * IPA文件的拷贝，把一个IPA文件复制为Zip文件,同时返回Info.plist文件
     * 参数 oldfile 为 IPA文件
     */
    private static Map<String, Object> getIpaInfo(File oldfile) throws IOException {
        try {
            int byteread = 0;
            String filename = oldfile.getAbsolutePath().replaceAll(".ipa", ".zip");
            File newfile = new File(filename);
            if (oldfile.exists()) {
                // 创建一个Zip文件
                InputStream inStream = new FileInputStream(oldfile);
                FileOutputStream fs = new FileOutputStream(newfile);
                byte[] buffer = new byte[1444];
                while ((byteread = inStream.read(buffer)) != -1) {
                    fs.write(buffer, 0, byteread);
                }
                if (inStream != null) {
                    inStream.close();
                }
                if (fs != null) {
                    fs.close();
                }
                // 解析Zip文件
                return getZipInfo(newfile, newfile.getParent());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getXML(String versionName, String filesUrl, String packageName, String appVersionCode) {
        StringBuilder plistStr = new StringBuilder("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        plistStr.append("<!DOCTYPE plist PUBLIC \"-//Apple//DTD PLIST 1.0//EN\" \"http://www.apple.com/DTDs/PropertyList-1.0.dtd\">");
        plistStr.append("<plist version=\"" + versionName + "\">");
        plistStr.append("<dict>");
        plistStr.append("<key>items</key>");
        plistStr.append("<array>");
        plistStr.append("<dict>");
        plistStr.append("<key>assets</key>");
        plistStr.append("<array>");
        plistStr.append("<dict>");
        plistStr.append("<key>kind</key>");
        plistStr.append("<string>software-package</string>");
        plistStr.append("<key>url</key>");
        plistStr.append("<string>" + filesUrl + "</string>");
        plistStr.append("</dict>");
        plistStr.append("</array>");
        plistStr.append("<key>metadata</key>");
        plistStr.append("<dict>");
        plistStr.append("<key>bundle-identifier</key>");
        plistStr.append("<string>" + packageName + "</string>");
        plistStr.append("<key>bundle-version</key>");
        plistStr.append("<string>" + appVersionCode + "</string>");
        plistStr.append("<key>kind</key>");
        plistStr.append("<string>software</string>");
        plistStr.append("<key>title</key>");
        plistStr.append("<string>iZichanSalary</string>");
        plistStr.append("</dict>");
        plistStr.append("</dict>");
        plistStr.append("</array>");
        plistStr.append("</dict>");
        plistStr.append("</plist>");
        return plistStr.toString();
    }

    public static void main(String[] args) throws Exception {
        File file = new File("d:/com.tencent.xin_6.0.2.22.ipa");
        Map<String, String> map = getIpaInfoMap(file);
        for (String key : map.keySet()) {
            System.out.println(key + " : " + map.get(key));
        }

    }
}