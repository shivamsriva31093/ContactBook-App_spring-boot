package com.example.demo.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "contacts", schema = "public", catalog = "postgres")
public class ContactsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactFirstname;
    private String contactLastname;
    private String contactPhone;
    private String contactEmail;

    public ContactsEntity() {
    }

    public ContactsEntity(String contactFirstname, String contactLastname, String contactPhone, String contactEmail) {
        this.contactFirstname = contactFirstname;
        this.contactLastname = contactLastname;
        this.contactPhone = contactPhone;
        this.contactEmail = contactEmail;
    }

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "contact_firstname")
    public String getContactFirstname() {
        return contactFirstname;
    }

    public void setContactFirstname(String contactFirstname) {
        this.contactFirstname = contactFirstname;
    }

    @Basic
    @Column(name = "contact_lastname")
    public String getContactLastname() {
        return contactLastname;
    }

    public void setContactLastname(String contactLastname) {
        this.contactLastname = contactLastname;
    }

    @Basic
    @Column(name = "contact_phone")
    public String getContactPhone() {
        return contactPhone;
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

    @Basic
    @Column(name = "contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContactsEntity that = (ContactsEntity) o;
        return id.equals(that.id) &&
                Objects.equals(contactFirstname, that.contactFirstname) &&
                Objects.equals(contactLastname, that.contactLastname) &&
                Objects.equals(contactPhone, that.contactPhone) &&
                Objects.equals(contactEmail, that.contactEmail);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, contactFirstname, contactLastname, contactPhone, contactEmail);
    }
}
