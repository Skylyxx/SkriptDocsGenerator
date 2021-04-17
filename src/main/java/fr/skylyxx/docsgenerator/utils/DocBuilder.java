package fr.skylyxx.docsgenerator.utils;

import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;
import ch.njol.skript.doc.*;
import ch.njol.skript.lang.*;
import ch.njol.util.Pair;
import fr.skylyxx.docsgenerator.SkriptDocsGenerator;
import fr.skylyxx.docsgenerator.types.DocumentationElement;
import fr.skylyxx.docsgenerator.types.JsonDocOutput;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DocBuilder {

    private static SkriptDocsGenerator skriptDocsGenerator = SkriptDocsGenerator.getPlugin(SkriptDocsGenerator.class);

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
        if (clazz.isAnnotationPresent(Examples.class))
            documentationElement.setExamples(clazz.getAnnotation(Examples.class).value());
        if (clazz.isAnnotationPresent(Since.class))
            documentationElement.setSince(new String[]{clazz.getAnnotation(Since.class).value()});
        if (clazz.isAnnotationPresent(RequiredPlugins.class))
            documentationElement.setRequiredPlugins(clazz.getAnnotation(RequiredPlugins.class).value());

        documentationElement.setPatterns(syntaxElementInfo.getPatterns());

        return documentationElement;
    }

    public static DocumentationElement generateEventDoc(SkriptEventInfo<?> skriptEventInfo) {
        DocumentationElement documentationElement = new DocumentationElement();

        documentationElement.setId(skriptEventInfo.getDocumentationID());
        documentationElement.setName(skriptEventInfo.getName());
        documentationElement.setDescription(skriptEventInfo.getDescription());
        documentationElement.setSince(new String[]{skriptEventInfo.getSince()});
        documentationElement.setRequiredPlugins(skriptEventInfo.getRequiredPlugins());

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
            Class<?> clazz = event.getElementClass();
            if (clazz.getName().startsWith(thePackage)) {
                DocumentationElement documentationElement = generateEventDoc(event);
                System.out.println(documentationElement);
                events.add(documentationElement);
            }
        }

        JsonDocOutput jsonDocOutput = new JsonDocOutput(effects, expressions, conditions, events);
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

}
