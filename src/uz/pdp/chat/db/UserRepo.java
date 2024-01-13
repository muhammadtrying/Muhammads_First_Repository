package uz.pdp.chat.db;

import uz.pdp.chat.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;


public class UserRepo implements Repository<User> {

    List<User> users;
    private static UserRepo userRepo;
    private static final String PATH = "src/uz/pdp/chat/db/user_db.txt";

    private UserRepo(List<User> users) {
        this.users = users;
    }

    public static UserRepo getInstance() {
        if ( userRepo == null ) {
            userRepo = new UserRepo(loadData());
        }
        return userRepo;
    }

    @SuppressWarnings("unchecked")
    private static List<User> loadData() {
        try (InputStream inputStream = new FileInputStream(PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            return (List<User>) objectInputStream.readObject();
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(User user) {
        users.add(user);
        uploadData();
    }

    private void uploadData() {
        try (
         OutputStream outputStream = new FileOutputStream(PATH);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(users);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<User> findAll() {
        return users;
    }


    @Override
    public void delete(User user) {
        users.remove(user);
    }
}
