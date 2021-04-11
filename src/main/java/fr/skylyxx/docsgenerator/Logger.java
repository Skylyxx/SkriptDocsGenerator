package fr.skylyxx.docsgenerator;

import java.util.logging.Level;

public class Logger {

    private static SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

    public static void log(Level level, String msg, String... args) {
        for (int i = 0; i < args.length; i++) {
            msg = msg.replace("{" + i + "}", args[i]);
        }
        skriptDocsGenerator.getLogger().log(level, msg);
    }

    public static void info(String msg, String... args) {
        log(Level.INFO, msg, args);
    }

    public static void warning(String msg, String... args) {
        log(Level.WARNING, msg, args);
    }

    public static void severe(String msg, String... args) {
        log(Level.SEVERE, msg, args);
    }

}
