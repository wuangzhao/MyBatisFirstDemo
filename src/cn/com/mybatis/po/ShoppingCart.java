package cn.com.mybatis.po;

public class ShoppingCart {
    private int scartid;
    private String pname;
    private User user;
    private int productId;
    private String productName;

    public int getScartid() {
        return scartid;
    }

    public void setScartid(int scartid) {
        this.scartid = scartid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    private int number;
    private double price;
    private double totalAmount;

    public ShoppingCart() {
    }

    public ShoppingCart(int productId, String productName, int number, double price, double totalAmount) {
        this.productId = productId;
        this.productName = productName;
        this.number = number;
        this.price = price;
        this.totalAmount = totalAmount;
    }

    public void init(){
        this.totalAmount = this.number * this.price;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public int getUnmber() {
        return number;
    }

    public void setUnmber(int number) {
        this.number = number;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }
}
