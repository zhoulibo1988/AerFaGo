package org.ivan.entity.utils;

import java.security.MessageDigest;

public class PasswordEncoder {
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    private Object salt;

    private String algorithm;

    public PasswordEncoder(Object salt, String algorithm) {
        this.salt = salt;
        this.algorithm = algorithm;
    }

    public String encode(String rawPass) {
        String result = null;
        try {
            MessageDigest md = MessageDigest.getInstance(algorithm);
            // 加密后的字符串
            result = byteArrayToHexString(md.digest(mergePasswordAndSalt(rawPass).getBytes("utf-8")));
        } catch (Exception ex) {
        }
        return result;
    }

    public boolean isPasswordValid(String encPass, String rawPass) {
        String pass1 = "" + encPass;
        String pass2 = encode(rawPass);

        return pass1.equals(pass2);
    }

    private String mergePasswordAndSalt(String password) {
        if (password == null) {
            password = "";
        }

        if ((salt == null) || "".equals(salt)) {
            return password;
        } else {
            return password + "{" + salt.toString() + "}";
        }
    }

    /**
     * 转换字节数组为16进制字串
     * 
     * @param b
     *            字节数组
     * @return 16进制字串
     */
    private static String byteArrayToHexString(byte[] b) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            resultSb.append(byteToHexString(b[i]));
        }
        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n = 256 + n;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }

    /**
     * 
     * @author buyuer
     * @Title: getPassword
     * @Description: 获取密码
     * @param password
     * @return
     */
    public static String getPassword(String password) {
        PasswordEncoder encoderMd5 = new PasswordEncoder("cn.dianyou.teamwork", "MD5");
        String encode = encoderMd5.encode(password);
        return encode;
    }

    public static void main(String[] args) {
//        String salt = "1";
//        PasswordEncoder encoderMd5 = new PasswordEncoder(salt, "MD5");
//        String encode = encoderMd5.encode("test");
//        System.out.println(encode);
//        boolean passwordValid = encoderMd5.isPasswordValid("083a8db3ff5b9b4ece3ef2bde03226c8", "test");
//        System.out.println(passwordValid);
//
//        PasswordEncoder encoderSha = new PasswordEncoder(salt, "SHA");
//        String pass2 = encoderSha.encode("test");
//        System.out.println(pass2);
//        boolean passwordValid2 = encoderSha.isPasswordValid("182287937a931ab67947494db83fffe49e00acbf", "test");
//        System.out.println(passwordValid2);
        System.out.println(getPassword("123456"));
    }
}
