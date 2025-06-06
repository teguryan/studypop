//package com.tubes.studypop.repository;
//
//import com.tubes.studypop.dummy.User;
//import org.springframework.data.jpa.repository.JpaRepository;
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserManagerRepository {
//    private List<User> users = new ArrayList<>();
//
//    public void register(User u) {
//        users.add(u);
//    }
//
//    public User login(String username, String password) {
//        for (User u : users) {
//            if (u.getUsername().equals(username) && u.login(username, password)) {
//                return u;
//            }
//        }
//        return null;
//    }
//
//}
