package com.sword.core.entrypt;

import org.apache.commons.codec.binary.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class Digests {

    private static final String SHA1 = "SHA-1";
    private static final String MD5 = "MD5";

    private static SecureRandom random = new SecureRandom();

    public static String generateRandom(int numBytes) {
        byte[] bytes = new byte[numBytes];
        random.nextBytes(bytes);
        return Hex.encodeHexString(bytes);
    }


    public static byte[] digest(byte[] input, byte[] salt, int interations) {
        return digest(input, MD5, salt, interations);
    }

    private static byte[] digest(byte[] input, String algorithm, byte[] salt, int interations) {
        try {
            MessageDigest digest = MessageDigest.getInstance(algorithm);
            if(salt != null) {
                digest.update(salt);
            }
            byte[] result = digest.digest(input);
            for(int i = 0; i < interations; i++) {
                digest.reset();
                result = digest.digest(result);
            }
            return result;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(Digests.generateRandom(10));
    }

}
