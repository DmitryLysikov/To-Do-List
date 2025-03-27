package ru.dima.NaumenJava.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    private final String appName;
    private final String appVersion;

    public AppConfig(@Value("${app.name}") String appName,
                     @Value("${app.version}") String appVersion)
    {
        this.appName = appName;
        this.appVersion = appVersion;
    }

    @PostConstruct
    public void printAppInfo() {
        System.out.println("Приложение: " + appName + ", версия: " + appVersion);
    }
}
