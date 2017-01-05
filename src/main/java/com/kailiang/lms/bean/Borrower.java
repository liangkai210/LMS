package com.kailiang.lms.bean;

public class Borrower {

    private int cardNo;
    private String name;
    private String address;
    private String phone;
    private String username;
    private String password;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Borrower borrower = (Borrower) o;

        if (cardNo != borrower.cardNo) return false;
        if (name != null ? !name.equals(borrower.name) : borrower.name != null) return false;
        if (address != null ? !address.equals(borrower.address) : borrower.address != null) return false;
        if (phone != null ? !phone.equals(borrower.phone) : borrower.phone != null) return false;
        if (username != null ? !username.equals(borrower.username) : borrower.username != null) return false;
        return password != null ? password.equals(borrower.password) : borrower.password == null;
    }

    @Override
    public int hashCode() {
        int result = cardNo;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (phone != null ? phone.hashCode() : 0);
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCardNo() {
        return cardNo;
    }

    public void setCardNo(int cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

}
