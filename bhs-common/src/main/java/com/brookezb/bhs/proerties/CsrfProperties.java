package com.brookezb.bhs.proerties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author brooke_zb
 */
@Data
@ConfigurationProperties(prefix = "csrf.path")
public class CsrfProperties {
    private List<String> includes = new ArrayList<>();
    private List<String> excludes = new ArrayList<>();
}
