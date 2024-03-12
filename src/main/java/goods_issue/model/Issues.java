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
public class Issues extends Product {

    private String iId;
    private String id_Id;
    private String uId;
    private String sId;
    private String eId;
    private int qty;
    private String date;
    private int status;

    public Issues() {
    }

    public String getiId() {
        return iId;
    }
    
    public String getid_Id() {
        return id_Id;
    }

    public String getuId() {
        return uId;
    }

    public String getsId() {
        return sId;
    }

    public String geteId() {
        return eId;
    }

    public int getQty() {
        return qty;
    }

    public String getDate() {
        return date;
    }

    public int getStatus() {
        return status;
    }

    public void setiId(String iId) {
        this.iId = iId;
    }
    
    public void setid_Id(String id_Id) {
        this.id_Id = id_Id;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public void seteId(String eId) {
        this.eId = eId;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Issues{" + "iId=" + iId + "id_Id=" + id_Id + ", uId=" + uId + ", sId=" + sId + ", eId=" + eId + ", qty=" + qty + ", date=" + date + ", status=" + status + '}';
    }

}
