package core.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import core.model.User;

@Service(value = CustomUserDetailsService.BEAN_NAME)
public class CustomUserDetailsService implements UserDetailsService {

	public static final String BEAN_NAME = "customUserDetailsService";
	
	@Autowired private UserService userService;
	
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		User user = userService.findByUsername(userName);
		
		if (user == null) {
			throw new UsernameNotFoundException("Username not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
			user.getActive(), true, true, true, getGrantedAuthorities(user));
	}
	
	private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        
        for(String role : user.getType().getRoles()){
        	authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
        }
        
        return authorities;
    }
	
}
