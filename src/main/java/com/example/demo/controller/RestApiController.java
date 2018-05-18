package com.example.demo.controller;

import com.example.demo.model.ContactsEntity;
import com.example.demo.repo.ContactsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collection;

@RestController
@RequestMapping("/contactapp")
public class RestApiController {

    private final ContactsRepository contactsRepository;

    @Autowired
    public RestApiController(ContactsRepository contactsRepository) {
        this.contactsRepository = contactsRepository;
    }

    @GetMapping("/get")
    Collection<ContactsEntity> getContactsLike(@RequestParam(value = "keyword", required = true) String keyword) {
        return contactsRepository.findAllByContactEmailContaining(keyword);
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
