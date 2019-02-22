package org.ivan.entity.utils;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

public class FtpUtils {


    /**
     * 本地字符编码
     */
    private static String LOCAL_CHARSET = "GBK";

    // FTP协议里面，规定文件名编码为iso-8859-1
    private static String SERVER_CHARSET = "ISO-8859-1";

    /**
     * 通过流上传本地文件到Ftp服务器
     * @param path 需要保存到Ftp服务器的路径信息 ps：/test/apk/
     * @param filename  文件名称
     * @param input 需要上传的文件流
     * @param address ftp地址
     * @param port 端口号
     * @param name 登录名
     * @param pwd 密码
     * @return boolean
     * 2015-12-14上午10:32:42
     */
    static boolean uploadFile(String path, String filename,
                              InputStream input, String address, int port, String name, String pwd) {
        // 过滤掉的文件类型
        String[] errorType = {".exe", ".com", ".cgi", ".asp", ".php", ".jsp", ".dll", ".bat"};
        for (String anErrorType : errorType) {
            if (filename.endsWith(anErrorType)) {
                filename = filename + ".danger";
            }
        }

        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            ftp.connect(address, port);

            if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
                if (ftp.login(name, pwd)) {
                    if (FTPReply.isPositiveCompletion(ftp.sendCommand(
                            "OPTS UTF8", "ON"))) {// 开启服务器对UTF-8的支持，如果服务器支持就用UTF-8编码，否则就使用本地编码（GBK）.
                        LOCAL_CHARSET = "UTF-8";
                    }
                }
            } else {
                ftp.disconnect();
                return success;
            }
            ftp.setControlEncoding(LOCAL_CHARSET);
            ftp.setBufferSize(1024 * 1024);
            splitDir(ftp, path);
            //被动
            ftp.enterLocalPassiveMode();
            // 设置以二进制流的方式传输
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            //登陆后设置文件类型为二进制否则可能导致乱码文件无法打开
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            //上传文件时，文件名称需要做编码转换
            filename = new String(filename.getBytes(LOCAL_CHARSET), SERVER_CHARSET);
            ftp.storeFile(filename, input);
            //input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.getMessage();
                }
            }
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return success;
    }

    /**
     * @param  ftp ftp
     * @param  path 路径
     * ftp上传判断文件夹是否存在并创建文件
     * true没有这个文件夹，false是有
     */
    private static void splitDir(FTPClient ftp, String path) {
        String[] paths = path.split("/");
        String parent = "";
        for (String path1 : paths) {
            if (path1.equals(""))
                continue;
            parent += "/" + path1;
            try {
                ftp.makeDirectory(parent);
                ftp.cwd(parent);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param serverAddr 服务地址
     * @param port 端口
     * @param account 账号
     * @param password 密码
     * @return FTPClient
     * 获取ftp连接
     */
    public static FTPClient getFTPClient(String serverAddr, int port,
                                         String account, String password) {
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(serverAddr, port);
            ftp.login(account, password);
            ftp.setBufferSize(1024 * 1024);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return ftp;
            }
            ftp.enterLocalPassiveMode();
            ftp.setFileTransferMode(FTP.STREAM_TRANSFER_MODE);
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        } catch (IOException e) {
            e.getMessage();
        }
        return ftp;
    }

    /**
     * @param  ftp ftp对象
     * 断开ftp连接
     */
    public static void disconnect(FTPClient ftp) {
        if (ftp != null && ftp.isConnected()) {
            try {
                ftp.logout();
                ftp.disconnect();
            } catch (IOException ioe) {
                ioe.getMessage();
            }
        }
    }
}
