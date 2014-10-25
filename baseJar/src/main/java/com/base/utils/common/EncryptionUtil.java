package com.base.utils.common;

import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

/**
 * 密码加密工具类
 * @author
 *
 */
public class EncryptionUtil {
    
    public static String pwdEncrypt(String password, String username) {
	ShaPasswordEncoder spe = new ShaPasswordEncoder(256);
	spe.setEncodeHashAsBase64(true);
	return spe.encodePassword(password, username);
    }

    /**md5编码*/
    public static String md5Encrypt(String url){
        Md5PasswordEncoder md5=new Md5PasswordEncoder();
        String m = md5.encodePassword(url,"");
        return m;
    }
    
    public static void main(String[] args) {
	System.out.println(EncryptionUtil.md5Encrypt("http://jhfj!@#%^*(*(.com"));
        System.out.println(EncryptionUtil.md5Encrypt("http://jhfj!@#%^*(*(.com"));
        System.out.println(EncryptionUtil.md5Encrypt("http://jhfj!@#%^*(*(.com"));
    }
}
