package com.io.example.roomdatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Index;

import java.sql.Date;
import java.sql.Timestamp;
//import androidx.room.Unique;


@Entity(tableName = "user_table",
        indices = {
                @Index(value = {"user_user_name"}, unique = true),
                @Index(value = {"user_email"}, unique = true),
                @Index(value = {"user_phone"}, unique = true)
        })
public class User {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "user_id")
    private Integer id;

    @ColumnInfo(name = "user_first_name")
    private String firstName;

    @ColumnInfo(name = "user_last_name")
    private String lastName;

    @ColumnInfo(name = "user_display_name")
    @Nullable
    private String displayName;

    @ColumnInfo(name = "user_user_name")
    @Nullable
    private String username;

    @ColumnInfo(name = "user_email")
    @NonNull
    private String email;

    @ColumnInfo(name = "user_phone")
    @Nullable
    private String phone;

    @ColumnInfo(name = "user_password")
    @NonNull
    private String password;
    @ColumnInfo(name = "user_date_of_birth")
    @Nullable
    private Date dateOfBirth;

    @ColumnInfo(name = "user_gender")
    @NonNull
    private Character gender;

    @ColumnInfo(name = "user_image")
    @Nullable
    private String image;

    @ColumnInfo(name = "user_description")
    @Nullable
    private String description;

    @ColumnInfo(name = "user_status")
    @NonNull
    private boolean status;

    @ColumnInfo(name = "user_created_at")
    private Timestamp createdAt;

    @ColumnInfo(name = "user_updated_at")
    private Timestamp updatedAt;

    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Character getGender() {
        return gender;
    }

    public void setGender(Character gender) {
        this.gender = gender;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    public User(String firstName, String lastName, String displayName, String username, String email, String phone, String password, Date dateOfBirth, Character gender, String image, String description, boolean status, Timestamp createdAt, Timestamp updatedAt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.displayName = displayName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.password = password;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.image = image;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Override
    public String toString() {
        return "\nUser\t{" +
                "uId=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", displayName='" + displayName + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", gender=" + gender +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}