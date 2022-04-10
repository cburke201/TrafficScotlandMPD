package org.me.gcu.trafficscotlandmpd;

import java.io.Serializable;
import java.util.Calendar;

// Craig Burke S2024071

// https://developer.android.com/reference/java/io/Serializable
public class Item implements Serializable
{
    private String title;
    private String description;
    private Calendar startDate;
    private Calendar endDate;
    private int severityLevel;
    private String link;
    private String georss;
    private double[] latLng;
    private String author;
    private String comments;
    private String pubDate;

    public Item() {}

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Calendar getStartDate()
    {
        return startDate;
    }

    public void setStartDate(Calendar startDate)
    {
        this.startDate = startDate;
    }

    public Calendar getEndDate()
    {
        return endDate;
    }

    public void setEndDate(Calendar endDate)
    {
        this.endDate = endDate;
    }

    public int getSeverityLevel()
    {
        return severityLevel;
    }

    public void setSeverityLevel(int severityLevel)
    {
        this.severityLevel = severityLevel;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink(String link)
    {
        this.link = link;
    }

    public String getGeorss()
    {
        return georss;
    }

    public void setGeorss(String georss)
    {
        this.georss = georss;
    }

    public double[] getLatLng()
    {
        return latLng;
    }

    public void setLatLng(double[] latLng)
    {
        this.latLng = latLng;
    }

    public String getAuthor()
    {
        return author;
    }

    public void setAuthor(String author)
    {
        this.author = author;
    }

    public String getComments()
    {
        return comments;
    }

    public void setComments(String comments)
    {
        this.comments = comments;
    }

    public String getPubDate()
    {
        return pubDate;
    }

    public void setPubDate(String pubDate)
    {
        this.pubDate = pubDate;
    }
}
