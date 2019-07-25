package com.example.supernote.view.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.supernote.R;
import com.example.supernote.view.socketIO.SocketIO_Class;
import com.github.nkzawa.emitter.Emitter;
import com.github.nkzawa.socketio.client.Socket;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FragmentChat extends Fragment implements View.OnClickListener {
    EditText edt_sendName, edt_sendMes;
    Button btn_sendName, btn_sendMes;
    Socket socket = new SocketIO_Class().inIt();
    ListView simple_list;
    ArrayAdapter<String> adapter;
    List<String> contentMessList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_chat,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        inIt(view);
        btn_sendMes.setOnClickListener(this);
        btn_sendName.setOnClickListener(this);
        socket.connect();
//        ContentMess contentMess = new ContentMess();
//        contentMess.setContent("jaja");
        adapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_list_item_activated_1,contentMessList);
        simple_list.setAdapter(adapter);
    }

    private void inIt(View view) {
        edt_sendMes = view.findViewById(R.id.edt_content_chat);
        edt_sendName = view.findViewById(R.id.edtname_socket);
        btn_sendName = view.findViewById(R.id.btn_send_name);
        btn_sendMes = view.findViewById(R.id.btn_send_content);
        simple_list = view.findViewById(R.id.simple_list);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_send_content:
                if(edt_sendMes.getText().toString().length() ==0){
        Toast.makeText(getContext(),"Không được bỏ trống!",Toast.LENGTH_SHORT).show();
        }
        else{
                    socket.emit("client_send_message",edt_sendMes.getText().toString());
                    edt_sendMes.setText("");
                    edt_sendName.setVisibility(View.INVISIBLE);
                    btn_sendName.setVisibility(View.INVISIBLE);
                    edt_sendMes.requestFocus();
        }
        break;
        case R.id.btn_send_name:
        if(edt_sendName.getText().toString().length() == 0){
        Toast.makeText(getContext(),"Không được bỏ trống",Toast.LENGTH_SHORT).show();
        }
       else{
            edt_sendName.setVisibility(View.INVISIBLE);
            btn_sendName.setVisibility(View.INVISIBLE);
            edt_sendMes.setVisibility(View.VISIBLE);
            btn_sendMes.setVisibility(View.VISIBLE);
            socket.emit("client_send_username",edt_sendName.getText().toString());
            socket.on("server_send_mess",onNewMessage);
        }
        break;
        }
        }
    private Emitter.Listener onNewMessage = new Emitter.Listener() {
        @Override
        public void call(final Object... args) {
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    JSONObject data = (JSONObject) args[0];
                    String message;
                    try {
                        message = data.getString("dulieu");
                        contentMessList.add(message);
                        adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        return;
                    }

                }
            });
        }
    };
        }
