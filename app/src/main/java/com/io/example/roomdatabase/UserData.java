package com.io.example.roomdatabase;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.ColumnInfo;
import androidx.room.Index;
//import androidx.room.Unique;
import java.sql.Timestamp;
import java.util.Date;

//@Entity(tableName = "users", indices = {@Index(value = {"username", "email", "phone"}, unique = true)})
@Entity(tableName = "users",
        indices = {@Index(value = {"username"}, unique = true),
                @Index(value = {"email"}, unique = true),
                @Index(value = {"phone"}, unique = true)})
public class UserData {
    @PrimaryKey
    private Integer id;

    @ColumnInfo(name = "firstname")
    private String firstName;

    @ColumnInfo(name = "lastname")
    private String lastName;

    @ColumnInfo(name = "displayname")
    private String displayName;

    @ColumnInfo(name = "username")
    private String username;

    @ColumnInfo(name = "email")
    private String email;

    @ColumnInfo(name = "phone")
    private String phone;

    @ColumnInfo(name = "password")
    private String password;

    @ColumnInfo(name = "date_of_birth")
    private Date dateOfBirth;

    @ColumnInfo(name = "gender")
    private Character gender;

    @ColumnInfo(name = "image")
    private String image;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "status")
    private boolean status;

    @ColumnInfo(name = "created_at")
    private Timestamp createdAt;

    @ColumnInfo(name = "updated_at")
    private Timestamp updatedAt;

    public UserData() {
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

    public UserData(String firstName, String lastName, String displayName, String username, String email, String phone, String password, Date dateOfBirth, Character gender, String image, String description, boolean status, Timestamp createdAt, Timestamp updatedAt) {
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

}

// String timestampString = LocalDateTime.ofInstant(Instant.now(), ZoneId.of("Asia/Kolkata")).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
//            Timestamp timestamp;
//            try {
//                timestamp = new Timestamp(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timestampString).getTime());
//            } catch (ParseException e) {
//                throw new RuntimeException(e);
//            }
//
//            userDao.insertRecordUserData(new UserData("Hardik", "Vaghani", "HVaghani", "@h_vaghani", "hardik@gmail.com", "+918866335532", "Admin@123", new Date(1995, 02, 13), 'M', "image", "I am fine", true, timestamp, null));