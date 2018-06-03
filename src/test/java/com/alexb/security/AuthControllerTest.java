package com.alexb.security;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

//TODO: completely rewrite
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "bob")
    public void getIndexPageTest() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(model().attributeExists("username"))
                .andExpect(view().name("/index"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    public void loginFormRedirectionTest() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection());
    }
}
