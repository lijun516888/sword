package com.sword.core.entrypt;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class EntryptUtils {

    public void secretEncrypt() {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);

            FileOutputStream fileOutputStream = new FileOutputStream("d://secetyKey.key");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(secretKey);
            objectOutputStream.close();
            fileOutputStream.close();

            byte[] bytes = cipher.doFinal("你好".getBytes());
            FileOutputStream fileOutputStream1 = new FileOutputStream("d://secretContent.dat");
            fileOutputStream1.write(bytes);
            fileOutputStream1.close();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void secretDecrypt() {
        try {
            Cipher cipher = Cipher.getInstance("AES");
            FileInputStream fisKey=new FileInputStream("d://secetyKey.key");
            ObjectInputStream oisKey =new ObjectInputStream(fisKey);
            Key key =(Key)oisKey.readObject();
            oisKey.close();
            fisKey.close();

            cipher.init(Cipher.DECRYPT_MODE, key);

            FileInputStream fisDat = new FileInputStream("d://secretContent.dat");
            //获取数据第一种方式
            byte [] src=new byte [fisDat.available()];
            int len =fisDat.read(src);
            int total =0;
            while(total<src.length){
                total +=len;
                len=fisDat.read(src,total,src.length-total);
            }

            byte [] result=cipher.doFinal(src);
            fisDat.close();
            System.out.println(new String(result));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
