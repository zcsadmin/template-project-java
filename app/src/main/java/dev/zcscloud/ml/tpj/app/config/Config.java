package dev.zcscloud.ml.tpj.app.config;

import java.io.InputStream;
import java.util.Properties;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class Config {
    
    private static final String CONFIG_BASENAME = "app";

    protected String getPropAsString(String key) throws Exception {
        return getPropertyFromConfigFile(key);
    }

    protected String getPropAsString(String env, String key) throws Exception {
        var envValue = System.getenv(env);
        if ( envValue != null && !envValue.isEmpty() ) {
            return envValue;
        }
        return getPropAsString(key);
    }

    protected Integer getPropAsInt(String key) throws Exception {
        return Integer.valueOf(getPropertyFromConfigFile(key));
    }

    protected Integer getPropAsInt(String env, String key) throws Exception {
        var envValue = System.getenv(env);
        if ( envValue != null && !envValue.isEmpty() ) {
            return Integer.valueOf(envValue);
        }
        return getPropAsInt(key);
    }

    protected String getAppEnvironment() {
        return System.getenv("APP_ENVIRONMENT");
    }

    private String getPropertyFromConfigFile(String propName) throws Exception {
        
        String appEnvironment = this.getAppEnvironment();

        if ( appEnvironment != null && !appEnvironment.isEmpty() ) {
            String envConfigFile = "/" + CONFIG_BASENAME + "." + appEnvironment.toLowerCase() + ".properties";
            try (InputStream inputStream = getClass().getResourceAsStream(envConfigFile)) {

                Properties prop = new Properties();
                prop.load(inputStream);
                if ( prop.containsKey(propName) ) {
                    return prop.getProperty(propName);
                }
            } catch (Throwable e) {
                
            }
        }

        String configFile = "/" + CONFIG_BASENAME + ".properties";
        try (InputStream inputStream = getClass().getResourceAsStream(configFile)) {

            Properties prop = new Properties();
            prop.load(inputStream);

            return prop.getProperty(propName);
        } catch (Exception e) {
            log.error("Exception in reading property '{}' from config file '{}': {}", propName, configFile, e.getMessage(), e);
            throw e;
        }

    }
}
