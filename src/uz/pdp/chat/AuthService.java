package uz.pdp.chat;

import uz.pdp.chat.db.UserRepo;
import uz.pdp.chat.entity.User;

import static uz.pdp.chat.util.Input.*;

import java.time.ZoneId;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AuthService {

    public static User CurrentUser = null;
    static UserRepo userRepo = UserRepo.getInstance();

    public static void register() {
        User user = new User(inputStr("Enter Name"),
         inputStr("Enter Phone Number"),
         createPassword(),
         chooseZoneId());

        userRepo.save(user);
        System.out.println("User has been successfully created!");
    }

    private static String createPassword() {
        String password = inputStr("Strong password includes at least 8 elements\n 1 digit and at least one upper case");
        Pattern pattern = Pattern.compile("(?=.*[a-z]+)(?=.*[A-Z]+)(?=.*[0-9]+).{8,}");
        Matcher matcher = pattern.matcher(password);
        if ( matcher.matches() ) {
            return password;
        }
        return createPassword();
    }

    private static ZoneId chooseZoneId() {
        System.out.println("""
                  
         1. Tashkent
         2. Japan
         3. America
                  
         """);
        return switch (inputInt("Choose time zone")) {
            case 1 -> ZoneId.of("Asia/Tashkent");
            case 2 -> ZoneId.of("Asia/Tokyo");
            case 3 -> ZoneId.of("America/New_York");
            default -> ZoneId.systemDefault();
        };
    }


    public static void logIn() {
        String phoneNumber = inputStr("Enter Phone Number");
        String password = inputStr("Enter Password");
        List<User> users = userRepo.findAll();
        for (User user : users) {
            if ( user.getPassword().equals(password) && user.getPhoneNumber().equals(phoneNumber) ) {
                CurrentUser = user;
            }
        }
    }
}
