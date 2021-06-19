package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Workspaces {
    private String kind;
    private Long id;
    private String name;
    private Long person_id;
    private List<Long> project_ids = new ArrayList<>();

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPerson_id() {
        return person_id;
    }

    public void setPerson_id(Long person_id) {
        this.person_id = person_id;
    }

    public List<Long> getProject_ids() {
        return project_ids;
    }

    public void setProject_ids(List<Long> project_ids) {
        this.project_ids = project_ids;
    }

    public void addProject_id(Long project_id) {
        project_ids.add(project_id);
    }
}
