package com.jackson.processcommunication.process;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.jackson.processcommunication.R;
import com.jackson.processcommunication.serializable.Book;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThirdActivity extends AppCompatActivity {


    @BindView(R.id.tv_show)
    TextView tvShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        ButterKnife.bind(this);

        Book book=getIntent().getParcelableExtra("book");
        tvShow.setText(String.format(getResources().getString(R.string.tv_show), book.getBookId(), book.getBookName(), book.isNative()));

    }
}
