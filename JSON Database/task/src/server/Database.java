package server;

public class Database {

    String[] array;

    public Database(int size) {
        this.array = new String[size];

        for (String s : array) {
            s = "";
        }

    }

    public void setData(int index, String data) {
        this.array[index] = data;
    }


    public String getData(int index) {
        return this.array[index];
    }


    public void deleteData(int index) {
        this.array[index] = "";
    }
}
