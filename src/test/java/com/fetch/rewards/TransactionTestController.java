package com.fetch.rewards;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TransactionTestController extends FetchRewardsAssignmentApplicationTests
{
	@Autowired
	private WebApplicationContext webAppContext;
	
	private MockMvc mockMVC;
	
	@Before
	public void setup()
	{
		mockMVC = MockMvcBuilders.webAppContextSetup(webAppContext).build();
	}
	
	@Test
	public void testAddTransaction() throws Exception {
		mockMVC.perform(get("transaction/balancePoints")).andExpect(status().isOk())
		.andExpect(content().contentType("application/json;charset=UTF-8"))
		.andExpect((ResultMatcher) jsonPath("$.payer"))
		.andExpect((ResultMatcher) jsonPath("$.points"));
	}
}
