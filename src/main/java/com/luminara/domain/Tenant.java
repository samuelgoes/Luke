package com.luminara.domain;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.query.Update;

import java.util.Date;

@Document(collection = "tenant")
public class Tenant {

    @Id
    private ObjectId id;
    @Field("type_id")
    private String typeId;
    @Field("tenant_id")
    private String tenantId;
    private String name;
    @Field("last_name")
    private String lastName;
    @Field("phone_number")
    private String phoneNumber;
    @Field("created_date")
    private Date createdDate;
    @Field("last_update")
    private Date lastUpdate;
    @DBRef
    private Property residence;


    public Tenant(){
        this.id = new ObjectId();
    }

    @Override
    public String toString(){
        StringBuilder toString = new StringBuilder();

        toString.append("Id: " + id)
                .append("Type ID: " + typeId)
                .append("Tenant ID: " + tenantId)
                .append("Name: " + name)
                .append("Last Name: " + lastName)
                .append("Phone Number: " + phoneNumber)
                .append("Created Date: " + (createdDate != null ? createdDate.toString() : ""))
                .append("Last Update: " + (lastUpdate != null ? lastUpdate.toString() : ""))
                .append("Residence: " + residence);

        return toString.toString();
    }


    public Update getUpdate(){
        Update update;

        update = new Update();

        if(typeId != null)
            update.set("type_id", typeId);
        if(tenantId != null)
            update.set("tenant_id", tenantId);
        if(name != null)
            update.set("name", name);
        if(lastName != null)
            update.set("status", lastName);
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

    public String getTypeId() {
        return typeId;
    }

    public void setTypeId(String typeId) {
        this.typeId = typeId;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public Property getResidence() {
        return residence;
    }

    public void setResidence(Property residence) {
        this.residence = residence;
    }
}
