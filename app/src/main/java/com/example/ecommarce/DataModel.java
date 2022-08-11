package com.example.ecommarce;

public class DataModel {

    String Pimage,Name,BrandName,Capacity,Detail,Price;

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    Integer quantity,total;



    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public DataModel(String pimage, String name, String brandName, String price, Integer quantity,Integer total) {
        Pimage = pimage;
        Name = name;
        BrandName = brandName;
        Price = price;
        this.quantity = quantity;
        this.total=total;
    }



    public String getPimage() {
        return Pimage;
    }

    public void setPimage(String pimage) {
        Pimage = pimage;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getCapacity() {
        return Capacity;
    }

    public void setCapacity(String capacity) {
        Capacity = capacity;
    }



    public String getDetail() {
        return Detail;
    }

    public void setDetail(String detail) {
        Detail = detail;
    }

    public DataModel() {
    }

    public DataModel(String pimage, String name, String brandName, String capacity, String price, String detail) {
        Pimage = pimage;
        Name = name;
        BrandName = brandName;
        Capacity = capacity;
        Price = price;
        Detail = detail;
    }
}
