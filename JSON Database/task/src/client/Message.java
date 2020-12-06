package client;

import com.beust.jcommander.Parameter;

public class Message {

    @Parameter(names = "-t")
    private String type;
    @Parameter(names = "-k")
    private String key;
    @Parameter(names = "-v")
    private String value;
    @Parameter(names = "-in")
    private String inputFile;
    private String response;
    private String reason;

    public Message() {
    }

    public Message(String type, String key, String value, String response, String reason) {
        this.type = type;
        this.key = key;
        this.value = value;
        this.response = response;
        this.reason = reason;
    }


    public static final class Builder {
        private String type;
        private String key;
        private String value;
        private String response;
        private String reason;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder key(String key) {
            this.key = key;
            return this;
        }

        public Builder value(String value) {
            this.value = value;
            return this;
        }

        public Builder response(String response) {
            this.response = response;
            return this;
        }

        public Builder reason(String reason) {
            this.reason = reason;
            return this;
        }

        public Message build() {
            return new Message(type, key, value, response, reason);
        }
    }

    public String getType() {
        return type;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }

    public String getResponse() {
        return response;
    }

    public String getReason() {
        return reason;
    }

    public String getInputFile() {
        return inputFile;
    }
}