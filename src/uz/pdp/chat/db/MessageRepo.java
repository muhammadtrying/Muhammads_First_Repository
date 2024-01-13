package uz.pdp.chat.db;

import uz.pdp.chat.entity.Message;
import uz.pdp.chat.entity.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class MessageRepo implements Repository<Message> {
    List<Message> messages;
    private static MessageRepo messageRepo;
    private static final String PATH = "src/uz/pdp/chat/db/messages_db.txt";

    private MessageRepo(List<Message> messages) {
        this.messages = messages;
    }

    public static MessageRepo getInstance() {
        if ( messageRepo == null ) {
            messageRepo = new MessageRepo(loadData());
        }
        return messageRepo;
    }

    @SuppressWarnings("unchecked")
    private static List<Message> loadData() {
        try (InputStream inputStream = new FileInputStream(PATH);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)
        ) {
            return (List<Message>) objectInputStream.readObject();
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void save(Message message) {
        messages.add(message);
        uploadData();
    }

    private void uploadData() {
        try (
         OutputStream outputStream = new FileOutputStream(PATH);
         ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)
        ) {
            objectOutputStream.writeObject(messages);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Message> findAll() {
        return messages;
    }

    @Override
    public void delete(Message message) {
        messages.remove(message);
    }
}
