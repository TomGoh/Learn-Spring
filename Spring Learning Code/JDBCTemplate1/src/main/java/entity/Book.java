package entity;

public class Book {

    private String userName;
    private String userId;
    private String ustatus;

    public Book(String userName, String userId, String ustatus) {
        this.userName = userName;
        this.userId = userId;
        this.ustatus = ustatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUstatus() {
        return ustatus;
    }

    public void setUstatus(String ustatus) {
        this.ustatus = ustatus;
    }

    @Override
    public String toString() {
        return "Book{" +
                "userName='" + userName + '\'' +
                ", userId='" + userId + '\'' +
                ", ustatus='" + ustatus + '\'' +
                '}';
    }

    public Book() {
    }
}
