package com.fmss.hr.repos.user;

import com.fmss.hr.dto.projection.BirthdayEventProjection;
import com.fmss.hr.dto.projection.CandidateProjection;
import com.fmss.hr.entities.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    boolean existsUserByEmail(String email);
    List<User> findAllByStatusIsTrueAndFirstNameContainingIgnoreCaseOrStatusIsTrueAndLastNameContainingIgnoreCaseOrStatusIsTrueAndTitleContainingIgnoreCase(String firstName, String lastName, String title, Pageable pageable);
    List<User> findAllByStatusIsTrueAndFirstNameContainingIgnoreCaseOrStatusIsTrueAndLastNameContainingIgnoreCaseOrStatusIsTrueAndTitleContainingIgnoreCase(String firstName, String lastName, String title);
    List<User> findAllByStatusIsFalseAndFirstNameContainingIgnoreCaseOrStatusIsFalseAndLastNameContainingIgnoreCaseOrStatusIsFalseAndTitleContainingIgnoreCase(String firstName, String lastName, String title, Pageable pageable);
    List<User> findAllByStatusIsFalseAndFirstNameContainingIgnoreCaseOrStatusIsFalseAndLastNameContainingIgnoreCaseOrStatusIsFalseAndTitleContainingIgnoreCase(String firstName, String lastName, String title);
    List<User> findAllByRoleId(int roleId, Pageable pageable);
    @Query(value = "SELECT first_name as firstName, last_name as lastName, email, phone, tag, birthday FROM candidates WHERE reference_id = :userId", nativeQuery = true)
    List<CandidateProjection> myCandidates(Long userId);

    @Query(value = "select count(*) from users where status=true", nativeQuery = true)
    int numberOfUsers();

    @Query(value = "select count(*) from users where status=false", nativeQuery = true)
    int numberOfPassiveUsers();

    @Query(value = "SELECT count(*) FROM candidates WHERE reference_id = :userId", nativeQuery = true)
    int myCandidatesCount(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET status=false WHERE id = :userId", nativeQuery = true)
    void makePassive(Long userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE users SET status=true WHERE id = :userId", nativeQuery = true)
    void makeActive(Long userId);

    @Query(value = "SELECT first_name as firstName, last_name as lastName, photo_path as photoPath, EXTRACT(YEAR FROM AGE(birthday)) AS age, CASE WHEN dob2 < current_date THEN dob2 + interval '1 year' ELSE dob2 END  AS birthday FROM users, make_date(cast(date_part('year',now()) as int), cast(date_part('month',birthday) as int), cast(date_part('day',birthday) as int)) as dob2 ORDER BY birthday LIMIT 5;", nativeQuery = true)
    List<BirthdayEventProjection> getBirthdayEvent();
    @Query(value = "select DISTINCT(id) from users where role_id = :roleId", nativeQuery = true)
    List<Long> getAllIdsByRole(Integer roleId);

    List<User> findAllByFullNameContainingIgnoreCase(String fullName);
}
