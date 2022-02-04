package com.brookezb.bhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author brooke_zb
 */
@EnableCaching
@SpringBootApplication
public class BlackHoleStationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlackHoleStationApplication.class, args);
    }
}
