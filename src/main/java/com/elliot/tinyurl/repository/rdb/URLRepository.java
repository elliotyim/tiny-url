package com.elliot.tinyurl.repository.rdb;

import com.elliot.tinyurl.entity.rdb.URL;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface URLRepository extends JpaRepository<URL, String> {
    Optional<URL> findByLongUrl(String longUrl) throws Exception;
}
