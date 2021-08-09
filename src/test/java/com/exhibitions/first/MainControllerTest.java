package com.exhibitions.first;

import com.exhibitions.first.controllers.PostController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;

import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("admin")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/post-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/post-list-after.sql" ,"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

public class MainControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostController controller;

    @Test
    public void  mainPageTest() throws Exception {
        this.mockMvc.perform(get("/main"))
                .andDo(print())
                .andExpect(authenticated());
    }

    @Test
    public void  postListTest() throws Exception {
        this.mockMvc.perform(get("/post"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//*[id='post-list']").nodeCount(4));
    }

    @Test
    public void filterPostTest() throws Exception {
        this.mockMvc.perform(get("/post").param("filter", "first"))
                .andDo(print())
                .andExpect(authenticated())
                .andExpect(xpath("//div[@id='post-list]/div").nodeCount(1))
                .andExpect(xpath("//div[@id='post-list]/div[@data-id=1]").exists());
    }

    @Test
    public void addPostToListTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/post")
                .file("file", "123".getBytes())
                .param("text", "first");

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(authenticated());
    }
}
