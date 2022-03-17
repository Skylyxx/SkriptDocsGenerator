package fr.skylyxx.docsgenerator.utils;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.classes.ClassInfo;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.*;
import ch.njol.skript.registrations.Classes;
import ch.njol.util.Pair;
import fr.skylyxx.docsgenerator.SkriptDocsGenerator;
import fr.skylyxx.docsgenerator.types.DocumentationElement;
import fr.skylyxx.docsgenerator.types.EventDocumentationElement;
import fr.skylyxx.docsgenerator.types.JsonDocOutput;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DocBuilder {

    private static final SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

    @Nullable
    public static DocumentationElement generateElementDoc(SyntaxElementInfo syntaxElementInfo) throws Exception {
        Class<?> clazz = syntaxElementInfo.c;
        if (clazz.isAnnotationPresent(NoDoc.class))
            return null;
        DocumentationElement documentationElement = new DocumentationElement();

        if (clazz.isAnnotationPresent(DocumentationId.class))
            documentationElement.setId(clazz.getAnnotation(DocumentationId.class).value());
        else
            documentationElement.setId(clazz.getSimpleName());

        if (clazz.isAnnotationPresent(Name.class))
            documentationElement.setName(clazz.getAnnotation(Name.class).value());
        else
            documentationElement.setName(clazz.getSimpleName());

        if (clazz.isAnnotationPresent(Description.class))
            documentationElement.setDescription(clazz.getAnnotation(Description.class).value());

        documentationElement.setPatterns(Arrays.copyOf(syntaxElementInfo.patterns, syntaxElementInfo.patterns.length));

        if (clazz.isAnnotationPresent(Examples.class))
            documentationElement.setExamples(clazz.getAnnotation(Examples.class).value());
        if (clazz.isAnnotationPresent(Since.class)) {
            documentationElement.setSince(new String[]{clazz.getAnnotation(Since.class).value()});
        } else {
            SkriptAddon addon = getAddon(syntaxElementInfo);
            if (addon == null) {
                throw new Exception("No addon found for syntax " + syntaxElementInfo.c.getName());
            } else {
                documentationElement.setSince(new String[]{addon.plugin.getDescription().getVersion()});
            }
        }
        if (clazz.isAnnotationPresent(RequiredPlugins.class)) {
            documentationElement.setRequiredPlugins(clazz.getAnnotation(RequiredPlugins.class).value());
        }

        return documentationElement;
    }

    public static EventDocumentationElement generateEventDoc(SkriptEventInfo<?> skriptEventInfo) throws Exception {
        SkriptAddon addon = getAddon(skriptEventInfo);
        if (addon == null)
            throw new Exception("No addon found for event " + skriptEventInfo.c.getName());
        String[] split = skriptEventInfo.originClassPath.split("\\.");
        String className = split[split.length - 1];

        boolean cancellable = true;
        for (Class<? extends Event> clazz : skriptEventInfo.events) {
            if(!Cancellable.class.isAssignableFrom(clazz)) {
                cancellable = false;
                break;
            }
        }

        EventDocumentationElement eventDocumentationElement = new EventDocumentationElement()
                .setId(skriptEventInfo.getDocumentationID() == null ? className : skriptEventInfo.getDocumentationID())
                .setName(skriptEventInfo.getName())
                .setDescription(skriptEventInfo.getDescription())
                .setPatterns(Arrays.copyOf(skriptEventInfo.patterns, skriptEventInfo.patterns.length))
                .setExamples(skriptEventInfo.getExamples())
                .setSince(new String[]{skriptEventInfo.getSince() == null ? addon.plugin.getDescription().getVersion() : skriptEventInfo.getSince()})
                .setRequiredPlugins(skriptEventInfo.getRequiredPlugins())
                .setCancellable(cancellable);
        return eventDocumentationElement;
    }

    public static DocumentationElement generateClassInfoDoc(ClassInfo<?> classInfo) throws Exception {
        SkriptAddon addon = getAddon(classInfo);
        if (addon == null)
            throw new Exception("No addon found for classinfo" + classInfo.getCodeName());
        DocumentationElement documentationElement = new DocumentationElement()
                .setId(getID(classInfo))
                .setName(classInfo.getDocName())
                .setDescription(classInfo.getDescription())
                .setPatterns(new String[]{classInfo.getCodeName()})
                .setExamples(classInfo.getExamples())
                .setSince(new String[]{classInfo.getSince() == null ? addon.plugin.getDescription().getVersion() : classInfo.getSince()});

        return documentationElement;
    }

    private static String getID(ClassInfo<?> classInfo) {
        String result = null;
        try {
            final Method method =  classInfo.getClass().getMethod("getDocumentationId");
            method.setAccessible(true);
            result = (String) method.invoke(classInfo);
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e1) {
            try {
                final Method method = classInfo.getClass().getMethod("getDocumentationID");
                method.setAccessible(true);
                result = (String) method.invoke(classInfo);
            } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e2) {
                e2.printStackTrace();
            }
        }
        return result != null ? result : classInfo.getCodeName();
    }

    public static int generateAddonDoc(Pair<String, SkriptAddon> pair) throws Exception {
        String mainClass = pair.getKey();
        SkriptAddon skriptAddon = pair.getValue();
        assert mainClass != null;
        assert skriptAddon != null;
        String[] split = mainClass.split("\\.");
        split[split.length - 1] = null;

        List<DocumentationElement> effects = new ArrayList<>();
        for (SyntaxElementInfo<? extends Effect> effect : Skript.getEffects()) {
            SkriptAddon addon = getAddon(effect);
            if (addon == null)
                continue;
            if (addon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateElementDoc(effect);
                if (documentationElement == null)
                    continue;
                effects.add(documentationElement);
            }
        }

        Iterator<ExpressionInfo<?, ?>> iterator = Skript.getExpressions();
        List<ExpressionInfo<?, ?>> skriptExpressions = new ArrayList<>();
        while (iterator.hasNext())
            skriptExpressions.add(iterator.next());

        List<DocumentationElement> expressions = new ArrayList<>();
        for (ExpressionInfo<?, ?> expression : skriptExpressions) {
            SkriptAddon addon = getAddon(expression);
            if (addon == null)
                continue;
            if (addon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateElementDoc(expression);
                if (documentationElement == null)
                    continue;
                expressions.add(documentationElement);
            }
        }

        List<DocumentationElement> sections = new ArrayList<>();
        if (skriptDocsGenerator.isUsingSkript26())
            for (SyntaxElementInfo<? extends ch.njol.skript.lang.Section> section : Skript.getSections()) {
                SkriptAddon addon = getAddon(section);
                if (addon == null)
                    continue;
                if (addon.equals(skriptAddon)) {
                    DocumentationElement documentationElement = generateElementDoc(section);
                    if (documentationElement == null)
                        continue;
                    sections.add(documentationElement);
                }
            }

        List<DocumentationElement> conditions = new ArrayList<>();
        for (SyntaxElementInfo<? extends Condition> condition : Skript.getConditions()) {
            SkriptAddon addon = getAddon(condition);
            if (addon == null)
                continue;
            if (addon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateElementDoc(condition);
                if (documentationElement == null)
                    continue;
                conditions.add(documentationElement);
            }
        }

        List<EventDocumentationElement> events = new ArrayList<>();
        for (SkriptEventInfo<?> event : Skript.getEvents()) {
            SkriptAddon addon = getAddon(event);
            if (addon == null)
                continue;
            if (addon.equals(skriptAddon)) {
                EventDocumentationElement eventDocumentationElement = generateEventDoc(event);
                events.add(eventDocumentationElement);
            }
        }

        List<DocumentationElement> types = new ArrayList<>();
        for (ClassInfo<?> type : Classes.getClassInfos()) {
            SkriptAddon addon = getAddon(type);
            if (addon == null)
                continue;
            if (addon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateClassInfoDoc(type);
                types.add(documentationElement);
            }
        }

        JsonDocOutput jsonDocOutput = new JsonDocOutput(effects, expressions, conditions, events, types, sections);
        String json = skriptDocsGenerator.getGson().toJson(jsonDocOutput);
        File file = new File(skriptDocsGenerator.getDataFolder() + File.separator + skriptAddon.getName() + ".json");
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(json);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("effects.size() = " + effects.size());
//        System.out.println("expressions.size() = " + expressions.size());
//        System.out.println("conditions.size() = " + conditions.size());
//        System.out.println("events.size() = " + events.size());
//        System.out.println("types.size() = " + types.size());
        return effects.size() + expressions.size() + conditions.size() + events.size() + types.size()
                + (skriptDocsGenerator.isUsingSkript26() ? sections.size() : 0);
    }

    @Nullable
    public static SkriptAddon getAddon(SyntaxElementInfo<?> elementInfo) {
        return getAddon(elementInfo.c);
    }

    @Nullable
    public static SkriptAddon getAddon(SkriptEventInfo<?> eventInfo) {
        return getAddon(eventInfo.originClassPath);
    }

    @Nullable
    public static SkriptAddon getAddon(ClassInfo<?> classInfo) {
        if (classInfo.getParser() != null)
            return getAddon(classInfo.getParser().getClass());
        if (classInfo.getSerializer() != null)
            return getAddon(classInfo.getSerializer().getClass());
        if (classInfo.getChanger() != null)
            return getAddon(classInfo.getChanger().getClass());
        return null;
    }

    @Nullable
    public static SkriptAddon getAddon(Class<?> clazz) {
        return getAddon(clazz.getName());
    }

    @Nullable
    public static SkriptAddon getAddon(String clazzName) {
        if (clazzName.startsWith("ch.njol.skript"))
            return Skript.getAddonInstance();
        for (SkriptAddon addon : Skript.getAddons()) {
            if (clazzName.startsWith(addon.plugin.getClass().getPackage().getName()))
                return addon;
        }
        return null;
    }

}
