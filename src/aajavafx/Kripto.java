/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aajavafx;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Iuliu on 2016-12-10.
 */
public class Kripto {
    static MySecretKey msk=new MySecretKey();
     static String IV = msk.getNumber1();
     String plaintext = "hhh"; /*Note null padding*/

     static String encryptionKey = msk.getNumber2();

    public  String encrypt(String plainText) throws Exception
    {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        byte[]byteCipher= cipher.doFinal(plainText.getBytes("UTF-8"));
        Base64 base64=new Base64();
        String stringToStore = new String(base64.encode(byteCipher));
        //byte[] restoredBytes = Base64.decode(stringToStore.getBytes());
        return  stringToStore;
    }
    public  String decrypt(String cipherText) throws Exception
    {
        Base64 decoder = new Base64();
        byte[] decodedBytes = org.apache.commons.codec.binary.Base64.decodeBase64(cipherText.getBytes());
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        SecretKeySpec key = new SecretKeySpec(encryptionKey.getBytes("UTF-8"), "AES");
        cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(IV.getBytes("UTF-8")));
        return new String(cipher.doFinal(decodedBytes), "UTF-8");
    }

}

