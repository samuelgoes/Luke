package com.luminara.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;
import java.util.List;

@Document(collection = "client")
public class Client {

    @Id
    private ObjectId id;

    @Field("client_id")
    private String clientId;

    private String name;
    private Integer status;

    @Field("phone_number")
    private String phoneNumber;

    @Field("created_date")
    @CreatedDate
    private Date createdDate;

    @Field("last_update")
    @LastModifiedDate
    private Date lastUpdate;

    @Field("payment_list") @DBRef(lazy = false)
    private List<Payment> paymentList;


    // ********************************************** //


    public Client(){
        this.id = new ObjectId();
    }

    public Client (ObjectId id){
        this.id = id;
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();

        toString.append("Id: " + id)
                .append("Client Id: " + clientId)
                .append("Name: " + name)
                .append("Status: " + status)
                .append("Phone Number: " + phoneNumber)
                .append("Created Date: " + (createdDate != null ? createdDate.toString() : ""))
                .append("Last Update: " + (lastUpdate != null ? lastUpdate.toString() : ""));

        return toString.toString();
    }


    public Update getUpdate(){
        Update update;

        update = new Update();

        if(clientId != null)
            update.set("client_id", clientId);
        if(name != null)
            update.set("name", name);
        if(status != null)
            update.set("status", status);
        if(phoneNumber != null)
            update.set("phone_number", phoneNumber);
        if(createdDate != null)
            update.set("created_date", createdDate);
        if(lastUpdate != null)
            update.set("last_update", lastUpdate);

        return update;
    }


    //****************************
    //*     GETTERS & SETTERS    *
    //****************************


    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }
}
