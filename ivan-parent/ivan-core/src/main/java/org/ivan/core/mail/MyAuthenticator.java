package org.ivan.core.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
/**
 * 向邮件服务器提交认证信息
 * @author 周立波
 *
 */
public class MyAuthenticator extends Authenticator{
	 private String username = "475379135@qq.com";  
	   
     private String password = "qpawpnvgvmxxbjfa";  

     public MyAuthenticator() {  
         super();  
     }  

     public MyAuthenticator(String username, String password) {  
         super();  
         this.username = username;  
         this.password = password;  
     }  

     protected PasswordAuthentication getPasswordAuthentication() {  

         return new PasswordAuthentication(username, password);  
     } 
}
