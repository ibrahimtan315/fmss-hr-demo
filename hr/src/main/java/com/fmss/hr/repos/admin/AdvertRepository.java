package com.fmss.hr.repos.admin;

import com.fmss.hr.entities.Advert;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AdvertRepository extends JpaRepository<Advert, Long> {

    Optional<Advert> findByTitle(String title);
    List<Advert> findAllByStatus(boolean status, Pageable pageable);
    @Query(value = "select count(*) from adverts where status= :status", nativeQuery = true)
    int advertCountWithStatus(boolean status);
    @Query(value = "select count(*) from adverts", nativeQuery = true)
    int advertCount();
    List<Advert> findAllByTitleContainingIgnoreCase(String title, Pageable pageable);
    List<Advert> findAllByStatusAndTitleContainingIgnoreCase(boolean status, String title, Pageable pageable);
    List<Advert> findAllByStatusIsTrue();
}