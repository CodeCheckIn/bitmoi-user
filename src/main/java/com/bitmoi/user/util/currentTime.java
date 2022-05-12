package com.bitmoi.user.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class currentTime {
    private LocalDateTime times = LocalDateTime.now();

    public String times() {
        return times.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

    }
}
