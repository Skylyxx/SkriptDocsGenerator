package fr.skylyxx.docsgenerator.types;

import java.util.Arrays;

public class EventDocumentationElement {

    protected String id;
    protected String name;
    protected String[] description;
    protected String[] patterns;
    protected String[] examples;
    protected String[] since;
    protected String[] requiredPlugins;
    private boolean cancellable;

    public EventDocumentationElement() {
        this.id = null;
        this.name = null;
        this.description = null;
        this.examples = null;
        this.since = null;
        this.patterns = null;
        this.requiredPlugins = null;
        this.cancellable = false;
    }

    public String getId() {
        return id;
    }

    public EventDocumentationElement setId(String id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public EventDocumentationElement setName(String name) {
        this.name = name;
        return this;
    }

    public String[] getDescription() {
        return description;
    }

    public EventDocumentationElement setDescription(String[] description) {
        this.description = description;
        return this;
    }

    public String[] getExamples() {
        return examples;
    }

    public EventDocumentationElement setExamples(String[] examples) {
        this.examples = examples;
        return this;
    }

    public String[] getSince() {
        return since;
    }

    public EventDocumentationElement setSince(String[] since) {
        this.since = since;
        return this;
    }

    public String[] getPatterns() {
        return patterns;
    }

    public EventDocumentationElement setPatterns(String[] patterns) {
        this.patterns = removeParsemarks(patterns);
        return this;
    }

    public String[] getRequiredPlugins() {
        return requiredPlugins;
    }

    public EventDocumentationElement setRequiredPlugins(String[] requiredPlugins) {
        this.requiredPlugins = requiredPlugins;
        return this;
    }

    public boolean isCancellable() {
        return cancellable;
    }

    public EventDocumentationElement setCancellable(boolean cancellable) {
        this.cancellable = cancellable;
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
