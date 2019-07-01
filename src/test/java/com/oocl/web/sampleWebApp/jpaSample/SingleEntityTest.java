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
import org.springframework.util.StringUtils;

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
public class SingleEntityTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void should_be_able_to_create_entity() throws Exception {
        ResultActions perform = this.mockMvc.perform(put("/singleEntity")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\":\"my name\"}"));

        perform.andDo(print()).andExpect(status().isCreated());
    }

    @Test
    public void should_be_able_to_fetch_entity() throws Exception {
        ResultActions perform = this.mockMvc.perform(get("/singleEntity"));

        perform.andDo(print()).andExpect(status().isOk()).andExpect(content().string(is("[]")));
    }

    @Test
    public void should_throw_exception_when_the_name_is_longer_than_64() {
        char[] chars = new char[100];
        Arrays.fill(chars, 'a');
        String nameLongerThan64 = new String(chars);
        assertThrows(RuntimeException.class, () -> {
            try {
                this.mockMvc.perform(put("/singleEntity")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"" + nameLongerThan64 + "\"}"));
            } catch (Exception e) {
                throw new RuntimeException();
            }
        });
    }
}
