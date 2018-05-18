package com.example.demo;

import com.example.demo.model.ContactsEntity;
import com.example.demo.repo.ContactsRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;

/**
 * @author Josh Long
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
@WebAppConfiguration
public class ContactAppRestControllerTest {


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private MockMvc mockMvc;

    private String id = "0";

    private HttpMessageConverter mappingJackson2HttpMessageConverter;

    private ContactsEntity account;

    private List<ContactsEntity> contactsEntities = new ArrayList<>();

    @Autowired
    private ContactsRepository contactsRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
            .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
            .findAny()
            .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }

    @Before
    public void setup() throws Exception {
        this.mockMvc = webAppContextSetup(webApplicationContext).build();

        this.contactsRepository.deleteAll();


        this.contactsEntities.add(contactsRepository.save(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio0")));
        this.contactsEntities.add(contactsRepository.save(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio1")));
        this.contactsEntities.add(contactsRepository.save(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio2")));
        this.contactsEntities.add(contactsRepository.save(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio3")));
        this.contactsEntities.add(contactsRepository.save(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio4")));
    }

    @Test
    public void userNotFound() throws Exception {
        mockMvc.perform(post("/contactapp/save")
                .content(this.json(new ContactsEntity("", "", "", "")))
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void readSingleContact() throws Exception {
        mockMvc.perform(get("/contactapp/get/"
                + this.contactsEntities.get(0).getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(jsonPath("$.id", is(this.contactsEntities.get(0).getId().intValue())))
                .andExpect(jsonPath("$.contactFirstname", is("a")))
                .andExpect(jsonPath("$.contactLastname", is("b")))
                .andExpect(jsonPath("$.contactPhone", is("77619522")));
    }


    @Test
    public void createContact() throws Exception {
        String bookmarkJson = json(new ContactsEntity("a","b","77619522", "as.ccu@gd.cio6"));

        this.mockMvc.perform(post("/contactapp/save")
                .contentType(contentType)
                .content(bookmarkJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void getContactByEmail() throws Exception {
        this.mockMvc.perform(get("/contactapp/searchByMail?keyword=as")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", is(5)));
    }

    @Test
    public void deleteContact() throws Exception {
        this.mockMvc.perform(delete("/contactapp/delete/" + this.contactsEntities.get(0).getId()))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteContactNegativeTest() throws  Exception {
        this.mockMvc.perform(delete("/contactapp/delete/2"))
                .andExpect(status().isNotFound());
//                .andExpect(jsonPath("$.message", is("could not find user \'"+2+"\'.")));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}
