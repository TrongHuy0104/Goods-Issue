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
public class Storage {

    private String sID;
    private String sName;
    private int sSize;
    private String sAddress;
    private String sType;
    private String status;

    public Storage() {
    }

    public Storage(String sID, String sName, int sSize, String sAddress, String sType, String status) {
        this.sID = sID;
        this.sName = sName;
        this.sSize = sSize;
        this.sAddress = sAddress;
        this.sType = sType;
        this.status = status;
    }
    
    public Storage(String sID, String sName, int sSize, String sAddress, String sType) {
        this.sID = sID;
        this.sName = sName;
        this.sSize = sSize;
        this.sAddress = sAddress;
        this.sType = sType;
    }

    public String getsID() {
        return sID;
    }

    public String getsName() {
        return sName;
    }

    public int getsSize() {
        return sSize;
    }

    public String getsAddress() {
        return sAddress;
    }

    public String getsType() {
        return sType;
    }

    public String getStatus() {
        return status;
    }

    public void setsID(String sID) {
        this.sID = sID;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public void setsAddress(String sAddress) {
        this.sAddress = sAddress;
    }

    public void setsSize(int sSize) {
        this.sSize = sSize;
    }

    public void setsType(String sType) {
        this.sType = sType;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Storage{" + "sID=" + sID + ", sName=" + sName + ", sSize=" + sSize + ", sType=" + sType + ", status=" + status + '}';
    }

}
