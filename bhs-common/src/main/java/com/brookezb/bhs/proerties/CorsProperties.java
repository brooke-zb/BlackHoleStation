package com.brookezb.bhs.proerties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author brooke_zb
 */
@Data
@ConfigurationProperties(prefix = "cors")
public class CorsProperties {
    private String[] origins;
}
