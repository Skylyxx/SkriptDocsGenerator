package fr.skylyxx.docsgenerator;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fr.skylyxx.docsgenerator.commands.SkriptDocsGeneratorCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

public class SkriptDocsGenerator extends JavaPlugin {

    private final Collection<SyntaxElementInfo<? extends Effect>> effects = new ArrayList<>();
    private final Collection<ExpressionInfo<?, ?>> expressions = new ArrayList<>();
    private final Collection<SyntaxElementInfo<? extends Condition>> conditions = new ArrayList<>();
    private final Collection<SkriptEventInfo<?>> events = new ArrayList<>();
    private Gson gson;

    @Override
    public void onEnable() {
        new Thread(() -> {
            gson = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

            final File dir = getDataFolder();
            if (!dir.exists()) {
                if (!dir.mkdirs()) {
                    Logger.severe("Directory 'plugins/SkriptDocsGenerator' cannot be created ! Disabling...");
                    setEnabled(false);
                }
            }
            final PluginManager PM = Bukkit.getPluginManager();
            final Plugin SKRIPT = PM.getPlugin("Skript");
            if (SKRIPT == null || !SKRIPT.isEnabled()) {
                Logger.severe("Missing Skript dependency ! Disabling...");
                setEnabled(false);
            }

            while (Skript.isAcceptRegistrations()) {
                Logger.warning("Waiting to Skript finish registration");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    Thread.currentThread().interrupt();
                }
            }

            PluginCommand command = getCommand("skriptdocsgenerator");
            assert command != null;
            SkriptDocsGeneratorCommand skriptDocsGeneratorCommand = new SkriptDocsGeneratorCommand();
            command.setExecutor(skriptDocsGeneratorCommand);
            command.setTabCompleter(skriptDocsGeneratorCommand);
            command.setPermissionMessage(getColored("&aSkriptDocsGenerator &6» &cYou don't have the required permission !"));

            Logger.info("SkriptDocsGenerator successfully enabled !");
        }).start();
    }

    public String getColored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public Gson getGson() {
        return gson;
    }
}
