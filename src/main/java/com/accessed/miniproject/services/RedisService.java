package com.accessed.miniproject.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RedisService {

    RedisTemplate<String, Object> redisTemplate;
    ObjectMapper objectMapper;

    public <T> T getObject(String key, Class<T> clazz) {
        Object value = redisTemplate.opsForValue().get(key);
        log.info("Redis get: key={}, value={}", key, value);
        if (value == null) {
            log.warn("Cache miss for key={}", key);
            return null;
        }
        try {
            if (value instanceof String) {
                return objectMapper.readValue((String) value, clazz);
            }
            return objectMapper.convertValue(value, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to deserialize JSON from Redis", e);
        }
    }

    public <T> void setObject(String key, T value, Integer timeout) {
        if (value == null) {
            return;
        }
        try {
            String json = objectMapper.writeValueAsString(value);
            log.info("Redis set: key={}, json={}", key, json);
            redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(timeout));
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Failed to serialize object to JSON", e);
        }
    }

    public void deleteObject(String key) {
        log.info("Redis delete: key={}", key);
        redisTemplate.delete(key);
    }
}