package com.productService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.productService.dto.ProductRequest;
import com.productService.repository.ProductRepository;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.platform.engine.TestExecutionResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ProductServcieApplicationTests {

	// // Selecting the image version , and setting a time out when facing network issues .and creating a container

//	@Container
//	static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:4.4.3")
//			.withStartupTimeout(Duration.ofMinutes(5));

	// // we need to start our container befoer using it .
//	static {
//		mongoDBContainer.start();
//	}

	//used for Object testing
	@Autowired
	private MockMvc mockMvc;

	//used to conver POJO to JSON and JSON to POJO
	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ProductRepository productRepository;

	//Here we are pulling the docker image of the database, to run dynamically during runtime . this container uses the image to run tests on it

//	@DynamicPropertySource
//	static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
//		dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//	}
// //here making sure the out container has started and validating it  .
//	@Test
//	void testContainerStarts() {
//		mongoDBContainer.start();
//		Assertions.assertTrue(mongoDBContainer.isRunning(), "MongoDB container should be running");
//		System.out.println("MongoDB URI: " + mongoDBContainer.getReplicaSetUrl());
//	}
//	@Test
//	void containerIsRunning() {
//		Assertions.assertTrue(mongoDBContainer.isRunning());
//	}
	// this test will create a product and validate the content type from the end point. we use mockMVC to check the
	// created product is valid . but we specified the content type is JSON. so we used objectMapper to convert POJO ->JSON and viseversa in other cases

	@Test
	void ShouldCreateAProduct() throws Exception {
		ProductRequest productRequest = getProductRequest();
		String productRequestString = objectMapper.writeValueAsString(productRequest);
		mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
						.contentType(MediaType.APPLICATION_JSON)
						.content(productRequestString))
				.andExpect(status().isCreated());

		//Here we are cheking the data is inserted in the mongodb of not
		//		int size = productRepository.findAll().size();
		//		assertNotEquals(0, size);
	}

	//this method used to create the POJO poroduct
	private ProductRequest getProductRequest() {
		return ProductRequest.builder()
				.name("Samsung")
				.price(10000)
				.build();
	}

	//Here we are cheking the data is inserted in the mongodb of not
	@Test
	void checkingDatabase() {
		int size = productRepository.findAll().size();
		assertNotEquals(0, size);
	}


//	@Test
//	void stopContainer(){
//		mongoDBContainer.stop();
//	}


//	@Test
//    void stopContainerShouldbeStopped(){
//		boolean isrunnig=mongoDBContainer.isRunning();
//        assertTrue(isrunnig, "Container is still running");
//	}

//	@Test
//	public void testCreateProductEndpoint() throws Exception {
//		// Create a ProductRequest object
//		ProductRequest productRequest = ProductRequest.builder()
//				.name("IPhone")
//				.price(6000.98)
//				.build();
//
//		// Convert the ProductRequest object to JSON string
//		String productRequestJson = objectMapper.writeValueAsString(productRequest);
//
//		// Perform a POST request to the /api/product endpoint
//		mockMvc.perform(post("/api/product")
//						.contentType(MediaType.APPLICATION_JSON)
//						.content(productRequestJson))
//				.andExpect(status().isCreated()); // Expecting HTTP 201 Created
//				//.andExpect(content().contentType(MediaType.APPLICATION_JSON)); // Optional: check content type
//		//Assertions.assertEquals(1<productRepository.findAll().size(),productRepository.findAll().size());
//	}

}
