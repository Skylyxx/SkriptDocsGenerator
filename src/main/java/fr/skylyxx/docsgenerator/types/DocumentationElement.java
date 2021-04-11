package fr.skylyxx.docsgenerator.types;

import java.util.Arrays;

public class DocumentationElement {

    protected String id;
    protected String name;
    protected String[] description;
    protected String[] examples;
    protected String[] since;
    protected String[] patterns;
    protected String[] requiredPlugins;
    protected transient ElementType elementType;

    public DocumentationElement(String id, String name, String[] description, String[] examples, String[] since, String[] patterns, String[] requiredPlugins, ElementType elementType) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.examples = examples;
        this.since = since;
        this.patterns = patterns;
        this.requiredPlugins = requiredPlugins;
        this.elementType = elementType;
    }

    public DocumentationElement() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.examples = null;
        this.since = null;
        this.patterns = null;
        this.requiredPlugins = null;
        this.elementType = null;
    }

    public String getId() {
        return id;
    }

    public DocumentationElement setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public DocumentationElement setName(String name) {
        this.name = name;
        return this;
    }

    public String[] getDescription() {
        return description;
    }

    public DocumentationElement setDescription(String[] description) {
        this.description = description;
        return this;
    }

    public String[] getExamples() {
        return examples;
    }

    public DocumentationElement setExamples(String[] examples) {
        this.examples = examples;
        return this;
    }

    public String[] getSince() {
        return since;
    }

    public DocumentationElement setSince(String[] since) {
        this.since = since;
        return this;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public DocumentationElement setPatterns(String[] patterns) {
        this.patterns = patterns;
        return this;
    }

    public String[] getRequiredPlugins() {
        return requiredPlugins;
    }

    public DocumentationElement setRequiredPlugins(String[] requiredPlugins) {
        this.requiredPlugins = requiredPlugins;
        return this;
    }

    public ElementType getElementType() {
        return elementType;
    }

    public DocumentationElement setElementType(ElementType elementType) {
        this.elementType = elementType;
        return this;
    }

    @Override
    public String toString() {
        return "DocumentationElement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description=" + Arrays.toString(description) +
                ", examples=" + Arrays.toString(examples) +
                ", since='" + since + '\'' +
                ", patterns=" + Arrays.toString(patterns) +
                ", requiredPlugins=" + Arrays.toString(requiredPlugins) +
                ", elementType=" + elementType +
                '}';
    }
}
