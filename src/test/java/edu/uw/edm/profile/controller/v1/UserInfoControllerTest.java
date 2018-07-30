package edu.uw.edm.profile.controller.v1;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.profile.controller.v1.model.UserDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

/**
 * @author Maxime Deravet Date: 7/27/18
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserInfoControllerTest extends AbstractProfileControllerTest {
    @Autowired
    private TestRestTemplate restTemplate;


    @MockBean
    private GroupsWebServiceClient groupsWebServiceClient;


    @Test
    public void shouldReturnAUSerWithGroupsTest() {

        gwsShouldReturnGroups(groupsWebServiceClient, "group1", "group2");

        HttpEntity<String> entity = getHeaders("toto");


        ResponseEntity<UserDTO> response = this.restTemplate.exchange("/v1/user", HttpMethod.GET, entity, UserDTO.class, new HashMap<>());

        UserDTO userDTO = response.getBody();
        assertThat(userDTO, is(notNullValue()));
        assertThat(userDTO.getUserName(), is(equalTo("toto")));
        assertThat(userDTO.getUserGroups(), contains("group1", "group2"));

    }

}