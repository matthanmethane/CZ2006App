package com.example.testapp.db.dao;

import android.os.AsyncTask;

import com.example.testapp.db.entity.Article;
import com.example.testapp.db.entity.SourceType;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

/**
 * The news article Dao.
 */
public class ArticleDao_Impl extends AsyncTask<Void, Void, Article[]> {

    private Article[] articles;

    /**
     * Class constructor.
     */
    public ArticleDao_Impl() {
    }

    /**
     * Run the retrieval of news articles from StraitTimes and CNA in the background.
     * @param voids
     * @return list of articles
     */
    @Override
    protected Article[] doInBackground(Void... voids) {
        getAllArticlesAsArray();
        return articles;
    }

    /**
     * Retrieve education related activity_news_view article from Straits Times
     * @return a list of articles
     */
    private Article[] retrieveST() {
        // Variables
        Article[] a = new Article[10];
        SourceType source = SourceType.StraitsTimes;

        // fetch html of the site
        try {
            final Document stDocument = Jsoup.connect("https://www.straitstimes.com/singapore/education").get();
            Elements news = stDocument.select("div.view.view-articles.view-id-articles > div.view-content > div");
            System.out.println("Generating news articles");
            // For each news section
            for (int i = 0; i < 10; i++) {
                Element row = news.get(i);

                // Retrieve Title
                String title = row.getElementsByClass("story-headline").text();

                // Retrieve Link
                String url = "https://www.straitstimes.com" + row.getElementsByAttribute("href").attr("href");

                // Generate Article
                Article article = new Article(title, url, source);
                a[i] = article;
            }
            return a;
        } catch(IOException e) {
            return new Article[0];
        }
    }

    /**
     * Retrieve education related activity_news_view article from Channel NewsAsia
     * @return a list of articles
     */
    private Article[] retrieveCNA() {
        // Variables
        Article[] a = new Article[10];
        SourceType source = SourceType.ChannelNewsAsia;

        // fetch html of the site
        try {
            final Document cnaDocument = Jsoup.connect("https://www.channelnewsasia.com/news/topic/education").get();
            Elements news = cnaDocument.select("li.result-section__list-item");
            int i = 0;
            // For each news article
            for (Element row : news) {
                // Retrieve Title
                String title = row.getElementsByClass("teaser__title").text();

                // Retrieve URL
                String url = "https://www.channelnewsasia.com" + row.getElementsByClass("teaser__title").attr("href");

                // Generate article
                Article article = new Article(title, url, source);
                a[i] = article;
                i++;
            }
            return a;
        } catch (IOException e) {
            return new Article[0];
        }
    }

    private void getAllArticlesAsArray() {
        // Retrieve all activity_news_view
        Article[] CNAnews = retrieveCNA();
        Article[] STnews = retrieveST();

        // Sort activity_news_view together
        Article[] newsList = new Article[20];
        int i = 0;
        for (int j = 0; j < 10; j ++) {
            newsList[i] = CNAnews[j];
            newsList[i+1] = STnews[j];
            i = i + 2;
        }
        articles = newsList;
    }

    /**
     * Returns a list of recent education-related news articles from Straits Times and CNA
     * @return list of recent news articles
     */
    public Article[] getArticleList() {
        return articles;
    }
}
