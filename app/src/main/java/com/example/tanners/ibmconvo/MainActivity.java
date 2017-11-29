package com.example.tanners.ibmconvo;

import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.tanners.ibmconvo.data.WatsonResponse;
import com.ibm.watson.developer_cloud.conversation.v1.ConversationService;
import com.ibm.watson.developer_cloud.*;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ConvoAdapter convoAdapter;
    private String userInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button requestBtn = (Button) findViewById(R.id.convoRequestBtn);
        final EditText request = (EditText) findViewById(R.id.convoRequest);

        requestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userInput = request.getText().toString().replace("\n", " ");
//                ((ConvoAdapter)(mRecyclerView.getAdapter())).updateAdapter(input);
                new ConversationRequestCall().execute(userInput);
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.convo_list);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        convoAdapter = new ConvoAdapter(this);
        mRecyclerView.setAdapter(convoAdapter);
    }


    private class ConversationRequestCall extends AsyncTask<String, Void, WatsonResponse> {
        private ConnectionRequest request;

        @Override
        protected void onPreExecute() {
            request = new ConnectionRequest("https://9a057f2f-c27b-47da-8979-861cd97eaff1:zv0WWXCWN7G0@gateway.watsonplatform.net/conversation/api/v1/workspaces/08616136-d7c6-4e79-aae2-acf3df17ef14/message?version=2017-05-26");
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected WatsonResponse doInBackground(String... params) {
//            request.addBasicBody("{ \"input\":{ \"text\":\"" + params[0] + "\" } }");
            request.addBasicBody(" {\n" +
                    "                                                                      \"input\": {\n" +
                    "                                                                        \"text\": \"" +  params[0] + "\"\n" +
                    "                                                                      },\n" +
                    "                                                                      \"context\": {\n" +
                    "                                                                        \"conversation_id\": \"bd3756f9-9846-47ce-8d54-7d163a2f4e16\",\n" +
                    "                                                                        \"system\": {\n" +
                    "                                                                          \"dialog_stack\": [\n" +
                    "                                                                            {\n" +
                    "                                                                              \"dialog_node\": \"root\"\n" +
                    "                                                                            }\n" +
                    "                                                                          ],\n" +
                    "                                                                          \"dialog_turn_counter\": 1,\n" +
                    "                                                                          \"dialog_request_counter\": 1,\n" +
                    "                                                                          \"_node_output_map\": {\n" +
                    "                                                                            \"Welcome\": [\n" +
                    "                                                                              0\n" +
                    "                                                                            ]\n" +
                    "                                                                          },\n" +
                    "                                                                          \"branch_exited\": true,\n" +
                    "                                                                          \"branch_exited_reason\": \"completed\"\n" +
                    "                                                                        }\n" +
                    "                                                                      }\n" +
                    "                                                                    }");
            request.setBasicAuth("9a057f2f-c27b-47da-8979-861cd97eaff1","zv0WWXCWN7G0");
            request.addRequestHeader("Content-Type","application/json");
            request.connect();

            WatsonResponse watsonResponse = null;

            try {
                watsonResponse = request.getResponse();
            } catch (IOException e) {
                e.printStackTrace();
            }
            request.closeConnection();
//            Log.i("RESPONSE", response);

            return watsonResponse;
        }

        @Override
        protected void onPostExecute(WatsonResponse result) {
            ((ConvoAdapter)(mRecyclerView.getAdapter())).updateAdapter(new ConvoAdapter.DataWrapper(false, userInput));
            ((ConvoAdapter)(mRecyclerView.getAdapter())).updateAdapter(new ConvoAdapter.DataWrapper(true, arrayToString(result.getOutput().getText())));
        }

        @Override
        protected void onProgressUpdate(Void... values) {

        }

        private String arrayToString(ArrayList<String> arg)
        {
            StringBuilder builder = new StringBuilder();
            for(String str : arg)
            {
                builder.append(str);
                builder.append(" ");
            }

            return builder.toString();
        }
    }
}


//    curl -X POST --header 'Content-Type: application/json' --header 'Accept: application/json' -d '{ \
//            "input": { \
//        "text": "Turn on the lights" \
//    }, \
//            "alternate_intents": true \
//}' 'https://watson-api-explorer.mybluemix.net/conversation/api/v1/workspaces/0a0c06c1-8e31-4655-9067-58fcac5134fc/message?version=2017-05-26'
//

//    private void setUpConversationCreds()
//    {
//        ConversationService service = new ConversationService("2017-05-26");
////        ConversationService service = new ConversationService(ConversationService.VERSION_DATE_2016_07_11);
//        service.setUsernameAndPassword("3c6cfc06-1558-4031-8ed6-ac13295edb39", "rkulETlEJQk5");
//
//
//
//    }
//}

