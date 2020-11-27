package client;

import com.beust.jcommander.Parameter;

public class Message {

    @Parameter(names = "-t")
    private String type;

    @Parameter(names = "-i")
    private String index;

    @Parameter(names = "-m")
    private String content;


    public Message() {
        this.content = "";
        this.index = "";
        this.content = "";
    }

    public String getType() {
        return type;
    }

    public String  getIndex() {
        return index;
    }

    public String getContent() {
        return content;
    }
}