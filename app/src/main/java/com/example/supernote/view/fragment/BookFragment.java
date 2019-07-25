package com.example.supernote.view.fragment;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.supernote.model.Book;
import com.example.supernote.view.Behavior_scroll;
import com.example.supernote.view.adapter.BookAdapter;
import com.example.supernote.view.database.Database;
import com.example.supernote.R;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {
    Database database;
    RecyclerView recyclerView;
    TextView tvtest;
    List<Book> bookList = new ArrayList<>();
    BookAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_book,container,false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        tvtest = view.findViewById(R.id.tv_test);
        recyclerView = view.findViewById(R.id.recyle_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new BookAdapter(getContext(),bookList);
        recyclerView.setAdapter(adapter);
        database = new Database(getContext(),"quanlysach.db",null,1);
        database.QueryData("CREATE TABLE IF NOT EXISTS tbl_sach(id integer primary key autoincrement,tensach varchar,price integer)");
       // database.QueryData("INSERT INTO tbl_sach VALUES(null,'Những cậu bé dũng cảm',25000)");
        Cursor cursor = database.getData("SELECT * FROM tbl_sach");
        while (cursor.moveToNext()){
            int id = cursor.getInt(0);
            String name_book = cursor.getString(1);
            int price = cursor.getInt(2);
            Book book = new Book(id,name_book,price);
            bookList.add(book);
            adapter.notifyDataSetChanged();
        }
    }
}
