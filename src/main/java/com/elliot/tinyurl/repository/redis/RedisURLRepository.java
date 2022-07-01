package com.elliot.tinyurl.repository.redis;

import com.elliot.tinyurl.entity.redis.RedisURL;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RedisURLRepository extends JpaRepository<RedisURL, String> {
}
