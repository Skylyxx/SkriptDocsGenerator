package fr.skylyxx.docsgenerator.types;

import java.util.Arrays;

public class DocumentationElement {

    protected String id;
    protected String name;
    protected String[] description;
    protected String[] patterns;
    protected String[] examples;
    protected String[] since;
    protected String[] requiredPlugins;

    public DocumentationElement() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.examples = null;
        this.since = null;
        this.patterns = null;
        this.requiredPlugins = null;
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
        this.patterns = removeParsemarks(patterns);
        return this;
    }

    public String[] getRequiredPlugins() {
        return requiredPlugins;
    }

    public DocumentationElement setRequiredPlugins(String[] requiredPlugins) {
        this.requiredPlugins = requiredPlugins;
        return this;
    }

    private String[] removeParsemarks(String[] patterns) {
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = patterns[i].replaceAll("\\d\\¦", "");
        }
        return patterns;
    }

    @Override
    public String toString() {
        return "DocumentationElement{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", description=" + Arrays.toString(description) +
                ", examples=" + Arrays.toString(examples) +
                ", since=" + Arrays.toString(since) +
                ", patterns=" + Arrays.toString(patterns) +
                ", requiredPlugins=" + Arrays.toString(requiredPlugins) +
                '}';
    }
}
