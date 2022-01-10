package ru.job4j.concurrent.transfer;

import net.jcip.annotations.GuardedBy;
import org.junit.runner.notification.RunListener;

import java.util.ArrayList;
import java.util.List;

@RunListener.ThreadSafe
public class UserStore {

    @GuardedBy("this")
    private final List<User> list;

    public UserStore() {
        list = new ArrayList<>();
    }

    public synchronized boolean add(User user) {
        return list.add(user);
    }

    public synchronized boolean update(User user) {
        boolean rst =  false;
        for (User temp: list) {
            if (user.getId() == temp.getId()) {
               temp.setAmount(user.getAmount());
                rst = true;
            }
        }
       return rst;
    }

    public synchronized boolean delete(User user) {
        return list.remove(user);
    }

    public synchronized boolean transfer(int fromId, int toId, int amount) {
        boolean check = false;
        User from = null;
        User to = null;
        for (User user: list) {
            if (user.getId() == fromId) {
                from = user;
            } else if (user.getId() == toId) {
                to = user;
            }
        }
       if (from != null && to != null && amount > 0 && from.getAmount() >= amount) {
           from.setAmount(from.getAmount() - amount);
           to.setAmount(to.getAmount() + amount);
           check = true;
       }
        return check;
   }
}
