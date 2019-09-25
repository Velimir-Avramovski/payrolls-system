package org.welle.constants;

import java.io.InputStream;
import java.util.Map;

import org.jboss.logging.Logger;
import org.yaml.snakeyaml.Yaml;

public class Configuration {

    final static Logger logger = Logger.getLogger(Configuration.class);

    private Yaml yaml;

    public Map<String, Object> properties;

    public Configuration() {
        yaml = new Yaml();
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("project-defaults.yml");
        properties = (Map<String, Object>) yaml.load(inputStream);
        logger.info("configuration: " + properties);
    }

}