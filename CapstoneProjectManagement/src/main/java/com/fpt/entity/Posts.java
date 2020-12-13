package com.fpt.entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

@NamedEntityGraph(
        name = "post-entity-graph-with-comment-users",
        attributeNodes = {
                @NamedAttributeNode("author"),
                @NamedAttributeNode("files"),
                @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),
        }
)
@Entity
@Table(name = "[Posts]")
public class Posts {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "INT")
    private Integer id;
    @Column(name = "title", columnDefinition = "NVARCHAR(256)")
    private String title;
    @Column(name = "description", columnDefinition = "longtext")
    private String description;
    @Column(name = "created_date", columnDefinition = "DATETIME")
    private Date created_date;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    @OrderBy
    private Set<Comments> comments;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "post")
    private List<HistoryRecords> historyRecords;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "post")
    @OrderBy
    private Set<Files> files;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private Users author;

    public Posts() {
        super();
    }

    public Posts(Integer id, String title, String description, Date created_date, Set<Comments> comments,
                 List<HistoryRecords> historyRecords, Set<Files> files, Users author) {
        super();
        this.id = id;
        this.title = title;
        this.description = description;
        this.created_date = created_date;
        this.comments = comments;
        this.historyRecords = historyRecords;
        this.files = files;
        this.author = author;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public Set<Comments> getComments() {
        return comments;
    }

    public void setComments(Set<Comments> comments) {
        this.comments = comments;
    }

    public List<HistoryRecords> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<HistoryRecords> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public Set<Files> getFiles() {
        return files;
    }

    public void setFiles(Set<Files> files) {
        this.files = files;
    }

    public Users getAuthor() {
        return author;
    }

    public void setAuthor(Users author) {
        this.author = author;
    }

}
