package server;

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
        readLock.lock();
        currentData = database.getData();

        result = currentData.getOrDefault(key, null);
        readLock.unlock();
        return result;
    }

    public void setData(String key, String value) {
        writeLock.lock();
        currentData = database.getData();
        currentData.put(key, value);
        database.updateDatabase(currentData);
        writeLock.unlock();
    }

    public void deleteData(String key) {
        writeLock.lock();
        currentData = database.getData();
        currentData.remove(key);
        database.updateDatabase(currentData);
        writeLock.unlock();
    }

}
