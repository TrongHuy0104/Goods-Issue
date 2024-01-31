/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package goods_issue.model;

/**
 *
 * @author HuuPhuc
 */
public class Storage {
    
    
    private String id;
    private String name;
    private int size;
    private String address;
    private String type;
    private String status;

    public Storage() {
    }

    public Storage(String id, String name, int size, String address, String type, String status) {
        this.id = id;
        this.name = name;
        this.size = size;
        this.address = address;
        this.type = type;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Storage{" + "id=" + id + ", name=" + name + ", size=" + size + ", address=" + address + ", type=" + type + ", status=" + status + '}';
    }
 
}


