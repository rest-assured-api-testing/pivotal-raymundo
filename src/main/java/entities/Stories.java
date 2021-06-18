package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Stories {
    private String kind;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long id;
    private String created_at;
    private String updated_at;
    private String story_type;
    private String name;
    private String current_state;
    private String requested_by_id;
    private String url;
    private String project_id;
    private List owner_ids = new ArrayList();
    private List labels = new ArrayList();

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getStory_type() {
        return story_type;
    }

    public void setStory_type(String story_type) {
        this.story_type = story_type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrent_state() {
        return current_state;
    }

    public void setCurrent_state(String current_state) {
        this.current_state = current_state;
    }

    public String getRequested_by_id() {
        return requested_by_id;
    }

    public void setRequested_by_id(String requested_by_id) {
        this.requested_by_id = requested_by_id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getProject_id() {
        return project_id;
    }

    public void setProject_id(String project_id) {
        this.project_id = project_id;
    }

    public List getOwner_ids() {
        return owner_ids;
    }

    public void setOwner_ids(List owner_ids) {
        this.owner_ids = owner_ids;
    }

    public List getLabels() {
        return labels;
    }

    public void setLabels(List labels) {
        this.labels = labels;
    }
}
