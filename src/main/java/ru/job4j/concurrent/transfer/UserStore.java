package ru.job4j.concurrent.transfer;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RunListener.ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final Map<Integer, User> map = new ConcurrentHashMap<>();

    public synchronized boolean add(User user) {
        return map.putIfAbsent(user.getId(), user) != null;
    }

    public synchronized boolean update(User user) {
       return map.replace(user.getId(), user) != null;

    }

    public synchronized boolean delete(User user) {
        return  map.remove(user.getId(), user);

    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean rst = false;
        if (map.containsKey(fromId) && map.containsKey(toId) && amount > 0 && map.get(fromId).getAmount() >= amount) {
                map.replace(fromId, new User(fromId, map.get(fromId).getAmount() - amount));
                map.replace(toId, new User(toId, map.get(toId).getAmount() + amount));
            rst = true;
        }
        return rst;
   }
}
