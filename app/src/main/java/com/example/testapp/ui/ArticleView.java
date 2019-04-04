package com.example.testapp.ui;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.testapp.R;
import com.example.testapp.db.dao.ArticleDao_Impl;
import com.example.testapp.db.entity.Article;

import androidx.appcompat.app.AppCompatActivity;

public class ArticleView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_view);

        ArticleDao_Impl articleDao = new ArticleDao_Impl();
        try {
            articleDao.execute().get();
        } catch (Exception e) {
            System.out.println("Error occurred.");
        }

        Article[] articleList = articleDao.getArticleList();

        RelativeLayout rl=(RelativeLayout)findViewById(R.id.rl);
        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new ScrollView.LayoutParams(
                ScrollView.LayoutParams.MATCH_PARENT, ScrollView.LayoutParams.MATCH_PARENT
        ));
        LinearLayout ll = new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT));
        ll.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(ll);

        // for each article
        for (int i = 0; i < articleList.length; i ++) {
            LinearLayout linearLayout = new LinearLayout(this);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            linearLayout.setBackgroundResource(R.drawable.border);

            TextView title = new TextView(this);
            title.setText(articleList[i].getTitle());
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 25);
            title.setTextColor(Color.BLACK);
            title.setPadding(20,20,20,20);

            TextView source = new TextView(this);
            source.setText(articleList[i].getSource().toString());
            source.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 15);
            source.setGravity(Gravity.RIGHT);

            // Link news url to view
            String url = articleList[i].getUrl();

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                }
            });

            linearLayout.addView(title);
            linearLayout.addView(source);

            ll.addView(linearLayout);
        }
        rl.addView(scrollView);
    }
}
