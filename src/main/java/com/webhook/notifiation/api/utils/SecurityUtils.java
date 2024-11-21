package com.webhook.notifiation.api.utils;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Base64;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.springframework.beans.factory.annotation.Value;

@Component
public class SecurityUtils {
	
    @Value("${masterkey}")
    private String masterKey;
    
	public String decrypt(String strToDecrypt) {
	    try {
	        Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
	        cipher.init(Cipher.DECRYPT_MODE, setKey(masterKey));
	        byte[] decodedValue = Base64.getDecoder().decode(strToDecrypt);
	        return new String(cipher.doFinal(decodedValue), StandardCharsets.UTF_8);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}


	  private SecretKeySpec setKey(String myKey) {
		    byte[] key;
		    MessageDigest sha = null;
		    try {
		      key = myKey.getBytes("UTF-8");
		      sha = MessageDigest.getInstance("SHA-256");
		      key = sha.digest(key);
		      key = Arrays.copyOf(key, 16);
		      return new SecretKeySpec(key, "AES");

		    } catch (NoSuchAlgorithmException e) {
		      e.printStackTrace();
		    } catch (UnsupportedEncodingException e) {
		      e.printStackTrace();
		    }
		    return null;
		  }
}
