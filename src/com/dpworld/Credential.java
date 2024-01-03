package com.dpworld;

public class Credential {
    private String _url;
    private String _user;
    private String _pwd;

    public Credential(String url, String user, String pwd){
        this._url = url;
        this._user = user;
        this._pwd = pwd;
    }

    public String get_url() {
        return _url;
    }

    public void set_url(String _url) {
        this._url = _url;
    }

    public String get_user() {
        return _user;
    }

    public void set_user(String _user) {
        this._user = _user;
    }

    public String get_pwd() {
        return _pwd;
    }

    public void set_pwd(String _pwd) {
        this._pwd = _pwd;
    }
}
