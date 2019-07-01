package com.oocl.web.sampleWebApp.jpaSample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static com.oocl.web.sampleWebApp.jpaSample.AssertHelper.assertThrows;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest()
@AutoConfigureMockMvc
public class RelatedEntityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_be_able_to_create_entity() throws Exception {
        ResultActions perform = this.mockMvc.perform(put("/relatedEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"my name\"}"));

        perform.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void should_throw_exception_when_the_name_is_longer_than_64() throws Exception {
        this.mockMvc.perform(put("/singleEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"a single entity\"}"));

        assertThrows(RuntimeException.class, () -> {
            try {
                ResultActions perform = this.mockMvc.perform(put("/relatedEntity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"not a single entity\"}"));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
