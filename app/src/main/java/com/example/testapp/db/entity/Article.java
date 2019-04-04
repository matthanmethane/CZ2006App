package com.example.testapp.db.entity;

import androidx.annotation.NonNull;

/**
 * entity Article class.
 */
public class Article {
    /**
     * The activity_news_view article title.
     */
    @NonNull
    private String title;

    /**
     * The activity_news_view article website link.
     */
    @NonNull
    private String url;

    /**
     * The source of the activity_news_view article
     */
    @NonNull
    private SourceType source;

    /**
     * Instantiates a activity_news_view article entity
     * @param title     the activity_news_view article title
     * @param url       the website link to the activity_news_view
     * @param source    the source of the activity_news_view article
     */
    public Article(String title, String url, SourceType source) {
        this.title = title;
        this.url = url;
        this.source = source;
    }

    /**
     * Get title
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Get website link
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * Get source information
     * @return source
     */
    public SourceType getSource() { return source; }
}
