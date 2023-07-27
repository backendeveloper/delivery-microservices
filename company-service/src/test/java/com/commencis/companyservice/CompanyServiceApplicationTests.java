//package com.commencis.companyservice;
//
//import com.commencis.companyservice.dto.CompanyRequest;
//import com.commencis.companyservice.repository.CompanyRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import org.testcontainers.containers.MongoDBContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//import org.testcontainers.utility.DockerImageName;
//
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@SpringBootTest
//@Testcontainers
//@AutoConfigureMockMvc
//class CompanyServiceApplicationTests {
//
//    @Container
//    static MongoDBContainer mongoDBContainer = new MongoDBContainer(DockerImageName.parse("mongo:4.0.10"));
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    @Autowired
//    private CompanyRepository companyRepository;
//
//    @DynamicPropertySource
//    static void setProperties(DynamicPropertyRegistry dymDynamicPropertyRegistry) {
//        dymDynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
//    }
//
//    @Test
//    void shouldCreateCompany() throws Exception {
//        CompanyRequest companyRequest = getCompanyRequest();
//        String companyRequestString = objectMapper.writeValueAsString(companyRequest);
//        mockMvc.perform(MockMvcRequestBuilders.post("/api/company")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(companyRequestString))
//                .andExpect(status().isCreated());
//        Assertions.assertEquals(1, companyRepository.findAll().size());
//    }
//
//    private CompanyRequest getCompanyRequest() {
//        return CompanyRequest.builder()
//                .name("Commencis co")
//                .description("Commencis smart solution")
//                .address("Istanbul")
//                .build();
//    }
//}
