package uz.pdp.chat;

import uz.pdp.chat.db.MessageRepo;
import uz.pdp.chat.db.UserRepo;
import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import static uz.pdp.chat.util.Input.*;

public class Main {
    static UserRepo userRepo = UserRepo.getInstance();
    static MessageRepo messageRepo = MessageRepo.getInstance();

    public static void main(String[] args) throws InterruptedException {
        while (true) {
            showFirstMenu();
            switch (inputInt("Choose")) {
                case 1 -> AuthService.register();
                case 2 -> AuthService.logIn();
                case 3 -> System.exit(0);
            }
            if ( AuthService.CurrentUser == null ) {
                continue;
            }
            generateMessage();
        }
    }

    private static void generateMessage() throws InterruptedException {
        while (true) {
            System.out.println();
            User chosenUser = chooseUserToChat();

            showMessages(chosenUser);

            Message newMessage = new Message(

             AuthService.CurrentUser.getId(), chosenUser.getId(), inputStr("Write a message"), ZonedDateTime.now());

            System.out.println("Loading...");

            CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> messageRepo.save(newMessage));
            completableFuture.thenRun(() -> showMessages(chosenUser));
            Thread.sleep(1200);
            if ( inputInt("0 to exit/1 to continue") == 0 ) {
                AuthService.CurrentUser = null;
                return;
            }
        }
    }


    private static void showMessages(User chosenUser) {
        List<Message> messages = messageRepo.findAll();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        for (Message message : messages) {
            String formattedTime = message.getZonedDateTime().withZoneSameInstant(AuthService.CurrentUser.getZoneId()).format(formatter);
            if ( message.getFrom().equals(chosenUser.getId()) && message.getTo().equals(AuthService.CurrentUser.getId()) ) {
                System.out.println(message.getText());
                System.out.println(formattedTime);
            } else if ( message.getTo().equals(chosenUser.getId()) && message.getFrom().equals(AuthService.CurrentUser.getId()) ) {
                System.out.println("\t\t\t" + message.getText());
                System.out.println("\t\t\t" + formattedTime);
            }
        }
    }

    private static User chooseUserToChat() {
        List<User> users = userRepo.findAll();
        int i = 1;
        for (User user : users) {
            if ( !user.equals(AuthService.CurrentUser) ) {
                System.out.println(i + ". " + user.getName());
            }
            i++;
        }
        return users.get(inputInt("Choose user") - 1);
    }

    private static void showFirstMenu() {
        System.out.println("""
                  
         1. Register
         2. Log in
         3. Exit
                  
         """);
    }
}
