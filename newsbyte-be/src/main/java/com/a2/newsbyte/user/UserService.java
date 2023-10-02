package com.a2.newsbyte.user;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    private final Logger logger = LoggerFactory.getLogger(getClass());


    public User findByUname(String uname){
        return  userRepository.findByUname(uname);
    }


    //---new---
    @Cacheable("users")
    public UserDetails loadUserByUsername(String jti, String userName) throws UsernameNotFoundException {
        return loadUserByUsername(userName);
    }
    //---new---
    @Override
    public UserDetails loadUserByUsername(String uname) throws UsernameNotFoundException {
        logger.info("overrrrrrrrrrrrrrrrrrrrrloaded method:: "+uname);
        User user = userRepository.findByUname(uname);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid user: " + uname);
        }

        return new org.springframework.security.core.userdetails.User(user.getUname(), user.getPassword(), true,
                true, true, true, AuthorityUtils.commaSeparatedStringToAuthorityList(user.getRoles()));
    }
}
