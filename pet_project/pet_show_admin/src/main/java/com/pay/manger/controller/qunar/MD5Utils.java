package com.pay.manger.controller.qunar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Utils {

  
	 /** 
     * 加密算法MD5 
     *  
     * @param text 
     *            明文 
     * @return String 密文 
     */  
    public final static String encoding(String text)  
    {  
        char hexDigits[] =  
        { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd',  
                'e', 'f' };  
        String encodingStr = null;  
        try  
        {  
            byte[] strTemp = text.getBytes();  
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");  
            mdTemp.update(strTemp);  
            byte[] md = mdTemp.digest();  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++)  
            {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            encodingStr = new String(str);  
        }  
        catch (Exception e)  
        {  
        }  
        return encodingStr;  
    }  
    public static String encode(String text){  
        
        try {  
            MessageDigest digest = MessageDigest.getInstance("md5");  
            byte[] result = digest.digest(text.getBytes());  
            StringBuilder sb =new StringBuilder();  
            for(byte b:result){  
                int number = b&0xff;  
                String hex = Integer.toHexString(number);  
                if(hex.length() == 1){  
                    sb.append("0"+hex);  
                }else{  
                    sb.append(hex);  
                }  
            }  
            return sb.toString();  
        } catch (NoSuchAlgorithmException e) {  
            // TODO Auto-generated catch block  
            e.printStackTrace();  
        }  
      
    return "" ;  
}  
    public static void main(String[] areg){  
    	MD5Utils md5 = new MD5Utils(); 
    	String test="test";
    	String orderNo="orderNo";
    	String code="createTime=1411442340key=testparams={"+orderNo+":"+test+"}tag=flight.order.orderInfo.orderDetailtoken=test";
    	String code1="createTime=1411442340key=testparams={'orderNo':'test'}tag=flight.order.orderInfo.orderDetailtoken=test";
      String encodingStr=  md5.encoding(code1);  
      String encodingStr1= md5.encode(code1);
      System.out.println(encodingStr);
      System.out.println(encodingStr1);
    }
}
