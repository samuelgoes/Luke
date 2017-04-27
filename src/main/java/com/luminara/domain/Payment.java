package com.luminara.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

@Document(collection = "payment")
public class Payment {

    @Id
    private ObjectId id;
    private Double quantity;
    private String currency;
    @Field("created_date")
    private Date createdDate;
    @Field("last_update")
    private Date lastUpdate;


    public Payment(){
        this.id = new ObjectId();
    }

    public Payment(Double quantity, String currency) {
        this.quantity = quantity;
        this.currency = currency;
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();

        toString.append("Id: " + id)
                .append(" Quantity: " + quantity)
                .append(" Currency: " + currency)
                .append(" Created Date: " + (createdDate != null ? createdDate.toString() : ""))
                .append(" Last Update: " + (lastUpdate != null ? lastUpdate.toString() : ""));

        return toString.toString();
    }


    public Update getUpdate(){
        Update update;

        update = new Update();

        if(quantity != null)
            update.set("quantity", quantity);
        if(currency != null)
            update.set("currency", currency);
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

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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
}
