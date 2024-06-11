package com.example.do_an_cs3.Model;

import java.io.Serializable;

public class Invite implements Serializable {
    private String userNameSend;
    private  String avatarSend;
    private String companyId;
    private String context;
    private String emailSend;
    private String emailReceive;

    public Invite() {
    }


    public Invite(String userNameSend, String avatarSend, String companyId, String context, String emailSend, String emailReceive) {
        this.userNameSend = userNameSend;
        this.avatarSend = avatarSend;
        this.companyId = companyId;
        this.context = context;
        this.emailSend = emailSend;
        this.emailReceive = emailReceive;
    }

    public String getAvatarSend() {
        return avatarSend;
    }

    public void setAvatarSend(String avatarSend) {
        this.avatarSend = avatarSend;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getEmailReceive() {
        return emailReceive;
    }

    public void setEmailReceive(String emailReceive) {
        this.emailReceive = emailReceive;
    }



    public String getEmailSend() {
        return emailSend;
    }

    public void setEmailSend(String emailSend) {
        this.emailSend = emailSend;
    }

    public String getUserNameSend() {
        return userNameSend;
    }

    public void setUserNameSend(String userNameSend) {
        this.userNameSend = userNameSend;
    }
}
