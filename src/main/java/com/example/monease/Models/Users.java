package com.example.monease.Models;

public class Users {

    String Username, Phonenum , ProfilePic;
    String[] Bankname;

    public Users() {
    }

    public Users(String username, String phonenum) {
        Username = username;
        Phonenum = phonenum;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPhonenum() {
        return Phonenum;
    }

    public void setPhonenum(String phonenum) {
        Phonenum = phonenum;
    }

    public String getProfilePic() {
        return ProfilePic;
    }

    public void setProfilePic(String profilePic) {
        ProfilePic = profilePic;
    }

    public String[] getBankname() {
        return Bankname;
    }

    public void setBankname(String[] bankname) {
        Bankname = bankname;
    }
}
