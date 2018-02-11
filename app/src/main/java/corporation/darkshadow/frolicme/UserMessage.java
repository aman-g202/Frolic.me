package corporation.darkshadow.frolicme;

/**
 * Created by darkshadow on 30/11/17.
 */

public class UserMessage {

    private String inputmessage;
    public int messagecount;

    public int getMessagecount() {
        return messagecount;
    }

    public void setMessagecount(int messagecount) {
        this.messagecount = messagecount;
    }

    public String getInputmessage() {
        return inputmessage;
    }

    public void setInputmessage(String inputmessage) {
        this.inputmessage = inputmessage;
    }

    public UserMessage() {
    }

    public UserMessage(String inputmessage,int messagecount) {
        this.inputmessage = inputmessage;
        this.messagecount = messagecount;
    }

}
