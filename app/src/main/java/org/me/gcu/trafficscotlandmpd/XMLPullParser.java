package org.me.gcu.trafficscotlandmpd;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

// Craig Burke S2024071

public class XMLPullParser
{
    private ArrayList<Item> items;
    private Item item;
    private String input;
    private Format format;

    public XMLPullParser()
    {
        items = new ArrayList<>();
        format = new Format();
    }

    public void parseData(String data)
    {
        try
        {
            // https://developer.android.com/reference/org/xmlpull/v1/XmlPullParser
            // creation of factory to allow implementation of pull parser
            // to extract date from rss feed

            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            factory.setNamespaceAware(true);
            XmlPullParser xpp = factory.newPullParser();
            xpp.setInput(new StringReader(data));
            int eventType = xpp.getEventType();
            item = new Item();

            while (eventType != XmlPullParser.END_DOCUMENT)
            {
                String tag = xpp.getName();
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tag.equalsIgnoreCase("item")) {
                            item = new Item();
                        }
                        break;

                    case XmlPullParser.TEXT:
                        input = xpp.getText();
                        break;

                    case XmlPullParser.END_TAG:
                        // parser searches for an entry called item. equalsIgnoreCase
                        // it will search for this entry whether upper or lower case.
                        if (tag.equalsIgnoreCase("item")) {
                            if (!(item.getTitle() == null)) {
                                // if the title tag in the feed is not empty then add the
                                // entry to the items ArrayList
                                items.add(item);
                            }
                        }

                        else if (tag.equalsIgnoreCase("title"))
                        {
                            // Search for the title tag and set the title
                            // within the item tag to the input received
                            // from the rss feed.
                            item.setTitle(input);
                        }

                        else if (tag.equalsIgnoreCase("description"))
                        {
                            String[] dateStrings = format.getDateStrings(input);
                            String formatted = format.addNewLine(input);
                            item.setDescription(formatted);
                            if (dateStrings != null) {
                                item.setStartDate(format.getCalendarFromString(dateStrings[0]));
                                item.setEndDate(format.getCalendarFromString(dateStrings[1]));
                                item.setSeverityLevel(format.getLengthOfDisruption(item.getStartDate(), item.getEndDate()));
                            }
                        }

                        else if (tag.equalsIgnoreCase("link"))
                        {
                            if (item.getLink() == null)
                            {
                                item.setLink(input);
                            }
                        }

                        else if (tag.equalsIgnoreCase("point"))
                        {
                            double[] latLng = format.getLatLng(input);
                            item.setGeorss(input);
                            item.setLatLng(latLng);
                        }

                        else if (tag.equalsIgnoreCase("author"))
                        {
                            if (item.getAuthor() == " ")
                            {
                                item.setAuthor(input);
                            }
                            else
                            {
                                item.setAuthor(input = "Unknown Author");
                            }
                        }

                        else if (tag.equalsIgnoreCase("comments"))
                        {
                            if (item.getComments() == " ")
                            {
                                item.setComments(input);
                            }
                            else
                            {
                                item.setComments(input = "No Additional Comments Noted");
                            }
                        }
                        else if (tag.equalsIgnoreCase("pubDate"))
                        {
                            item.setPubDate(input);
                        }
                        break;

                    default:
                        break;
                }
                eventType = xpp.next();
            }
            MainActivity.items = items;
        }
        catch (XmlPullParserException | IOException e)
        {
            e.printStackTrace();
        }
    }
}
