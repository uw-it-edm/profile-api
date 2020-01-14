package edu.uw.edm.profile.controller.v1;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.ForwardedHeaderFilter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.profile.controller.v1.model.ConfigDTO;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsNull.nullValue;
import static org.springframework.hateoas.MediaTypes.HAL_JSON;

/**
 * @author Maxime Deravet Date: 10/13/17
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class AppConfigControllerTest extends AbstractProfileControllerTest {
    @TestConfiguration
    static class TestRestTemplateConfiguration {

        @Bean
        public RestTemplateBuilder restTemplateBuilder() {
            final RestTemplate restTemplate = new RestTemplate();
            //replace default MappingJackson2HttpMessageConverter with one that supports HATEOAS
            final List<HttpMessageConverter<?>> existingMessageConverts = restTemplate.getMessageConverters().stream().filter( converter -> !(converter instanceof MappingJackson2HttpMessageConverter)).collect(Collectors.toList());
            existingMessageConverts.add(getHalMessageConverter());

            return new RestTemplateBuilder().additionalMessageConverters(existingMessageConverts);
        }

        private HttpMessageConverter getHalMessageConverter() {
            final ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new Jackson2HalModule());
            final MappingJackson2HttpMessageConverter halConverter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
            halConverter.setSupportedMediaTypes(Arrays.asList(HAL_JSON));
            halConverter.setObjectMapper(objectMapper);
            return halConverter;
        }
    }

    @Autowired
    private TestRestTemplate restTemplate;

    @MockBean
    private GroupsWebServiceClient groupsWebServiceClient;

    @Test
    public void shouldUseXForwardedHostHeaderTest() {
        gwsShouldReturnGroups(groupsWebServiceClient, "test-group", "test-group2");

        HttpEntity<String> entity = getHeaders("toto");

        HttpHeaders headers = new HttpHeaders();
        headers.putAll(getHeaders("toto").getHeaders());
        headers.set("X-Forwarded-Host", "proxy.host.com");
        headers.set("X-Forwarded-Port", "443");
        headers.set("X-Forwarded-Proto", "https");

        entity = new HttpEntity<>(headers);

        final ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());
        final Link link = response.getBody().getLink("my-app");
        assertThat(link, is(not(nullValue())));
        assertThat(link.getHref(), is(equalTo("https://proxy.host.com/v1/app/test-app/my-app")));

    }

    @Test
    public void shouldReturnAListOfConfigTest() {

        gwsShouldReturnGroups(groupsWebServiceClient, "test-group", "test-group2");

        final HttpEntity<String> entity = getHeaders("toto");

        final ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());

        final Link link = response.getBody().getLink("my-app");
        assertThat(link, is(not(nullValue())));
        assertThat(link.getHref(), endsWith("v1/app/test-app/my-app"));

    }

    @Test
    public void whenNoAuthenticationHeaderShouldReturn403Test() {
        final HttpEntity<String> entity = getHeaders(null);

        final ResponseEntity response = this.restTemplate.exchange("/v1/app/test-app", HttpMethod.GET, entity, String.class, new HashMap<>());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }


    @Test
    public void shouldReturnAConfigForMyAppTest() {
        gwsShouldReturnGroups(groupsWebServiceClient, "test-group");

        final HttpEntity<String> entity = getHeaders("toto");

        final ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app/my-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());
        final ConfigDTO configDTO = response.getBody();
        final Link link = configDTO.getLink("self");
        assertThat(configDTO.getProperties().get("foo"), is(equalTo("bar")));
        assertThat(link.getHref(), endsWith("v1/app/test-app/my-app"));
    }

    @Test
    public void whenUserDoesNotHaveAccessToConfigThenForbiddenTest() {
        gwsShouldReturnGroups(groupsWebServiceClient, "bad-group");

        final HttpEntity<String> entity = getHeaders("toto");

        final ResponseEntity<ConfigDTO> response = this.restTemplate.exchange("/v1/app/test-app/my-app", HttpMethod.GET, entity, ConfigDTO.class, new HashMap<>());
        assertThat(response.getStatusCode(), is(HttpStatus.FORBIDDEN));
    }
}