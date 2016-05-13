package com.rssreader;

import java.util.ArrayList;

/**
 * Created by kshitij.sharma on 5/12/2016.
 */
public class Channel {
    private String title;
    private String link;
    private String description;

    private String language;
    private String copyRight;
    private String managingEditor;
    private String webMaster;
    private String pubDate;
    private String lastBuildDate;
    private String category;
    private String generator;
    private String docs;
    private Cloud cloud;
    private String ttl;
    private Image image;
    private ArrayList<Item> items;
    //private Image textInput;
    //private Image skipHours;
    //private Image skipDays;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCopyRight() {
        return copyRight;
    }

    public void setCopyRight(String copyRight) {
        this.copyRight = copyRight;
    }

    public String getManagingEditor() {
        return managingEditor;
    }

    public void setManagingEditor(String managingEditor) {
        this.managingEditor = managingEditor;
    }

    public String getWebMaster() {
        return webMaster;
    }

    public void setWebMaster(String webMaster) {
        this.webMaster = webMaster;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getLastBuildDate() {
        return lastBuildDate;
    }

    public void setLastBuildDate(String lastBuildDate) {
        this.lastBuildDate = lastBuildDate;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenerator() {
        return generator;
    }

    public void setGenerator(String generator) {
        this.generator = generator;
    }

    public String getDocs() {
        return docs;
    }

    public void setDocs(String docs) {
        this.docs = docs;
    }

    public Cloud getCloud() {
        return cloud;
    }

    public void setCloud(Cloud cloud) {
        this.cloud = cloud;
    }

    public String getTtl() {
        return ttl;
    }

    public void setTtl(String ttl) {
        this.ttl = ttl;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    class Cloud {
        private String domain;
        private String port;
        private String path;
        private String registerProcedure;
        private String protocol;

        public String getDomain() {
            return domain;
        }

        public void setDomain(String domain) {
            this.domain = domain;
        }

        public String getPort() {
            return port;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public String getRegisterProcedure() {
            return registerProcedure;
        }

        public void setRegisterProcedure(String registerProcedure) {
            this.registerProcedure = registerProcedure;
        }

        public String getProtocol() {
            return protocol;
        }

        public void setProtocol(String protocol) {
            this.protocol = protocol;
        }
    }

    class Image {
        private String url;
        private String title;
        private String link;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }
    }

    class Item {
        private String title;
        private String link;
        private String description;
        private String author;
        private Category category;
        private String comments;
        private Enclosure enclosure;
        private Guid guid;
        private String pubDate;
        private Source source;

        public Item() {
        }

        public Item(Item item) {
            this.title = item.getTitle();
            this.link = item.getLink();
            this.description = item.getDescription();
            this.author = item.getAuthor();
            this.category = item.getCategory();
            this.comments = item.getComments();
            this.enclosure = item.getEnclosure();
            this.guid = item.getGuid();
            this.pubDate = item.getPubDate();
            this.source = item.getSource();
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public Category getCategory() {
            return category;
        }

        public void setCategory(Category category) {
            this.category = category;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
        }

        public Enclosure getEnclosure() {
            return enclosure;
        }

        public void setEnclosure(Enclosure enclosure) {
            this.enclosure = enclosure;
        }

        public Guid getGuid() {
            return guid;
        }

        public void setGuid(Guid guid) {
            this.guid = guid;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public Source getSource() {
            return source;
        }

        public void setSource(Source source) {
            this.source = source;
        }

        class Source {
            private String val;
            private String attrUrl;

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public String getAttrUrl() {
                return attrUrl;
            }

            public void setAttrUrl(String attrUrl) {
                this.attrUrl = attrUrl;
            }
        }

        class Enclosure {
            String val;
            String attrUrl;
            String attrlength;
            String attrtype;

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public String getAttrUrl() {
                return attrUrl;
            }

            public void setAttrUrl(String attrUrl) {
                this.attrUrl = attrUrl;
            }

            public String getAttrlength() {
                return attrlength;
            }

            public void setAttrlength(String attrlength) {
                this.attrlength = attrlength;
            }

            public String getAttrtype() {
                return attrtype;
            }

            public void setAttrtype(String attrtype) {
                this.attrtype = attrtype;
            }
        }

        class Category {
            private String val;
            private String attrDomain;

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public String getAttrDomain() {
                return attrDomain;
            }

            public void setAttrDomain(String attrDomain) {
                this.attrDomain = attrDomain;
            }
        }

        class Guid {
            private String val;
            private String attrisPermaLink;

            public String getVal() {
                return val;
            }

            public void setVal(String val) {
                this.val = val;
            }

            public String getAttrisPermaLink() {
                return attrisPermaLink;
            }

            public void setAttrisPermaLink(String attrisPermaLink) {
                this.attrisPermaLink = attrisPermaLink;
            }
        }


    }
}
