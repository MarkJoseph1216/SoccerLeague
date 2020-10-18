package com.example.soccerleague;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

@Root(name = "rss", strict = false)
public class News {
    @Element(name = "channel")
    private Channel channel;

    public Channel getChannel ()
    {
        return channel;
    }

    @Root(name = "channel", strict = false)
    public static class Channel{
        @ElementList(name = "item", inline = true, required = false)
        private List<Item> item;

        public List<Item> getItem() {
            return item;
        }

        @Root(name = "item", strict = false)
        public static class Item{
            @Element(name = "link")
            private String link;
            @Element(name = "description")
            private String description;
            @Element(name = "title")
            private String title;
            @ElementList(name = "category", inline = true, required = false)
            private List<String> category;
            @Element(name = "pubDate")
            private String pubDate;

            public String getLink() {
                return link;
            }

            public String getDescription() {
                return description;
            }

            public String getTitle() {
                return title;
            }

            public List<String> getCategory() {
                return category;
            }

            public String getPubDate() {
                return pubDate;
            }
        }
    }
}