package org.licket.demo.service;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import org.licket.demo.model.Contact;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
@Service
public class ContactsService {

    private Faker faker = new Faker();

    public List<Contact> getAllContacts() {

        return newArrayList(
                contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph()),
                contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph()),
                contact(faker.internet(), faker.name().fullName(), faker.lorem().paragraph())
        );
    }

    private Contact contact(Internet internet, String name, String description) {
        Contact contact = new Contact();
        contact.setName(name);
        contact.setDescription(description);
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        contact.addEmail(internet.emailAddress());
        return contact;
    }
}
