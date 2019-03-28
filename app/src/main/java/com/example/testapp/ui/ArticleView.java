package com.example.testapp.ui;

import android.content.Intent;
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

        // Network on main thread exception
        //StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        //StrictMode.setThreadPolicy(policy);

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

            TextView title = new TextView(this);
            title.setText(articleList[i].getTitle());
            title.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 35);

            TextView source = new TextView(this);
            source.setText(articleList[i].getSource().toString());
            source.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
            source.setGravity(Gravity.RIGHT);

            // Link news url to view
            String url = articleList[i].getUrl();

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    Intent intent = new Intent(Intent.ACTION_VIEW,
                            Uri.parse(url));
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
