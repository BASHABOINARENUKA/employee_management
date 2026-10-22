package com.example.employee_management.service;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class RateLimitService {
    private final ConcurrentMap<String, Bucket> buckets = new ConcurrentHashMap<>();
    private Bucket createBucket() {
        Bandwidth limit = Bandwidth.classic(5, Refill.intervally(100, Duration.ofMinutes(1)));
        return Bucket.builder().addLimit(limit).build();
    }

    public boolean allowRequest(String userId) {
        Bucket bucket = buckets.computeIfAbsent(userId, key -> createBucket());
        return bucket.tryConsume(1);
    }
}