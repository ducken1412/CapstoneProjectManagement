package com.fpt.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "[Users]")
@Cacheable
@org.hibernate.annotations.Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Users implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "id", columnDefinition = "NVARCHAR(50)")
    private String id;
    @Column(name = "user_name", columnDefinition = "NVARCHAR(50) NOT NULL")
    private String username;
    @Column(name = "first_name", columnDefinition = "NVARCHAR(30) NOT NULL")
    private String firstName;
    @Column(name = "last_name", columnDefinition = "NVARCHAR(30) NOT NULL")
    private String lastName;
    @Column(name = "birth_date", columnDefinition = "DATE")
    private Date birthDate;
    @Column(name = "gender", columnDefinition = "INT NOT NULL")
    private Integer gender;
    @Column(name = "phone", columnDefinition = "VARCHAR(15) NOT NULL")
    private String phone;
    @Column(name = "image", columnDefinition = "longtext")
    private String image;
    @Column(name = "email", columnDefinition = "NVARCHAR(64) NOT NULL")
    private String email;
    @Column(name = "created_date", columnDefinition = "DATETIME NOT NULL")
    private Date createdDate;
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "locations_id", referencedColumnName = "id", columnDefinition = "INT NOT NULL")
    private Locations location;
    @Column(name = "description", columnDefinition = "NVARCHAR(256)")
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "status_id")
    private Status status;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "userRoleKey.user")
    private List<UserRoles> roleUser;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<HistoryRecords> historyRecords;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<CapstoneProjectDetails> capstoneProjectDetails;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<NotificationDetails> notificationDetails;
    @ManyToMany(mappedBy = "reportRecipients")
    private List<Reports> reportReceives;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "sender")
    private List<Comments> comments;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "evaluation_detail_id", referencedColumnName = "id", columnDefinition = "INT")
    private EvaluationDetails evaluationDetail;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Posts> posts;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Chat> chats;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<Reports> report;
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "user")
    private List<ChatDetails> chatDetail;
    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Semesters semester;
    @ManyToOne
    @JoinColumn(name = "site_id")
    private Sites site;

    public Users() {
    }

    public Users(String id, String username, String firstName, String lastName, Date birthDate, Integer gender, String phone, String image, String email, Date createdDate, Locations location, String description, Status status, List<UserRoles> roleUser, List<HistoryRecords> historyRecords, List<CapstoneProjectDetails> capstoneProjectDetails, List<NotificationDetails> notificationDetails, List<Reports> reportReceives, List<Comments> comments, EvaluationDetails evaluationDetail, List<Posts> posts, List<Chat> chats, List<Reports> report, List<ChatDetails> chatDetail) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.image = image;
        this.email = email;
        this.createdDate = createdDate;
        this.location = location;
        this.description = description;
        this.status = status;
        this.roleUser = roleUser;
        this.historyRecords = historyRecords;
        this.capstoneProjectDetails = capstoneProjectDetails;
        this.notificationDetails = notificationDetails;
        this.reportReceives = reportReceives;
        this.comments = comments;
        this.evaluationDetail = evaluationDetail;
        this.posts = posts;
        this.chats = chats;
        this.report = report;
        this.chatDetail = chatDetail;
    }

    public Users(String id, String username, String firstName, String lastName, Date birthDate, Integer gender, String phone, String image, String email, Date createdDate, Locations location, String description, Status status, List<UserRoles> roleUser, List<HistoryRecords> historyRecords, List<CapstoneProjectDetails> capstoneProjectDetails, List<NotificationDetails> notificationDetails, List<Reports> reportReceives, List<Comments> comments, EvaluationDetails evaluationDetail, List<Posts> posts, List<Chat> chats, List<Reports> report, Semesters semester, Sites site) {
        this.id = id;
        this.username = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.gender = gender;
        this.phone = phone;
        this.image = image;
        this.email = email;
        this.createdDate = createdDate;
        this.location = location;
        this.description = description;
        this.status = status;
        this.roleUser = roleUser;
        this.historyRecords = historyRecords;
        this.capstoneProjectDetails = capstoneProjectDetails;
        this.notificationDetails = notificationDetails;
        this.reportReceives = reportReceives;
        this.comments = comments;
        this.evaluationDetail = evaluationDetail;
        this.posts = posts;
        this.chats = chats;
        this.report = report;
        this.semester = semester;
        this.site = site;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Locations getLocation() {
        return location;
    }

    public void setLocation(Locations location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public List<UserRoles> getRoleUser() {
        return roleUser;
    }

    public void setRoleUser(List<UserRoles> roleUser) {
        this.roleUser = roleUser;
    }

    public List<HistoryRecords> getHistoryRecords() {
        return historyRecords;
    }

    public void setHistoryRecords(List<HistoryRecords> historyRecords) {
        this.historyRecords = historyRecords;
    }

    public List<CapstoneProjectDetails> getCapstoneProjectDetails() {
        return capstoneProjectDetails;
    }

    public void setCapstoneProjectDetails(List<CapstoneProjectDetails> capstoneProjectDetails) {
        this.capstoneProjectDetails = capstoneProjectDetails;
    }

    public List<NotificationDetails> getNotificationDetails() {
        return notificationDetails;
    }

    public void setNotificationDetails(List<NotificationDetails> notificationDetails) {
        this.notificationDetails = notificationDetails;
    }

    public List<Reports> getReportReceives() {
        return reportReceives;
    }

    public void setReportReceives(List<Reports> reportReceives) {
        this.reportReceives = reportReceives;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

    public EvaluationDetails getEvaluationDetail() {
        return evaluationDetail;
    }

    public void setEvaluationDetail(EvaluationDetails evaluationDetail) {
        this.evaluationDetail = evaluationDetail;
    }

    public List<Posts> getPosts() {
        return posts;
    }

    public void setPosts(List<Posts> posts) {
        this.posts = posts;
    }

    public List<Chat> getChats() {
        return chats;
    }

    public void setChats(List<Chat> chats) {
        this.chats = chats;
    }

    public List<Reports> getReport() {
        return report;
    }

    public void setReport(List<Reports> report) {
        this.report = report;
    }

    public Semesters getSemester() {
        return semester;
    }

    public void setSemester(Semesters semester) {
        this.semester = semester;
    }

    public Sites getSite() {
        return site;
    }

    public void setSite(Sites site) {
        this.site = site;
    }

    public List<ChatDetails> getChatDetail() {
        return chatDetail;
    }

    public void setChatDetail(List<ChatDetails> chatDetail) {
        this.chatDetail = chatDetail;
    }
}
