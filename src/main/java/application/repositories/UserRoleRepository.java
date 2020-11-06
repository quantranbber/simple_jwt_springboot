package application.repositories;

import application.entities.UserRole;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {

    @Transactional(readOnly = true)
    @Query("select u from  tbl_user_role u where u.userId = :userId")
    Iterable<UserRole> findRoleOfUser(@Param("userId") long userId);
}
