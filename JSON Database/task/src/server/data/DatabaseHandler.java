package server.data;

import server.Database;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class DatabaseHandler {
    private Database database;
    Map<String, String> currentData;
    ReadWriteLock lock;
    Lock readLock, writeLock;

    public DatabaseHandler(Database database) {
        this.database = database;
        currentData = new HashMap<>();
        lock = new ReentrantReadWriteLock();
        readLock = lock.readLock();
        writeLock = lock.writeLock();
    }

    public String getData(String key) {

        String result = null;
        currentData = database.getData();

        result = currentData.get(key);
        return result;
    }

    public void setData(String key, String value) {
        currentData = database.getData();
        currentData.put(key, value);
        database.updateDataBase(currentData);
    }
}
