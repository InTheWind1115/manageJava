package cn.edu.njtech.domain.dao;

public class User {
    private Integer id;

    private String userId;

    private String password;

    private Byte limit;

    private Byte status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Byte getLimit() {
        return limit;
    }

    public void setLimit(Byte limit) {
        this.limit = limit;
    }

    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }
}