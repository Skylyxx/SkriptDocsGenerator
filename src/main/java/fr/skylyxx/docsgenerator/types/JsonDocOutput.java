package fr.skylyxx.docsgenerator.types;

import java.util.List;

public class JsonDocOutput {

    private final List<DocumentationElement> effects;
    private final List<DocumentationElement> expressions;
    private final List<DocumentationElement> conditions;
    private final List<DocumentationElement> events;
    private final List<DocumentationElement> types;

    public JsonDocOutput(List<DocumentationElement> effects, List<DocumentationElement> expressions, List<DocumentationElement> conditions, List<DocumentationElement> events, List<DocumentationElement> types) {
        this.effects = effects;
        this.expressions = expressions;
        this.conditions = conditions;
        this.events = events;
        this.types = types;
    }
}
