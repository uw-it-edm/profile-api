package edu.uw.edm.profile.controller.v1;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.util.StringUtils;

import java.util.ArrayList;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.gws.model.GWSSearchType;
import edu.uw.edm.gws.model.GroupReference;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Maxime Deravet Date: 7/27/18
 */
public abstract class AbstractProfileControllerTest {
    protected void gwsShouldReturnGroups(GroupsWebServiceClient groupsWebServiceClient, String... groups) {

        ArrayList<GroupReference> values = new ArrayList<>();
        for (String group : groups) {
            GroupReference testGroup = mock(GroupReference.class);
            when(testGroup.getName()).thenReturn(group);
            values.add(testGroup);
        }
        when(groupsWebServiceClient.getGroupsForUser(anyString(), eq(GWSSearchType.effective))).thenReturn(values);
    }

    protected HttpEntity<String> getHeaders(String user) {
        HttpHeaders headers = new HttpHeaders();
        if (!StringUtils.isEmpty(user)) {
            headers.set("auth-header", user);
        }
        headers.set("Accept", "application/hal+json");

        return new HttpEntity<>(headers);
    }

}
