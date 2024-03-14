/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package goods_issue.model;

/**
 *
 * @author Trong Huy
 */
public class Model {
    private String year;
    private String month;
    private String total_products;

    public Model(String year, String month, String total_products) {
        this.year = year;
        this.month = month;
        this.total_products = total_products;
    }
    public Model() {
        this.year = null;
        this.month = null;
        this.total_products = null;
    }
    
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getTotal_products() {
        return total_products;
    }

    public void setTotal_products(String total_products) {
        this.total_products = total_products;
    }
    
    @Override
    public String toString() {
        return "Model{" + "year=" + year + ", month=" + month + ", total_products=" + total_products + '}';
    }
    
}
