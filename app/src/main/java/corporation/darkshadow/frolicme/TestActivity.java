package corporation.darkshadow.frolicme;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ai.api.AIDataService;
import ai.api.AIListener;
import ai.api.AIServiceException;
import ai.api.android.AIConfiguration;
import ai.api.android.AIService;
import ai.api.model.AIError;
import ai.api.model.AIRequest;
import ai.api.model.AIResponse;

/**
 * Created by darkshadow on 30/11/17.
 */

public class TestActivity extends AppCompatActivity implements AIListener {

    final private List<UserMessage> userMessageList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MessageAdapter mAdapter;
    EditText editTextchatinput;
    ImageButton buttonchatsubmit;
    ImageView fab;
    AIService aiService;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat_layout);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        editTextchatinput = (EditText)findViewById(R.id.edittextchatinput);
        buttonchatsubmit = (ImageButton) findViewById(R.id.buttonchatsubmit);
        fab = (ImageView) findViewById(R.id.fab);

        mAdapter = new MessageAdapter(userMessageList,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);


        //                          <!----To grant audio permission ----------->

        int permission = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECORD_AUDIO);

        if (permission != PackageManager.PERMISSION_GRANTED) {

            makeRequest();
        }

        //                            <!----To connect with Dialog Flow ----------->

        final AIConfiguration config = new AIConfiguration("6a56035db82e49d3ba5304b8b20d3037",
                AIConfiguration.SupportedLanguages.English,
                AIConfiguration.RecognitionEngine.System);
        aiService = AIService.getService(this, config);
        aiService.setListener(TestActivity.this);


        buttonchatsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editTextchatinput.getText().toString();
                UserMessage userMessage = new UserMessage(message,count);
                userMessageList.add(userMessage);
                count++;
                editTextchatinput.setText("");
                mAdapter.notifyDataSetChanged();
                final AIRequest aiRequest = new AIRequest();
                aiRequest.setQuery(message);
                Process chatbot = new Process();
                chatbot.execute(aiRequest);

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                aiService.startListening();
                Toast.makeText(TestActivity.this,"Listening...",Toast.LENGTH_LONG).show();
            }
        });

    }

    protected void makeRequest() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.RECORD_AUDIO},
                101);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 101: {

                if (grantResults.length == 0
                        || grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {


                } else {

                }
                return;
            }
        }
    }

    @Override
    public void onResult(final AIResponse result) {

        String m1 = result.getResult().getResolvedQuery();
        final UserMessage userMessage = new UserMessage(m1,count);
        userMessageList.add(userMessage);
        count++;
        mAdapter.notifyDataSetChanged();

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Do something after 5s = 5000ms
                //buttons[inew][jnew].setBackgroundColor(Color.BLACK);
                String m2 = result.getResult().getFulfillment().getSpeech();
                final UserMessage userMessage = new UserMessage(m2,count);
               // userMessage = new UserMessage(m2,count);
                userMessageList.add(userMessage);
                count++;
                mAdapter.notifyDataSetChanged();
            }
        }, 2000);


    }

    @Override
    public void onError(AIError error) {

    }

    @Override
    public void onAudioLevel(float level) {

    }

    @Override
    public void onListeningStarted() {

    }

    @Override
    public void onListeningCanceled() {

    }

    @Override
    public void onListeningFinished() {

    }

    public class Process extends AsyncTask<AIRequest,Void,AIResponse> {

        @Override
        protected AIResponse doInBackground(AIRequest... aiRequests) {
            final AIConfiguration config = new AIConfiguration("6a56035db82e49d3ba5304b8b20d3037",
                    AIConfiguration.SupportedLanguages.English,
                    AIConfiguration.RecognitionEngine.System);
            final AIRequest request = aiRequests[0];
           // Toast.makeText(TestActivity.this,"aman",Toast.LENGTH_LONG).show();
            AIDataService aiDataService = new AIDataService(config);
            try {
                final AIResponse response = aiDataService.request(request);
                return response;
            } catch (AIServiceException e) {
            }
            return null;
        }

        @Override
        protected void onPostExecute(AIResponse aiResponse) {
           // Toast.makeText(TestActivity.this,"aman",Toast.LENGTH_LONG).show();
            if (aiResponse != null) {
                String r1 = aiResponse.getResult().getFulfillment().getSpeech();
               // Toast.makeText(TestActivity.this,"aman",Toast.LENGTH_LONG).show();
                UserMessage userMessage = new UserMessage(r1,count);
                userMessageList.add(userMessage);
                count++;
                //  UsersMessage aman = messageList.get(1);
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
