package edu.uw.edm.profile.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

import edu.uw.edm.gws.GroupsWebServiceClient;
import edu.uw.edm.gws.model.GWSSearchType;
import edu.uw.edm.gws.model.GroupReference;


/**
 * @author James Renfro
 */
@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final GroupsWebServiceClient groupsWebServiceClient;

    @Autowired
    public UserDetailsService(GroupsWebServiceClient groupsWebServiceClient) {
        this.groupsWebServiceClient = groupsWebServiceClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) {
            return null;
        }

        List<GroupReference> userGroups = groupsWebServiceClient.getGroupsForUser(username, GWSSearchType.effective);

        List<SimpleGrantedAuthority> grantedAuthorities = userGroups.stream()
                .map(group -> new SimpleGrantedAuthority(group.getName()))
                .collect(Collectors.toList());

        return new User(username, "", grantedAuthorities);
    }

}
