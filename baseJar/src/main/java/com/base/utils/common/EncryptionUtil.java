package com.base.utils.common;

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
    
    public static void main(String[] args) {
	System.out.println(EncryptionUtil.pwdEncrypt("123456", "532401197111251239"));
    }
}
