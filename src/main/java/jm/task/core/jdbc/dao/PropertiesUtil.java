package jm.task.core.jdbc.dao;



import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public final class PropertiesUtil {

    private static final Properties PROPERTIES = new Properties();

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try {
            InputStream inputStream = PropertiesUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties");
            PROPERTIES.load(inputStream);
            log.info("Properties успешно загружены");
        } catch (CustomException | IOException e) {
            throw new CustomException("Не удалось загрузить properties");
        }
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}
