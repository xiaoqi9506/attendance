package cn.howtoplay.attendance.common;


import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaoqi on 2019/3/6
 */

@Configuration
public class AesConfig {

    @Bean
    public SymmetricCrypto getSymmetricCrypto() {
        String key = "attendance";
        return new SymmetricCrypto(SymmetricAlgorithm.AES, key.getBytes());
    }

}
