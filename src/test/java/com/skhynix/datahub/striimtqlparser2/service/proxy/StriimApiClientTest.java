package com.skhynix.datahub.striimtqlparser2.service.proxy;

import com.skhynix.datahub.striimtqlparser2.catalog.entity.Persons;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@RunWith(MockitoJUnitRunner.class)
public class StriimApiClientTest {

    @Mock
    private StriimApiClient personApiClient;

    @Before
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(new PersonApiController());
    }

    @Test
    public void testGetPersons() {
        // MockServer를 사용하여 REST API 호출 가로채기
        RestAssuredMockMvc.given()
                .when()
                .get("/persons")
                .then()
                .statusCode(200)
                .body("[0].id", equalTo(1))
                .body("[0].name", equalTo("John Doe"))
                .body("[1].id", equalTo(2))
                .body("[1].name", equalTo("Jane Smith"));

        // PersonApiClient를 통해 REST API 호출
        List<Persons> persons = personApiClient.getPersons();

        // 예상된 결과와 비교
        assertThat(persons).hasSize(2);
        assertThat(persons.get(0).getId()).isEqualTo(1);
        assertThat(persons.get(0).getName()).isEqualTo("John Doe");
        assertThat(persons.get(1).getId()).isEqualTo(2);
        assertThat(persons.get(1).getName()).isEqualTo("Jane Smith");
    }
}