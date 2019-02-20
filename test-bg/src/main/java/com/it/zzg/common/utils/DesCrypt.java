package com.it.zzg.common.utils;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.SecureRandom;
/**
 * <p>
 * </p>
 *
 * @author KeepGo Lamar
 * @email lamar_7950@hotmail.com
 * @date 2017/4/13
 */
public class DesCrypt {
    private String KEY = "password111111";
    private String CODE_TYPE = "UTF-8";
    
    /**
     * DES加密
     * @param datasource
     * @return
     */
    public String encode(String datasource){
        try{
            SecureRandom random = new SecureRandom();
            DESKeySpec desKey = new DESKeySpec(KEY.getBytes(CODE_TYPE));
            //创建一个密匙工厂，然后用它把DESKeySpec转换成
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
            SecretKey securekey = keyFactory.generateSecret(desKey);
            //Cipher对象实际完成加密操作
            Cipher cipher = Cipher.getInstance("DES");
            //用密匙初始化Cipher对象
            cipher.init(Cipher.ENCRYPT_MODE, securekey, random);
            //现在，获取数据并加密
            byte[] temp = Base64.encodeBase64(cipher.doFinal(datasource.getBytes()));
            return IOUtils.toString(temp,"UTF-8");
        }catch(Throwable e){
            e.printStackTrace();
            return null;
        }
    }

    /**
     * DES解密
     * @return
     */
    public String decode(String src) throws Exception {
        // DES算法要求有一个可信任的随机数源
        SecureRandom random = new SecureRandom();
        // 创建一个DESKeySpec对象
        DESKeySpec desKey = new DESKeySpec(KEY.getBytes(CODE_TYPE));
        // 创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey securekey = keyFactory.generateSecret(desKey);
        // Cipher对象实际完成解密操作
        Cipher cipher = Cipher.getInstance("DES");
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, securekey, random);
        // 真正开始解密操作
        return IOUtils.toString(cipher.doFinal(Base64.decodeBase64(src)),CODE_TYPE);
    }

    public String getKEY() {
        return KEY;
    }

    public void setKEY(String KEY) {
        this.KEY = KEY;
    }
    public static void main(String[] args) throws Exception {
    	DesCrypt des = new DesCrypt();
		des.setKEY("admin12345");
		String src = "FYqAjBx/0b41XDbjv5g1Vvk7KoiMabr9Fbw+R4MQ54FXMNWG+B5nvwjPutQiL3a+FTg8xF/ekwBg3T+RTH7BwZcv5aO7i3QuNikjch9D+UY7LdFmcshrmLDsA7mCL+dN8MUy2Omf35JpQZcS1lp8yp/H9586jiKpnHFA32SlQHeg+zUO9l9zJo0SYDi5rG4QCXyv+NCJkgwHAi3jxaPKeKPr/bHLIACRZoOP5pCWcwX0KwyBuZOF9TxYquzn626wQawXWDOetxpYsYEd5KQ59U887F8Z68+5C5sCJDgBU9fdMFO/9fmnCO2sEHXVgzyLr3MA6D6HaCUfN9qp+YY9vlCf1KP1nBM20XDlFWxiZjbh8cWkKl+9ldet3rbSb3YAksMO7Fh6jZxpOQrPg/uwrKdqnXJFqgyp5UMFT+RMZxTj5njJq+gxfRsieFjh+q8ekYSPui0vCNUxVLkyY1PB4Pm0/yzbzslfHbnNV3JoGLHBOF+RFP/aT5dH48bGqj2vKb9ApMTVeAllFas6chKnwLac0ihFIbjDRCwJzvTDbuQ=";
		System.out.println(des.decode(src));
	}
}