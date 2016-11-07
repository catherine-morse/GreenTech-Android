package edu.udel.cisc475.team1.greentech_android;

/**
 * Created by writingcenter on 11/6/16.
 */

public class Resource {
    private String title;
    private String website;
    private String description;

    public Resource() {

    }

    public Resource(String title, String website, String description) {
        this.title = title;
        this.website = website;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getWebsite() {
        return website;
    }

    public String getDescription() {
        return description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
