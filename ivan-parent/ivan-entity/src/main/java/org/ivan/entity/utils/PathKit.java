package org.ivan.entity.utils;

import java.io.File;

public class PathKit {
    private static String webRootPath;
    private static String rootClassPath;

    public static String getPath(Class clazz) {
        String path = clazz.getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getPath(Object object) {
        String path = object.getClass().getResource("").getPath();
        return new File(path).getAbsolutePath();
    }

    public static String getRootClassPath() {
        if (rootClassPath == null) {
            try {
                String path = PathKit.class.getClassLoader().getResource("")
                        .toURI().getPath();
                rootClassPath = new File(path).getAbsolutePath();
            } catch (Exception e) {
                String path = PathKit.class.getClassLoader().getResource("")
                        .getPath();
                rootClassPath = new File(path).getAbsolutePath();
            }
        }
        return rootClassPath;
    }

    public void setRootClassPath(String rootClassPath) {
        rootClassPath = rootClassPath;
    }

    public static String getPackagePath(Object object) {
        Package p = object.getClass().getPackage();
        return p != null ? p.getName().replaceAll("\\.", "/") : "";
    }

    public static File getFileFromJar(String file) {
        throw new RuntimeException("Not finish. Do not use this method.");
    }

    public static String getWebRootPath() {
        if (webRootPath == null)
            webRootPath = detectWebRootPath();
        return webRootPath;
    }

    public static void setWebRootPath(String webRootPath) {
        if (webRootPath == null) {
            return;
        }
        if (webRootPath.endsWith(File.separator))
            webRootPath = webRootPath.substring(0, webRootPath.length() - 1);
        webRootPath = webRootPath;
    }

    private static String detectWebRootPath() {
        try {
            String path = PathKit.class.getResource("/").toURI().getPath();
            return new File(path).getParentFile().getParentFile()
                    .getCanonicalPath();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public static void main(String[] args) {
        System.out.println(PathKit.detectWebRootPath());
    }
}
