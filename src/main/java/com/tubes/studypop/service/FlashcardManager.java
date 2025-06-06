//package com.tubes.studypop.service;
//
//import com.example.studypop.Flashcard;
//import com.tubes.studypop.repository.DBHelper;
//
//import java.sql.*;
//import java.util.ArrayList;
//import java.util.List;
//
//
//public class FlashcardManager {
//    public List<Flashcard> listFlashcards(){
//        List<Flashcard> flashcards = new ArrayList<>();
//        try (Connection conn = DBHelper.connect();
//             Statement stmt = conn.createStatement();
//             ResultSet rs = stmt.executeQuery("SELECT * FROM flashcards")) {
//
//            while (rs.next()) {
//                String question = rs.getString("question");
//                String answer = rs.getString("answer");
//                flashcards.add(new Flashcard(question, answer));
//            }
//    } catch (SQLException e) {
//        System.out.println("Gagal ambil flashcard dari database.");
//        e.printStackTrace();
//    }
//    return flashcards;
//    }
//
//    public void addFlashcard(Flashcard f) {
//        try (Connection conn = DBHelper.connect();
//             PreparedStatement stmt = conn.prepareStatement(
//                 "INSERT INTO flashcards (question, answer) VALUES (?, ?)")) {
//            stmt.setString(1, f.getQuestion());
//            stmt.setString(2, f.getAnswer());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Gagal tambah flashcard.");
//            e.printStackTrace();
//        }
//
//    }
//
//    public void deleteFlashcard(int index) {
//        List<Flashcard> flashcards = listFlashcards();
//        if (index < 0 || index >= flashcards.size()) {
//            System.out.println("Index tidak valid.");
//            return;
//        }
//        Flashcard fc = flashcards.get(index);
//        try (Connection conn = DBHelper.connect();
//             PreparedStatement stmt = conn.prepareStatement(
//                 "DELETE FROM flashcards WHERE question = ? AND answer = ? LIMIT 1")) {
//            stmt.setString(1, fc.getQuestion());
//            stmt.setString(2, fc.getAnswer());
//            stmt.executeUpdate();
//        } catch (SQLException e) {
//            System.out.println("Gagal hapus flashcard.");
//            e.printStackTrace();
//        }
//    }
//
//
//
//}
