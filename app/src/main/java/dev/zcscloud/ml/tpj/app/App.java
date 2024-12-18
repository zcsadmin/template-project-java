package dev.zcscloud.ml.tpj.app;

import dev.zcscloud.ml.tpj.app.config.AppConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class App {

    public static void main(String[] args) {

        var appConfig = AppConfig.getInstance();
        log.info("Welcome to {} - App version: {} - Env: {}", appConfig.getAppName(), appConfig.getAppVersion(),
                appConfig.getAppEnv());

    }

}
