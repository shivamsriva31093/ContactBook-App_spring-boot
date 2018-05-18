package com.example.demo.repo;

import com.example.demo.model.ContactsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.Collection;

public interface ContactsRepository extends PagingAndSortingRepository<ContactsEntity, Long> {

    Collection<ContactsEntity> findAllByContactEmailContaining(String emailAddress);

    Page<ContactsEntity> findByContactEmailLike(String email, Pageable pageable);

    Page<ContactsEntity> findByContactEmailStartsWith(String email, Pageable pageable);

    Page<ContactsEntity> findByContactPhoneLike(String phone, Pageable pageable);
}
