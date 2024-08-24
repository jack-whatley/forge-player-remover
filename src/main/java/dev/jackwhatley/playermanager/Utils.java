package dev.jackwhatley.playermanager;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

import java.security.SecureRandom;

public class Utils {
    private static final Logger logger = LogUtils.getLogger();
    
    public static void LogIfAllowed(String msg) {
        if (!Config.isLogEnabled) return;

        logger.warn(msg);
    }
    
    public static boolean CheckChance() {
        SecureRandom random = new SecureRandom();
        int randomNum = random.nextInt(1, 101);
        
        return randomNum <= Config.kickChance;
    }
}
