package jm.task.core.jdbc.dao;


import lombok.extern.slf4j.Slf4j;

import java.util.Properties;



@Slf4j
public class PropertiesUtil {
    public static final Properties INSTANCE = new Properties();
    static {
        loadProperties();
    }
     private PropertiesUtil(){
     }

     public static String getProperty(String key){
        return INSTANCE.getProperty(key);
     }

     private static void loadProperties() {
       try (var inputStream = PropertiesUtil.class.getClassLoader().getResourceAsStream("application.propierties")){
           INSTANCE.load(inputStream);
           log.info("load properties успешно загружен");
         }catch (Exception e){
           throw new RuntimeException(e);
       }
     }

}
