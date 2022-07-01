package com.elliot.tinyurl.entity.redis;

import com.elliot.tinyurl.entity.rdb.URL;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import javax.persistence.Column;
import java.io.Serializable;


@RedisHash
@Getter
public class RedisURL implements Serializable {
    @Id
    @Column(name = "long_url")
    private String longUrl;

    @Column(name = "short_url")
    private String shortUrl;

    public RedisURL(String longUrl, String shortUrl) {
        this.longUrl = longUrl;
        this.shortUrl = shortUrl;
    }

    public static RedisURL of(URL url) {
        return new RedisURL(url.getLongUrl(), url.getShortUrl());
    }
}
