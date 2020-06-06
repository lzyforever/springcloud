package com.jack.scheduler;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

/**
 * 商品测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class GoodTest {
    /**
     * 模拟mvc测试对象
     */
    private MockMvc mockMvc;

    /**
     * Web项目上下文
     */
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Before
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void addGood() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/good/save")
                .param("name", "小白菜")
                .param("unit", "斤")
                .param("price", "0.15"))
                .andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().is(200))
                .andReturn();
        result.getResponse().setCharacterEncoding("UTF-8");
        System.out.println(result.getResponse().getContentAsString());
    }
}
