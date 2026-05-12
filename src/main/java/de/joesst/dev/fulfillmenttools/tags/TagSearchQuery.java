package de.joesst.dev.fulfillmenttools.tags;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class TagSearchQuery {

    private final Map<String, Object> id;
    private final Map<String, Object> allowedValues;
    private final List<TagSearchQuery> and;
    private final List<TagSearchQuery> or;

    private TagSearchQuery(Builder b) {
        this.id = b.id;
        this.allowedValues = b.allowedValues;
        this.and = b.and;
        this.or = b.or;
    }

    public Map<String, Object> id() { return id; }
    public Map<String, Object> allowedValues() { return allowedValues; }
    public List<TagSearchQuery> and() { return and; }
    public List<TagSearchQuery> or() { return or; }

    public static Builder builder() { return new Builder(); }

    public static final class Builder {
        private Map<String, Object> id;
        private Map<String, Object> allowedValues;
        private List<TagSearchQuery> and;
        private List<TagSearchQuery> or;

        public Builder idEq(String value) { this.id = Map.of("eq", value); return this; }
        public Builder idNotEq(String value) { this.id = Map.of("notEq", value); return this; }
        public Builder idIn(String... values) { this.id = Map.of("in", Arrays.asList(values)); return this; }
        public Builder idLike(String pattern) { this.id = Map.of("like", pattern); return this; }

        public Builder allowedValuesEq(String value) { this.allowedValues = Map.of("eq", value); return this; }
        public Builder allowedValuesNotEq(String value) { this.allowedValues = Map.of("notEq", value); return this; }
        public Builder allowedValuesIn(String... values) { this.allowedValues = Map.of("in", Arrays.asList(values)); return this; }
        public Builder allowedValuesLike(String pattern) { this.allowedValues = Map.of("like", pattern); return this; }

        public Builder and(TagSearchQuery... queries) { this.and = Arrays.asList(queries); return this; }
        public Builder or(TagSearchQuery... queries) { this.or = Arrays.asList(queries); return this; }

        public TagSearchQuery build() { return new TagSearchQuery(this); }
    }
}
