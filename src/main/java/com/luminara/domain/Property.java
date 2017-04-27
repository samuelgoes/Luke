package com.luminara.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

@Document(collection = "property")
public class Property {

    @Id
    private ObjectId id;
    @Field("property_code")
    private String propertyCode;
    private Address address;
    private Double taxation;
    @Field("created_date")
    private Date createdDate;
    @Field("last_update")
    private Date lastUpdate;
    @DBRef
    private Client client;


    public Property(){
        this.id = new ObjectId();
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();

        toString.append("Id: " + id)
                .append("Property Code: " + propertyCode)
                .append("Address: " + address)
                .append("Taxation: " + taxation)
                .append("Created Date: " + (createdDate != null ? createdDate.toString() : ""))
                .append("Last Update: " + (lastUpdate != null ? lastUpdate.toString() : ""))
                .append("Client: " + (client != null ? client.toString() : ""));

        return toString.toString();
    }


    public Update getUpdate(){
        Update update;

        update = new Update();

        if(propertyCode != null)
            update.set("property_code", propertyCode);
        if(address != null)
            update.set("address", address);
        if(taxation != null)
            update.set("taxation", taxation);
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

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Double getTaxation() {
        return taxation;
    }

    public void setTaxation(Double taxation) {
        this.taxation = taxation;
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

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }
}
