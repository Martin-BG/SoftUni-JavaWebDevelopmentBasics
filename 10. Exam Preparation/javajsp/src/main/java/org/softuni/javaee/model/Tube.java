package org.softuni.javaee.model;

public final class Tube {

    private final String title;
    private final String description;
    private final int views;
    private final String uploader;

    public Tube(final String title,
                final String description,
                final int views,
                final String uploader) {
        this.title = title;
        this.description = description;
        this.views = views;
        this.uploader = uploader;
    }

    public String getTitle() {
        return this.title;
    }

    public String getDescription() {
        return this.description;
    }

    public int getViews() {
        return this.views;
    }

    public String getUploader() {
        return this.uploader;
    }
}
