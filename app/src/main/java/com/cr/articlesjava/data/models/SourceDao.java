package com.cr.articlesjava.data.models;

import com.cr.articlesjava.domain.models.Source;

public class SourceDao {
    private String id;
    private String name;

    public String getId() { return id; }
    public String getName() { return name; }

    public SourceDao(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public Source toSource() {
        return new Source(this.id, this.name);
    }

}
