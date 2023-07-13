package com.sample.springrest.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

@Component
public class RSAUtil {


    private static String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCPvEEXGD5SXKT0HH/QV6+ilbmkl4uh0jt6U+Z7ijStIQM79JOh/0X/uSxRWNhqYi1pc1ofj1EJNIviG4OqxBOvPBoZW42LhRjdEKWudqb0Pl51ItFV8vQjdyBr3V+OHQXMvIPoKeYjtuFydJde8ygv+w8r7EH/lz/LKXbvvZP4FwIDAQAB";
    private static String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI+8QRcYPlJcpPQcf9BXr6KVuaSXi6HSO3pT5nuKNK0hAzv0k6H/Rf+5LFFY2GpiLWlzWh+PUQk0i+Ibg6rEE688GhlbjYuFGN0Qpa52pvQ+XnUi0VXy9CN3IGvdX44dBcy8g+gp5iO24XJ0l17zKC/7DyvsQf+XP8spdu+9k/gXAgMBAAECgYACUk2Q1GzpQqLNaIpt8ISfMSDi9imkrvMT+jfuReCs7SwSs1QRGUDlrPV4yOER8sz994V8ngF2jxk1STF6uBkDRKLAGt4HuX8wM3PDqhL2nScDiPzgCjlYKkNCFqC15GlfDiSfMe5agd4KuC1eaJhoZmcDO5rTrQi6IvftmTYM8QJBAPimXfM069/sLvI6FzKW9Jw6fcnBUshf/f0qAr8HqHZCCLCPoiHEZmbBIiIojuIP73tmkhMH4Ml7aOPWL6O+jqcCQQCT+/UPjc8KpwJewt8DlAEOdYu5OGUXnw55f1WtU4n1yVYfI3lKoRbdmPoMhDIldGYIFYW/F7WhxgkaMyzmhmkRAkEAs2atwI09ZnmtQZ3CIQEjihj1qvzuMp53zhJimtv2oyRvypxHeZ+P1rGgZA7pUJf4zQsrd8mmWRjOjBx8kOMrXwJACRfTUBhbn4DxoVcCExs2EWhhpIj1lUT9w7NyVUsr0AGGHDW2z0IkwIsU2k4JMsn7NwXziPFxBLbBem2olaWusQJACkxTexoeh+hGaTmVrRrsmvn+jT7wAVkLEAZDy+Mk+CKGcJyVSAJ5H9M2ad+D8pPJvL14cXDsNRI5F/INBX6CoQ==";

    //'MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAI+8QRcYPlJcpPQcf9BXr6KVuaSXi6HSO3pT5nuKNK0hAzv0k6H/Rf+5LFFY2GpiLWlzWh+PUQk0i+Ibg6rEE688GhlbjYuFGN0Qpa52pvQ+XnUi0VXy9CN3IGvdX44dBcy8g+gp5iO24XJ0l17zKC/7DyvsQf+XP8spdu+9k/gXAgMBAAECgYACUk2Q1GzpQqLNaIpt8ISfMSDi9imkrvMT+jfuReCs7SwSs1QRGUDlrPV4yOER8sz994V8ngF2jxk1STF6uBkDRKLAGt4HuX8wM3PDqhL2nScDiPzgCjlYKkNCFqC15GlfDiSfMe5agd4KuC1eaJhoZmcDO5rTrQi6IvftmTYM8QJBAPimXfM069/sLvI6FzKW9Jw6fcnBUshf/f0qAr8HqHZCCLCPoiHEZmbBIiIojuIP73tmkhMH4Ml7aOPWL6O+jqcCQQCT+/UPjc8KpwJewt8DlAEOdYu5OGUXnw55f1WtU4n1yVYfI3lKoRbdmPoMhDIldGYIFYW/F7WhxgkaMyzmhmkRAkEAs2atwI09ZnmtQZ3CIQEjihj1qvzuMp53zhJimtv2oyRvypxHeZ+P1rGgZA7pUJf4zQsrd8mmWRjOjBx8kOMrXwJACRfTUBhbn4DxoVcCExs2EWhhpIj1lUT9w7NyVUsr0AGGHDW2z0IkwIsU2k4JMsn7NwXziPFxBLbBem2olaWusQJACkxTexoeh+hGaTmVrRrsmvn+jT7wAVkLEAZDy+Mk+CKGcJyVSAJ5H9M2ad+D8pPJvL14cXDsNRI5F/INBX6CoQ=='
    //@Value("${client.encrypt.key}")
    //private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

   // @Value("${server.decrypt.key}")
   // private String privateKey;

    public String getPrivateKey() {
        return privateKey;
    }

    public  PublicKey getPublicKey(String base64PublicKey) {
        PublicKey publicKey = null;
        try {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(Base64.getDecoder().decode(base64PublicKey.getBytes()));
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            publicKey = keyFactory.generatePublic(keySpec);
            return publicKey;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return publicKey;
    }

    public  PrivateKey getPrivateKey(String base64PrivateKey) {
        PrivateKey privateKey = null;
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(Base64.getDecoder().decode(base64PrivateKey.getBytes()));
        KeyFactory keyFactory = null;
        try {
            keyFactory = KeyFactory.getInstance("RSA");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        try {
            privateKey = keyFactory.generatePrivate(keySpec);
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return privateKey;
    }

    public  byte[] encrypt(String data, String publicKey) throws BadPaddingException, IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, NoSuchAlgorithmException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, getPublicKey(publicKey));
        return cipher.doFinal(data.getBytes());
    }

    public  String decrypt(byte[] data, PrivateKey privateKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {
        Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(data));
    }

    public  String decrypt(String data, String base64PrivateKey) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(base64PrivateKey));
    }

    public  String decrypt(String data) throws IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {
        return decrypt(Base64.getDecoder().decode(data.getBytes()), getPrivateKey(privateKey));
    }

    public static void main(String[] args) throws IllegalBlockSizeException, InvalidKeyException, NoSuchPaddingException, BadPaddingException {
        RSAUtil rsaUtil = new RSAUtil();
        try {
            String encryptedString = Base64.getEncoder().encodeToString(rsaUtil.encrypt("confidential", rsaUtil.publicKey));
            System.out.println(encryptedString);
            String decryptedString = rsaUtil.decrypt(encryptedString, rsaUtil.privateKey);
            System.out.println(decryptedString);
        } catch (NoSuchAlgorithmException e) {
            System.err.println(e.getMessage());
        }

    }
}
