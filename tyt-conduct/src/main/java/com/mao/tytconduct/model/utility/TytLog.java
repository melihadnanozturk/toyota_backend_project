package com.mao.tytconduct.model.utility;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TytLog {

    private static final Logger logger = LogManager.getLogger(TytLog.class);

    public void doSomething() {
        logger.debug("Debug log");
        logger.info("Info log");
        logger.warn("Warning log");
        logger.error("Error log");
    }
}
