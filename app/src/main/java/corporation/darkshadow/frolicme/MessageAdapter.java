package corporation.darkshadow.frolicme;

/**
 * Created by darkshadow on 30/11/17.
 */

import android.content.Context;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter{

    private List<UserMessage> messageList;
    Context mcontext;

    public static class TextTypeSender extends RecyclerView.ViewHolder {

        TextView testmessagesent;

        public TextTypeSender(View itemView) {
            super(itemView);

            this.testmessagesent = (TextView) itemView.findViewById(R.id.text_message_sent);
        }
    }

    public static class TextTypeReceiver extends RecyclerView.ViewHolder {

        TextView testmessagereceive;

        public TextTypeReceiver(View itemView) {
            super(itemView);

            this.testmessagereceive = (TextView) itemView.findViewById(R.id.text_message_receive);
        }
    }

    public MessageAdapter(List<UserMessage> messageList,Context mcontext) {
        this.messageList = messageList;
        this.mcontext = mcontext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_sent, parent, false);
                return new TextTypeSender(view);
            case 1:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_list_receive, parent, false);
                return new TextTypeReceiver(view);
        }

        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        int aman;
        UserMessage object = messageList.get(position);
        int temp24 = object.getMessagecount();

        if (temp24%2==0){
            aman=0;
        }
        else{
            aman=1;
        }

        if (object != null) {
            switch (aman) {
                case 0:
                    ((TextTypeSender) holder).testmessagesent.setText(object.getInputmessage());

                    break;
                case 1:
                    ((TextTypeReceiver) holder).testmessagereceive.setText(object.getInputmessage());
                    break;
            }
        }

    }

    @Override
    public int getItemViewType(int position) {

        int temp;
        int play = messageList.get(position).getMessagecount();
        if (play%2==0){
            temp=0;
        }
        else {
            temp=1;
        }

        switch (temp) {
            case 0:
                return temp;
            case 1:
                return temp;
            default:
                return -1;
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

}