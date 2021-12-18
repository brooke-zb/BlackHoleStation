package top.brookezb.bhs.cache;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.cache.Cache;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author brooke_zb
 */
@Slf4j
public class MybatisCache implements Cache {
    private final String id;
    private final Map<Object, Object> map;

    public MybatisCache(String id) {
        this.id = id;
        this.map = new ConcurrentHashMap<>();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void putObject(Object o, Object o1) {
        map.put(o, o1);
    }

    @Override
    public Object getObject(Object o) {
        return map.get(o);
    }

    @Override
    public Object removeObject(Object o) {
        return map.remove(o);
    }

    @Override
    public void clear() {
        map.clear();
    }

    @Override
    public int getSize() {
        return map.size();
    }
}
