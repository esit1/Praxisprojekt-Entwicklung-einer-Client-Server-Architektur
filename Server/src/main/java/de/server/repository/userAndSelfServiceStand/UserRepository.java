package de.server.repository.userAndSelfServiceStand;

import de.server.model.userAndSelfServiceStand.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Contains all the queries in the table "tb_user".
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * Query searches for the user with the entered name.
     *
     * @param userName The user name.
     * @return Found a user.
     */
    User findByUserName(String userName);

    /**
     * Query checks whether the username already exists.
     *
     * @param userName The user name.
     * @return Found a user? True or false.
     */
    boolean existsByUserName(String userName);


    /**
     * The query returns a single user, the ID is searched for.
     *
     * @param userId The user id.
     * @return Return a user.
     */
    User findByUserId(Integer userId);


}