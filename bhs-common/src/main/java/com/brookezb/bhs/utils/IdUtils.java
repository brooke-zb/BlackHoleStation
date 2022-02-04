package com.brookezb.bhs.utils;

import com.github.yitter.contract.IIdGenerator;
import com.github.yitter.contract.IdGeneratorException;
import com.github.yitter.contract.IdGeneratorOptions;
import com.github.yitter.idgen.DefaultIdGenerator;

/**
 * @author brooke_zb
 */
public class IdUtils {
    private static final IIdGenerator idGenInstance;

    public static long nextId() throws IdGeneratorException {
        return idGenInstance.newLong();
    }

    static {
        IdGeneratorOptions options = new IdGeneratorOptions((short) 1);
        idGenInstance = new DefaultIdGenerator(options);
    }
}
