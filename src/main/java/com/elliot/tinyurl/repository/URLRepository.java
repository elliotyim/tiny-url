package com.elliot.tinyurl.repository;

import com.elliot.tinyurl.model.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, String> {
    Optional<URL> findByLongUrl(String longUrl) throws Exception;
}
