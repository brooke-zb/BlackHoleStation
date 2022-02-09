package com.brookezb.bhs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author brooke_zb
 */
@EnableAsync
@EnableCaching
@EnableScheduling
@SpringBootApplication
public class BlackHoleStationApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlackHoleStationApplication.class, args);
    }
}
