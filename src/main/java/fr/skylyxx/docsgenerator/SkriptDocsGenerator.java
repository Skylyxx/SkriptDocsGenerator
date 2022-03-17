package fr.skylyxx.docsgenerator;

import ch.njol.skript.Skript;
import ch.njol.skript.lang.*;
import ch.njol.skript.util.Version;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import fr.skylyxx.docsgenerator.commands.SkriptDocsGeneratorCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;

public class SkriptDocsGenerator extends JavaPlugin {

    private final Collection<SyntaxElementInfo<? extends Effect>> effects = new ArrayList<>();
    private final Collection<ExpressionInfo<?, ?>> expressions = new ArrayList<>();
    private final Collection<SyntaxElementInfo<? extends Condition>> conditions = new ArrayList<>();
    private final Collection<SkriptEventInfo<?>> events = new ArrayList<>();
    private boolean usingSkript26;
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
            usingSkript26 = Skript.getVersion().isLargerThan(new Version("2.6"));

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

            try {
                this.checkForUpdates();
            } catch (IOException exception) {
                exception.printStackTrace();
            }

            Logger.info("SkriptDocsGenerator successfully enabled !");
        }).start();
    }

    public boolean checkForUpdates() throws IOException {
        URL url = new URL("https://api.github.com/repos/Skylyxx/SkriptDocsGenerator/releases/latest");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(url.openStream()));
        Gson gson = new Gson();
        JsonObject jsonObject = gson.fromJson(bufferedReader, JsonObject.class);
        Version reference = new Version(jsonObject.get("tag_name").getAsString().replaceAll("^v", ""));
        Version current = new Version(getDescription().getVersion());
        if (current.compareTo(reference) < 0)
            getLogger().warning("New version is available (" + jsonObject.get("tag_name").getAsString() + ")! Download it at https://github.com/Skylyxx/SkriptDocsGenerator/releases/latest !");
        else
            getLogger().info("You are running the latest version of SkriptDocsGenerator.");
        return current.compareTo(reference) < 0;
    }

    public String getColored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public boolean isUsingSkript26() {
        return usingSkript26;
    }

    public Gson getGson() {
        return gson;
    }
}
