package ru.job4j.concurrent.cahe;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Cache {

    private final Map<Integer, Base> map = new ConcurrentHashMap<>();

    public boolean add(Base model) {
        return map.putIfAbsent(model.getId(), model) == null;
    }

    public boolean update(Base model) {
        return map.computeIfPresent(model.getId(), (e, b) ->  {
            if (b.getVersion() != model.getVersion()) {
                throw new OptimisticException("Versions are not equal");
            }
            model.setVersion(b.getVersion() + 1);
                return model;
            }) != null;

    }

    public void delete(Base model) {
       map.remove(model.getId());
    }

    public int getSize() {
        return map.size();
    }
}
