package co.spribe.qaa.core.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.InputStream;
import java.util.Properties;

public final class Config {
    private static final Logger log = LoggerFactory.getLogger(Config.class);
    private static final Properties properties = new Properties();
    private static final String CONFIG_NAME = "application.properties";

    static {
        loadConfigFile(CONFIG_NAME);
        System.getProperties().forEach((k,v)-> properties.setProperty(k.toString(), v.toString()));
    }

    private static void loadConfigFile(String name){
        try (InputStream is = Config.class.getClassLoader().getResourceAsStream(name)) {
            if (is != null) {
                properties.load(is);
            }
            else {
                log.warn("Config file not found: {}", name);
            }
        } catch (Exception ex) {
            log.warn("Failed to load {}: {}", name, ex.toString());
        }
    }

    private Config(){}

    public static String baseUrl(){
        return properties.getProperty("baseUrl","http://3.68.165.45");
    }
    public static int timeoutMs(){
        return Integer.parseInt(properties.getProperty("http.timeout.ms","20000"));
    }

    public static int threads() {
        return Integer.parseInt(properties.getProperty("threads", "3"));
    }

}