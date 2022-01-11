package ru.job4j.concurrent.transfer;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunListener.ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final Map<Integer, Integer> map = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        boolean rst = false;
        if (user != null) {
            map.put(user.getId(), user.getAmount());
            rst = true;
        }
        return rst;
    }

    public synchronized boolean update(User user) {
        boolean rst = false;
        if (map.containsKey(user.getId())) {
            map.put(user.getId(), user.getAmount());
            rst = true;
        }
       return rst;

    }

    public synchronized boolean delete(User user) {
        boolean rst = false;
        if (map.containsKey(user.getId())) {
            map.remove(user.getId());
            rst = true;
        }
        return  rst;

    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rst = false;
        if (map.containsKey(fromId) && map.containsKey(toId) && amount > 0 && map.get(fromId) >= amount) {
                int i = map.get(fromId) - amount;
                int i2 = map.get(toId) + amount;
                map.replace(fromId, i);
                map.replace(toId, i2);
            rst = true;
        }
        return rst;
   }
}
