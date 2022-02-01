package de.server.security;


import de.server.model.userAndSelfServiceStand.User;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

/**
 * Class  implements the UserDetailsService Interface.
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    /**
     * goodsRepository to handle user repository information.
     */
    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        // find user by the name
        User user = userRepository.findByUserName(userName);

        // when user is null then exception
        if (user == null) {throw new UsernameNotFoundException(userName);
        }
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getUserPassword(), emptyList());
    }
}
