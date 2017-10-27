package edu.uw.edm.profile.controller.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.HashMap;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.gws.model.GWSSearchType;
import edu.uw.edm.gws.model.GroupReference;
import edu.uw.edm.profile.controller.v1.model.ConfigsDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ConfigControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private GroupsWebServiceClient groupsWebServiceClient;


    @Test
    public void shouldReturnAListOfConfigTest() {

        ArrayList<GroupReference> value = new ArrayList<>();
        GroupReference testGroup = mock(GroupReference.class);
        when(testGroup.getName()).thenReturn("test-group");
        value.add(testGroup);
        when(groupsWebServiceClient.getGroupsForUser(anyString(), eq(GWSSearchType.effective))).thenReturn(value);

        HttpHeaders headers = new HttpHeaders();
        headers.set("auth-header", "toto");
        headers.set("Accept", "application/hal+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<ConfigsDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigsDTO.class, new HashMap<>());
        Link link = response.getBody().getLink("my-app");
        assertThat(link, is(not(nullValue())));
        assertThat(link.getHref(), endsWith("v1/app/test-app/my-app"));
    }

    @Test
    public void whenNoAuthenticationHeaderShouldReturn403Test() {


        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/hal+json");

        HttpEntity<String> entity = new HttpEntity<>(headers);


        ResponseEntity<ConfigsDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigsDTO.class, new HashMap<>());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));

    }

}