package com.example.demo.repo;

import com.example.demo.model.ContactsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collection;

public interface ContactsRepository extends PagingAndSortingRepository<ContactsEntity, Long> {
//    @Query("SELECT c from contacts c where c.contact_email LIKE %:emailAddress%")
    Collection<ContactsEntity> findAllByContactEmailContaining(String emailAddress);
}
