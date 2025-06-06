//package com.tubes.studypop.controller;
//import com.tubes.studypop.dummy.*;
//import com.tubes.studypop.model.Flashcard;
//import com.tubes.studypop.model.Statistics;
//import com.tubes.studypop.model.Student;
//import com.tubes.studypop.model.UserManager;
//import com.tubes.studypop.service.FlashcardManager;
//import com.tubes.studypop.service.Quiz;
//
//import java.util.Scanner;
//
//// main class
//public class App {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        UserManager userManager = new UserManager();
//        FlashcardManager flashcardManager = new FlashcardManager();
//        Statistics statistics = new Statistics();
//
//        // Register admin dan student dummy
//        userManager.register(new LoginController.Admin("admin", "admin123"));
//        userManager.register(new Student("student", "student123"));
//
//        System.out.println("=== Selamat datang di StudyPop ===");
//        System.out.print("Username: ");
//        String username = scanner.nextLine();
//        System.out.print("Password: ");
//        String password = scanner.nextLine();
//
//        User currentUser = userManager.login(username, password);
//
//        if (currentUser == null) {
//            System.out.println("Login gagal. Username atau password salah.");
//            return;
//        }
//
//        System.out.println("Login berhasil sebagai " + currentUser.getUsername());
//        boolean running = true;
//
//        while (running) {
//            System.out.println("\nMenu:");
//            System.out.println("1. Tambah Flashcard");
//            System.out.println("2. Tampilkan Flashcard");
//            System.out.println("3. Hapus Flashcard");
//            System.out.println("4. Mulai Kuis");
//            System.out.println("5. Statistik");
//            System.out.println("0. Keluar");
//            System.out.print("Pilih: ");
//            int choice = Integer.parseInt(scanner.nextLine());
//
//            switch (choice) {
//                case 1:
//                    if (currentUser.canAddFlashcard()) {
//                        System.out.print("Masukkan pertanyaan: ");
//                        String q = scanner.nextLine();
//                        System.out.print("Masukkan jawaban: ");
//                        String a = scanner.nextLine();
//                        flashcardManager.addFlashcard(new Flashcard(q, a));
//                        System.out.println("Flashcard ditambahkan.");
//                    } else {
//                        System.out.println("Maaf, kamu tidak punya izin untuk menambah flashcard.");
//                    }
//                    break;
//                case 2:
//                    if (flashcardManager.listFlashcards().isEmpty()) {
//                        System.out.println("Maaf, tidak ada flashcard yang tersedia.");
//                    } else {
//                        int i = 0;
//                        for (Flashcard fc : flashcardManager.listFlashcards()) {
//                            System.out.println((i++) + ". " + fc.getQuestion() + " - " + fc.getAnswer());
//                        }
//                    }
//                    break;
//                case 3:
//                    if (currentUser.canDeleteFlashcard()) {
//                        System.out.print("Masukkan index flashcard yang ingin dihapus: ");
//                        int index = Integer.parseInt(scanner.nextLine());
//                        flashcardManager.deleteFlashcard(index);
//                        System.out.println("Flashcard dihapus.");
//                    } else {
//                        System.out.println("Maaf, kamu tidak punya izin untuk menghapus flashcard.");
//                    }
//                    break;
//                case 4:
//                    if (flashcardManager.listFlashcards().isEmpty()) {
//                        System.out.println("Maaf, tidak ada kuis yang tersedia.");
//                    }else {
//                    Quiz quiz = new Quiz(flashcardManager);
//                    quiz.startQuiz();
//                    statistics.updateStats(flashcardManager.listFlashcards().size(), quiz.getScore());
//                    }
//                    break;
//                case 5:
//                    statistics.showStats();
//                    break;
//                case 0:
//                    running = false;
//                    System.out.println("Keluar dari aplikasi. Sampai jumpa!");
//                    break;
//                default:
//                    System.out.println("Pilihan tidak valid.");
//            }
//        }
//    }
//}
