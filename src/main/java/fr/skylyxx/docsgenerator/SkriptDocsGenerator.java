package fr.skylyxx.docsgenerator;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
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
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

public class SkriptDocsGenerator extends JavaPlugin {

    private Gson gson;

    private HashMap<String, SkriptAddon> addons = new HashMap<>();

    private Collection<SyntaxElementInfo<? extends Effect>> effects = new ArrayList<>();
    private Collection<ExpressionInfo<?, ?>> expressions = new ArrayList<>();
    private Collection<SyntaxElementInfo<? extends Condition>> conditions = new ArrayList<>();
    private Collection<SkriptEventInfo<?>> events = new ArrayList<>();

    @Override
    public void onEnable() {
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

        try {
            Field field = Skript.class.getDeclaredField("addons");
            field.setAccessible(true);
            HashMap<String, SkriptAddon> addonsMap = (HashMap<String, SkriptAddon>) field.get((Skript) SKRIPT);
            addonsMap.forEach((s, skriptAddon) -> {
                addons.put(skriptAddon.plugin.getClass().getName(), skriptAddon);
            });
        } catch (NoSuchFieldException | IllegalAccessException e) {
            Logger.warning("Failed to get Skript's addons !");
            e.printStackTrace();
        }

        effects = Skript.getEffects();
        Iterator<ExpressionInfo<?, ?>> iterator = Skript.getExpressions();
        while(iterator.hasNext())
            expressions.add(iterator.next());
        conditions = Skript.getConditions();
        events = Skript.getEvents();

        PluginCommand command = getCommand("skriptdocsgenerator");
        assert command != null;
        SkriptDocsGeneratorCommand skriptDocsGeneratorCommand = new SkriptDocsGeneratorCommand();
        command.setExecutor(skriptDocsGeneratorCommand);
        command.setTabCompleter(skriptDocsGeneratorCommand);
        command.setPermissionMessage(getColored("&aSkriptDocsGenerator &6» &cYou don't have the requried permission !"));

        Logger.info("SkriptDocsGenerator successfully enabled !");
    }

    public String getColored(String s) {
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public HashMap<String, SkriptAddon> getAddons() {
        return addons;
    }

    public Collection<SyntaxElementInfo<? extends Effect>> getEffects() {
        return effects;
    }

    public Collection<ExpressionInfo<?, ?>> getExpressions() {
        return expressions;
    }

    public Collection<SyntaxElementInfo<? extends Condition>> getConditions() {
        return conditions;
    }

    public Collection<SkriptEventInfo<?>> getEvents() {
        return events;
    }

    public Gson getGson() {
        return gson;
    }
}
