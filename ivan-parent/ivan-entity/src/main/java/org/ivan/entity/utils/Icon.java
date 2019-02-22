package org.ivan.entity.utils;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;




public class Icon {
    private static final String PAGE_NAME = "package";
    private static final String VERSION_NAME = "versionName";
    private static final String VERSION_CODE = "versionCode";
    private static final String APPLICATION = "application:";
    private static final String APP_NAME = "appName";
    private static final String APP_ICON = "appIcon";
    private static final String APP_TYPE = "appType";

    /**
     * 获取aapt的路径(linux和windows使用不同的aapt)
     *
     * @return String
     */
    private static String getAaptPath() {

        String aaptPath = null;
        Properties prop = System.getProperties();
        String os = prop.getProperty("os.name");
        if (StringUtils.isNotBlank(ReadPro.getValue("aaptURL"))) {
            aaptPath = ReadPro.getValue("aaptURL");
        } else {
            //判断操作系统
            if (StringUtils.isNotBlank(os) && os.toLowerCase().startsWith("win")) {
                //windows系统
                aaptPath = PathKit.getRootClassPath() + File.separator + "aapt.exe";
                aaptPath = "\"" + aaptPath + "\"";
            } else {
                //linux系统
                aaptPath = PathKit.getRootClassPath() + File.separator + "aapt";
            }
        }


        return aaptPath;
    }

//    public static void main(String args[]) {
//
//        Map<String, String> map = getParams("e:/qunaerlvxing_121.apk");
//        System.out.println(PAGE_NAME + ":" + map.get(PAGE_NAME));
//        System.out.println(VERSION_NAME + ":" + map.get(VERSION_NAME));
//        System.out.println(VERSION_CODE + ":" + map.get(VERSION_CODE));
//        System.out.println(APP_NAME + ":" + map.get(APP_NAME));
//        System.out.println(APP_ICON + ":" + map.get(APP_ICON));
//
//    }

    public static Map<String, String> getParams(String apkPath) {
        int subIndex = apkPath.lastIndexOf(".");
        Map<String, String> map = new HashMap<String, String>();
        if (apkPath.substring(subIndex).equalsIgnoreCase(".ipa")) {
            Map<String, String> ipaMap = new HashMap<String, String>();
            try {
                ipaMap = Plist.getIpaInfoMap(new File(apkPath));
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            map.put(APP_NAME, ipaMap.get("name"));
            map.put(VERSION_NAME, ipaMap.get("version"));
            map.put(VERSION_CODE, ipaMap.get("versionCode"));
            map.put(PAGE_NAME, ipaMap.get("packageName"));
            map.put(APP_ICON, ipaMap.get("icon"));
            map.put(APP_TYPE, "1");
            return map;
        }
        try {
            map.put(APP_TYPE, "0");
            Runtime rt = Runtime.getRuntime();
            String order = getAaptPath() + " d badging " + apkPath + "";
            System.out.println("---------------order:" + order);
            Process proc = rt.exec(order);
            InputStream inputStream = proc.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));

            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(VERSION_NAME)) {
                    String[] str = StringUtils.substringsBetween(line, "'", "'");
                    map.put(PAGE_NAME, str[0]);
                    map.put(VERSION_CODE, str[1]);
                    map.put(VERSION_NAME, str[2]);
                } else if (line.contains(APPLICATION)) {
                    String[] str = StringUtils.substringsBetween(line, "'", "'");
                    map.put(APP_NAME, str[0]);
                    map.put(APP_ICON, str[1].substring(str[1].lastIndexOf("/") + 1));
                }
            }
            if (null != inputStream) {
                inputStream.close();
            }
            if (null != bufferedReader) {
                bufferedReader.close();
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return map;
    }

    /**
     * @author mac_chen apkPath : apk路径 apkoldName : apk名称 path ：apk文件输出路径
     */
    public static void getIcon(String apkPath, String apkoldName, String path, HttpServletRequest request) {
        FileInputStream in = null;
        FileOutputStream out = null;
        ZipInputStream zipin = null;

        File apkFile = new File(apkPath);
        try {
            in = new FileInputStream(apkFile);
            zipin = new ZipInputStream(in);
            ZipEntry entry = null;
            while ((entry = zipin.getNextEntry()) != null) {
                String name = entry.getName().toLowerCase();
                if (name.contains(apkoldName)) {
                    File icon = new File(path + "\\" + "icon.png");
                    File parent = icon.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    out = new FileOutputStream(icon);

                    byte[] buff = new byte[1024];
                    int n = 0;
                    while ((n = zipin.read(buff, 0, buff.length)) != -1) {
                        out.write(buff, 0, n);
                    }
                    break;
                }
            }
            if (out == null) {
                byte[] buf = new byte[1024];
                int len = 0;
                String WebRoots = request.getSession().getServletContext().getRealPath("");
                in = new FileInputStream(new File(WebRoots + "/images/icon.png"));
                out = new FileOutputStream(new File(path + "\\" + "icon.png"), true);
                while ((len = in.read(buf)) != -1) {
                    out.write(buf, 0, len);
                }
                out.close();
            } else {
                out.close();
            }
            zipin.closeEntry();
            entry = null;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zipin != null) {
                    zipin.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}