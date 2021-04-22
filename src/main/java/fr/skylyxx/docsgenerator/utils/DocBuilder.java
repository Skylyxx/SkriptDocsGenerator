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
import fr.skylyxx.docsgenerator.types.JsonDocOutput;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocBuilder {

    private static final SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

    public static DocumentationElement generateElementDoc(SyntaxElementInfo syntaxElementInfo) {
        Class<?> clazz = syntaxElementInfo.getElementClass();
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

        documentationElement.setPatterns(syntaxElementInfo.getPatterns());

        if (clazz.isAnnotationPresent(Examples.class))
            documentationElement.setExamples(clazz.getAnnotation(Examples.class).value());
        if (clazz.isAnnotationPresent(Since.class))
            documentationElement.setSince(new String[]{clazz.getAnnotation(Since.class).value()});
        else
            documentationElement.setSince(new String[]{getAddon(syntaxElementInfo).plugin.getDescription().getVersion()});
        if (clazz.isAnnotationPresent(RequiredPlugins.class))
            documentationElement.setRequiredPlugins(clazz.getAnnotation(RequiredPlugins.class).value());


        return documentationElement;
    }

    public static DocumentationElement generateEventDoc(SkriptEventInfo<?> skriptEventInfo) {
        String[] split = skriptEventInfo.originClassPath.split("\\.");
        String className = split[split.length - 1];
        DocumentationElement documentationElement = new DocumentationElement()
                .setId(skriptEventInfo.getDocumentationID() == null ? className : skriptEventInfo.getDocumentationID())
                .setName(skriptEventInfo.getName())
                .setDescription(skriptEventInfo.getDescription())
                .setPatterns(skriptEventInfo.getPatterns())
                .setExamples(skriptEventInfo.getExamples())
                .setSince(new String[]{skriptEventInfo.getSince() == null ? getAddon(skriptEventInfo).plugin.getDescription().getVersion() : skriptEventInfo.getSince()})
                .setRequiredPlugins(skriptEventInfo.getRequiredPlugins());

        return documentationElement;
    }

    public static DocumentationElement generateClassInfoDoc(ClassInfo<?> classInfo) {
        DocumentationElement documentationElement = new DocumentationElement()
                .setId(classInfo.getDocumentationId() == null ? classInfo.getCodeName() : classInfo.getDocumentationId())
                .setName(classInfo.getDocName())
                .setDescription(classInfo.getDescription())
                .setPatterns(new String[]{classInfo.getCodeName()})
                .setExamples(classInfo.getExamples())
                .setSince(new String[]{classInfo.getSince() == null ? getAddon(classInfo).plugin.getDescription().getVersion() : classInfo.getSince()});

        return documentationElement;
    }

    public static void generateAddonDoc(Pair<String, SkriptAddon> pair) {
        String mainClass = pair.getKey();
        SkriptAddon skriptAddon = pair.getValue();

        String[] split = mainClass.split("\\.");
        split[split.length - 1] = null;
        String thePackage = String.join(".", split).replace(".null", "");

        List<DocumentationElement> effects = new ArrayList<>();
        for (SyntaxElementInfo<? extends Effect> effect : Skript.getEffects()) {
            Class<?> clazz = effect.getElementClass();
            if (clazz.getName().startsWith(thePackage)) {
                if (clazz.isAnnotationPresent(NoDoc.class))
                    continue;
                DocumentationElement documentationElement = generateElementDoc(effect);
                effects.add(documentationElement);
            }
        }

        Iterator<ExpressionInfo<?, ?>> iterator = Skript.getExpressions();
        List<ExpressionInfo<?, ?>> skriptExpressions = new ArrayList<>();
        while (iterator.hasNext())
            skriptExpressions.add(iterator.next());

        List<DocumentationElement> expressions = new ArrayList<>();
        for (ExpressionInfo<?, ?> expression : skriptExpressions) {
            Class<?> clazz = expression.getElementClass();
            if (clazz.getName().startsWith(thePackage)) {
                if (clazz.isAnnotationPresent(NoDoc.class))
                    continue;
                DocumentationElement documentationElement = generateElementDoc(expression);
                expressions.add(documentationElement);
            }
        }

        List<DocumentationElement> conditions = new ArrayList<>();
        for (SyntaxElementInfo<? extends Condition> condition : Skript.getConditions()) {
            Class<?> clazz = condition.getElementClass();
            if (clazz.getName().startsWith(thePackage)) {
                if (clazz.isAnnotationPresent(NoDoc.class))
                    continue;
                DocumentationElement documentationElement = generateElementDoc(condition);
                conditions.add(documentationElement);
            }
        }

        List<DocumentationElement> events = new ArrayList<>();
        for (SkriptEventInfo<?> event : Skript.getEvents()) {
            SkriptAddon eventAddon = getAddon(event);
            if (eventAddon == null)
                continue;
            if (eventAddon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateEventDoc(event);
                events.add(documentationElement);
            }
        }

        List<DocumentationElement> types = new ArrayList<>();
        for (ClassInfo<?> type : Classes.getClassInfos()) {
            SkriptAddon typeAddon = getAddon(type);
            if (typeAddon == null)
                continue;
            if (typeAddon.equals(skriptAddon)) {
                DocumentationElement documentationElement = generateClassInfoDoc(type);
                types.add(documentationElement);
            }
        }

        JsonDocOutput jsonDocOutput = new JsonDocOutput(effects, expressions, conditions, events, types);
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
    }

    @Nullable
    public static SkriptAddon getAddon(SyntaxElementInfo<?> elementInfo) {
        return getAddon(elementInfo.originClassPath);
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
            return null;
        for (SkriptAddon addon : Skript.getAddons()) {
            if (clazzName.startsWith(addon.plugin.getClass().getPackage().getName()))
                return addon;
        }
        return null;
    }

}
