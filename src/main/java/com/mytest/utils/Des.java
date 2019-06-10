package com.mytest.utils;


import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.imageio.ImageIO;

import org.apache.commons.codec.binary.Base64;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;


public class Des {
    private static final char[] legalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/="
            .toCharArray();

    /**
     * data[]进行编码
     * 
     * @param data
     * @return
     */
    public static String encode(byte[] data) {
        int start = 0;
        int len = data.length;
        StringBuffer buf = new StringBuffer(data.length * 3 / 2);

        int end = len - 3;
        int i = start;
        int n = 0;

        while (i <= end) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 0x0ff) << 8)
                    | (((int) data[i + 2]) & 0x0ff);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append(legalChars[d & 63]);

            i += 3;

            if (n++ >= 14) {
                n = 0;
                buf.append(" ");
            }
        }

        if (i == start + len - 2) {
            int d = ((((int) data[i]) & 0x0ff) << 16)
                    | ((((int) data[i + 1]) & 255) << 8);

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append(legalChars[(d >> 6) & 63]);
            buf.append("=");
        } else if (i == start + len - 1) {
            int d = (((int) data[i]) & 0x0ff) << 16;

            buf.append(legalChars[(d >> 18) & 63]);
            buf.append(legalChars[(d >> 12) & 63]);
            buf.append("==");
        }

        return buf.toString();
    }

    public static String parseByte2HexStr(byte buf[]) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < buf.length; i++) {
            String hex = Integer.toHexString(buf[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            sb.append(hex.toUpperCase());
        }
        return sb.toString();
    }

    public static byte[] decode(String s) {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            decode(s, bos);
        } catch (IOException e) {
            throw new RuntimeException();
        }
        byte[] decodedBytes = bos.toByteArray();
        try {
            bos.close();
            bos = null;
        } catch (IOException ex) {
            System.err.println("Error while decoding BASE64: " + ex.toString());
        }
        return decodedBytes;
    }

    private static void decode(String s, OutputStream os) throws IOException {
        int i = 0;

        int len = s.length();

        while (true) {
            while (i < len && s.charAt(i) <= ' ')
                i++;

            if (i == len)
                break;

            int tri = (decode(s.charAt(i)) << 18)
                    + (decode(s.charAt(i + 1)) << 12)
                    + (decode(s.charAt(i + 2)) << 6)
                    + (decode(s.charAt(i + 3)));

            os.write((tri >> 16) & 255);
            if (s.charAt(i + 2) == '=')
                break;
            os.write((tri >> 8) & 255);
            if (s.charAt(i + 3) == '=')
                break;
            os.write(tri & 255);

            i += 4;
        }
    }

    private static int decode(char c) {
        if (c >= 'A' && c <= 'Z')
            return ((int) c) - 65;
        else if (c >= 'a' && c <= 'z')
            return ((int) c) - 97 + 26;
        else if (c >= '0' && c <= '9')
            return ((int) c) - 48 + 26 + 26;
        else
            switch (c) {
            case '+':
                return 62;
            case '/':
                return 63;
            case '=':
                return 0;
            default:
                throw new RuntimeException("unexpected code: " + c);
            }
    }

    private static byte[] iv = { 1, 2, 3, 4, 5, 6, 7, 8 };

    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(encryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE, key, zeroIv);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encodeBase64String(encryptedData);//.replace(CommonConst.TOKEN_JIA_HAO, CommonConst.TOKEN_AITE_JING_DAOLE);
    }

    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        String d = decryptString.replaceAll(" ", "+");
        byte[] byteMi = Base64.decodeBase64(d);
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(decryptKey.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE, key, zeroIv);
        byte decryptedData[] = cipher.doFinal(byteMi);

        return new String(decryptedData);
    }
    static BASE64Encoder encoder = new sun.misc.BASE64Encoder();    
    static BASE64Decoder decoder = new sun.misc.BASE64Decoder();    
    public static void main(String[] args) {
        try {
            
            File f = new File("E:\\testorc\\0.jpg");           
            BufferedImage bi;    
            try {    
                bi = ImageIO.read(f);    
                ByteArrayOutputStream baos = new ByteArrayOutputStream();    
                ImageIO.write(bi, "jpg", baos);    
                byte[] bytes = baos.toByteArray();    
                    
                System.out.println(encoder.encodeBuffer(bytes).trim()); ;    
            } catch (IOException e) {    
                e.printStackTrace();    
            }    
        
            
           /*  Pattern pattern = Pattern.compile(".+(狗屎问题)+?\\D+(\\d{3})\n");
            Matcher m = pattern.matcher(s3);
            while(m.find()){
                System.out.println(m.group(2));
            }
            
           AHttpClient httpClient = new AHttpClient();
            List<ParamPair> stringPartList = new ArrayList<ParamPair>();
            stringPartList.add(new ParamPair("7O48WhX3l72n0KxrtTnltA==","mfI1X/tOMbdgexlW1S+c7Q=="));
            stringPartList.add(new ParamPair("CI3MTqGDCEYI2y5OYNkybg==","bhbPkOG6dyjO+AtOZkEcew=="));
            stringPartList.add(new ParamPair("Dk+m7KX2GYlWHe2tTqwaGg==","xfV/Hg0BL12Mwir+/J4Q7w=="));
            stringPartList.add(new ParamPair("IRDfzpjMbhmS/KZVY50A2g==","K9RZ9yl1Yqq9eIRfUsJN55pTgDBGOtu5UFGxJHc3NQo3Hi7dJhBP0ft4sgbGMCwth+qMhDaZhBVXxZ9KoyZaSolwm97ws1Wd4Uhhd2qKF7M="));
            stringPartList.add(new ParamPair("NzNAVZBwmqI5JILsluVT0w==","mEMpNoKJNdj92ttdD9iDEA=="));
            stringPartList.add(new ParamPair("QTeMK5E/SO3Q883GxlSJtw==","B7qs10iAU95Fh8ojST69PQ=="));
            stringPartList.add(new ParamPair("U0QuKoyeQ+UDqZasfzSknA==","BTo34F/2ASTOrNR1TLh3mA=="));
            stringPartList.add(new ParamPair("eNuh4gaYnmvNe16q0/OUew==","BC8DxmPRHy7RiddAkUyyE992SOazBB3yAIe+1aayoDBZAnHHvaZ76bKCMHc9TAOc"));
            
            stringPartList.add(new ParamPair("jN2rSJt02gLs6WG0+uUpYg==","PWxudidJKnyIUrb0WpP5ig=="));
            stringPartList.add(new ParamPair("sdTkFz266cotpcZe1JE3Pg==", "L4734BW1iPzIqdlXB3CysMmlIzwaf3+07Z/swG0m6YA7EIiChvE0Ic4trK2dOeDE"));
            stringPartList.add(new ParamPair("tPHbTNykjnIv8B87Ot9P2A==","84QVIwveTQYiN6sZyU4dSw=="));
            stringPartList.add(new ParamPair("zH7YS6SYvZ2KXgF5aDcxhQ==","4AmNSiYMHKFiwniYN0Q3qw=="));
            String httpresult = httpClient.doHttpPostRequest("http://12.0.0.200:8890/qianbao-api-web/brand/userBrandCostomByUserId", stringPartList);
            System.out.println(httpresult);
            System.out.println(JMEncryptBox.decryptFromBase64(httpresult));*/
           
            /* String plaintext = "111111";z
            String ciphertext;
            ciphertext = Des.encryptDES(plaintext, "f7451ca8");
            System.out.println("明文：" + plaintext);
            System.out.println("密钥：" + "f7451ca8");
            System.out.println("密文：" + ciphertext);*/ 
         //System.out.println(Des.encryptDES("7461776,10028955,1457609789534,v2,1452601153000,18652090200,123456", "f7451ca8"));
           //System.out.println("解密后：" + Des.decryptDES("DJvNrXvPSBPEFIBmLxSvj9S%2FHnK%40%23%24l68aK00sktn4NZjkcLJnK6045r9Y5vbeqGi9".replaceAll(" ", "+"), "f7451ca8"));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}