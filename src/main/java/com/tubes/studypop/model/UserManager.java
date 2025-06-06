//package com.tubes.studypop.model;
//
//import com.tubes.studypop.dummy.User;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class UserManager {
//    private List<com.tubes.studypop.dummy.User> users;
//
//    public UserManager() {
//        users = new ArrayList<>();
//    }
//
//    public void register(com.tubes.studypop.dummy.User u) {
//        users.add(u);
//    }
//
//    public com.tubes.studypop.dummy.User login(String username, String password) {
//        for (User u : users) {
//            if (u.getUsername().equals(username) && u.login(username, password)) {
//
//                return u;
//            }
//        }
//        return null;
//    }
//}
