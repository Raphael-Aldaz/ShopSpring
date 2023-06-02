package fr.fms.Repository;

import fr.fms.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
    @Query("select u from User u where u.loginUser like :username and u.passwordUser like :password")
    User findByUsernameAndPassword(String username, String password);
}
