package com.example.demo.controller;

import com.example.demo.model.ContactsEntity;
import com.example.demo.repo.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/contactapp")
public class RestApiController {

    private final ContactsRepository contactsRepository;

    @Autowired
    public RestApiController(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @GetMapping("/searchByMail")
    Page<ContactsEntity> getContactsLike(@RequestParam(value = "keyword") String keyword, @PageableDefault(page = 0, size = 10)Pageable pageable) {
        return contactsRepository.findByContactEmailStartsWith(keyword, pageable);
    }



    @GetMapping("/searchByName")
    Page<ContactsEntity> getContactsPhoneLike(@RequestParam(value = "keyword") String keyword, Pageable pageable) {
        return contactsRepository.findByContactFirstnameLike(keyword, pageable);
    }

    @GetMapping("/get/{id}")
    ContactsEntity getContact(@PathVariable Long id) {
        return contactsRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id+""));
    }

    @PostMapping(value = "/save", produces = "application/json")
    ResponseEntity<ContactsEntity> createNewContact(@RequestBody ContactsEntity contact) {
        return new ResponseEntity<>(contactsRepository.save(contact), HttpStatus.CREATED);
    }

    @PutMapping("/save/{id}")
    ResponseEntity<ContactsEntity> updateContacts(@PathVariable(value = "id") Long id, @Valid @RequestBody ContactsEntity contact) {
        ContactsEntity existingContact = contactsRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id+""));
        existingContact.setContactPhone(contact.getContactPhone());
        existingContact.setContactLastname(contact.getContactLastname());
        existingContact.setContactFirstname(contact.getContactFirstname());
        existingContact.setContactEmail(contact.getContactEmail());

        return new ResponseEntity<>(contactsRepository.save(existingContact), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    ResponseEntity<?> deleteContact(@PathVariable Long id) {
        ContactsEntity contactsEntity = contactsRepository.findById(id).orElseThrow(() -> new ContactNotFoundException(id+""));
        contactsRepository.delete(contactsEntity);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getall")
    ResponseEntity<Iterable<ContactsEntity>> getAllContacts() {
        Iterable<ContactsEntity> contactList = contactsRepository.findAll();
        return new ResponseEntity<>(contactList, HttpStatus.OK);
    }

}
