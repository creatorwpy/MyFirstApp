package library.mlibrary.util.common;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by Harmy on 2015/8/10 0010.
 */
public class DES {
    // 算法名称
    public static final String KEY_ALGORITHM = "DES";
    // 算法名称/加密模式/填充方式
    // DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
    public static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding"; // PKCS5Padding
    // NoPadding
    // DES/ECB/PKCS5Padding

    /**
     * DES加密解密参数设置
     *
     * @param key
     * @param mode
     * @return
     * @throws Exception
     */
    private static Cipher init(String key, int mode) throws Exception {
        // Key deskey = desKeyGen(key);
        IvParameterSpec iv = new IvParameterSpec(key.getBytes());
        SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        cipher.init(mode, secretKeySpec, iv);
        return cipher;
    }

    /**
     * DES加密并使用base64加密
     *
     * @param encryptString 明文
     * @param encryptKey    秘钥
     * @return
     * @throws Exception
     */
    public static String encryptDES(String encryptString, String encryptKey)
            throws Exception {
        Cipher cipher = init(encryptKey, Cipher.ENCRYPT_MODE);
        byte[] encryptedData = cipher.doFinal(encryptString.getBytes());
        return Base64.encode(encryptedData);
    }


    /**
     * base64解密再DES解密
     *
     * @param decryptString 密文
     * @param decryptKey    秘钥
     * @return
     * @throws Exception
     */
    public static String decryptDES(String decryptString, String decryptKey)
            throws Exception {
        byte[] byteMi = new Base64().decode(decryptString);
        Cipher cipher = init(decryptKey, Cipher.DECRYPT_MODE);
        byte decryptedData[] = cipher.doFinal(byteMi);
        String result = new String(decryptedData);
        return result;
    }

    /**
     * byte[]转换十六进制
     *
     * @param bytes
     * @return
     */
    private static String formatIntTo16(byte[] bytes) {
        String[] resultBytes = new String[bytes.length];
        String result = "";
        for (int i = 0; i < bytes.length; i++) {
            resultBytes[i] = Integer.toHexString(bytes[i] & 0xFF);
            if (resultBytes[i].length() != 2) {
                resultBytes[i] = "0" + resultBytes[i];
            }
            result = result + resultBytes[i];
        }
        return result;

    }

    /**
     * String转成十进制再转byte[]
     *
     * @param str
     * @return
     */
    private static byte[] hexToInt(String str) {
        char[] ch = str.toCharArray();
        byte[] res = new byte[ch.length / 2];
        for (int i = 0; i < str.length(); i += 2) {
            Integer temp = (Integer.parseInt("" + ch[i] + ch[i + 1], 16));
            res[i / 2] = temp.byteValue();
        }

        return res;
    }

    /**
     * DES加密并转成十六进制
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encrypt(String data, String key)
            throws Exception {
        Cipher cipher = init(key, Cipher.ENCRYPT_MODE);
        byte[] results = cipher.doFinal(data.getBytes());
        String result = formatIntTo16(results);
        return result;
    }


    /**
     * 转成十进制再DES解密
     *
     * @param data 待解密数据
     * @param key  密钥
     * @return 解密后的数据
     */
    public static String decrypt(String data, String key)
            throws Exception {
        byte[] bytes = hexToInt(data);
        Cipher cipher = init(key, Cipher.DECRYPT_MODE);
        // 执行解密操作
        byte[] results = cipher.doFinal(bytes);
        String result = new String(results);
        return result;
    }

}
