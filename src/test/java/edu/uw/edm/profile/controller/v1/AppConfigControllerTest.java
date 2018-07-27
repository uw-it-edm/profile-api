package edu.uw.edm.profile.controller.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.profile.controller.v1.model.ConfigDTO;
import edu.uw.edm.profile.controller.v1.model.ConfigsDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppConfigControllerTest extends AbstractProfileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private GroupsWebServiceClient groupsWebServiceClient;


    @Test
    public void shouldReturnAListOfConfigTest() {

        gwsShouldReturnGroups(groupsWebServiceClient, "test-group", "test-group2");

        HttpEntity<String> entity = getHeaders("toto");


        ResponseEntity<ConfigsDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigsDTO.class, new HashMap<>());
        Link link = response.getBody().getLink("my-app");
        assertThat(link, is(not(nullValue())));
        assertThat(link.getHref(), endsWith("v1/app/test-app/my-app"));
    }

    @Test
    public void whenNoAuthenticationHeaderShouldReturn403Test() {
        HttpEntity<String> entity = getHeaders(null);


        ResponseEntity<ConfigsDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigsDTO.class, new HashMap<>());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

    }


    @Test
    public void shouldReturnAConfigForMyAppTest() {

        gwsShouldReturnGroups(groupsWebServiceClient, "test-group");

        HttpEntity<String> entity = getHeaders("toto");


        ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app/my-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());
        ConfigDTO configDTO = response.getBody();
        Link link = configDTO.getLink("self");
        assertThat(configDTO.getProperties().get("foo"), is(equalTo("bar")));
        assertThat(link.getHref(), endsWith("v1/app/test-app/my-app"));
    }

    @Test
    public void whenUserDoesNotHaveAccessToConfigThenForbiddenTest() {
        gwsShouldReturnGroups(groupsWebServiceClient, "bad-group");

        HttpEntity<String> entity = getHeaders("toto");

        ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app/my-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

    }


}