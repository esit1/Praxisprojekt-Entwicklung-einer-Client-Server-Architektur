package de.server.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.server.model.userAndSelfServiceStand.User;
import de.server.repository.userAndSelfServiceStand.UserRepository;
import net.minidev.json.JSONObject;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * Class extends the UsernamePasswordAuthenticationFilter
 * Processes an authentication form submission.
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    /**
     * SelfServiceStandUserNrRepository userRepository information.
     */
    private final UserRepository userRepository;

    /**
     * The live time from token.
     */
    //public static final long EXPIRATION_TIME = 315_360_000_000L; // 10 years in ms

    public static final long EXPIRATION_TIME = 315_360_000_000L; // 10 years in ms

    /**
     * the registation url.
     */
    public static final String LOGIN = "/user/newAdmin";

    /**
     * Secret to generate the Json Web Token,
     */
    public static final String SECRET = "secret-key-to-generate-jwt";

    /**
     *  typ of token.
     */
    public static final String TOKEN_TYPE = "Bearer ";

    /**
     * the name from authentication header,
     */
    public static final String AUTH_HEADER = "Authorization";

    /**
     * processes an authentication request
     */
    private final AuthenticationManager authManager;


    /**
     * Constructor
     *
     * @param authManager authManager
     * @param context     context
     */
    public JWTAuthenticationFilter(AuthenticationManager authManager, ApplicationContext context) {
        this.authManager = authManager;
        this.userRepository = context.getBean(UserRepository.class);

        // not required as the same path is registered by default
        setFilterProcessesUrl("/login/");
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain, Authentication auth) throws IOException {

        // create the json web token
        String token = JWT.create().withSubject(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername()).withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME)).sign(Algorithm.HMAC512(SECRET.getBytes()));

        // the authentication header
        res.addHeader(AUTH_HEADER, TOKEN_TYPE + token);

        // find the active user
        User user = userRepository.findByUserName(((org.springframework.security.core.userdetails.User) auth.getPrincipal()).getUsername());

        userRepository.save(user);

        JSONObject body = new JSONObject();
        body.put("token", token);
        body.put("userName", user.getUserName());

        // wrote User Information in the body
        res.getWriter().write(String.valueOf(body));
        res.getWriter().flush();
    }


    /**
     * Override The method attemptAuthentication.
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res) throws AuthenticationException {
        try {
            User credentials = new ObjectMapper().readValue(req.getInputStream(), User.class);
            return authManager.authenticate(new UsernamePasswordAuthenticationToken(credentials.getUserName(), credentials.getUserPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
