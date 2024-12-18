package dev.zcscloud.ml.tpj.app.config;

import lombok.Getter;

@Getter
public class AppConfig extends Config {

    private final String appName;
    private final String appVersion;
    private final String appEnv;
    private final Integer appPort;

    public AppConfig() {

        try {
            this.appName = getPropAsString("APP_NAME", "app.name");
            this.appVersion = getPropAsString("APP_VERSION", "app.version");
            this.appPort = getPropAsInt("APP_PORT", "app.port");
            this.appEnv = super.getAppEnvironment();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Service helper for Singleton pattern
    public static class ServiceHelper {
        private static final AppConfig instance = new AppConfig();
    }

    /**
     * Get Singleton instance of the class.
     *
     * @return Singleton instance of the class
     */
    public static AppConfig getInstance() {
        return ServiceHelper.instance;
    }
}
