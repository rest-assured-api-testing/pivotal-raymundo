package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Activity {
    private String kind;
    private String guid;
    private int project_version;
    private String message;
    private String highlight;
    private List changes = new ArrayList();
    private List primary_resources = new ArrayList();
    private List secondary_resources = new ArrayList();
    private PivotalProject project;
    private Account performed_by;
    private String occurred_at;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public int getProject_version() {
        return project_version;
    }

    public void setProject_version(int project_version) {
        this.project_version = project_version;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getHighlight() {
        return highlight;
    }

    public void setHighlight(String highlight) {
        this.highlight = highlight;
    }

    public List getChanges() {
        return changes;
    }

    public void setChanges(List changes) {
        this.changes = changes;
    }

    public List getPrimary_resources() {
        return primary_resources;
    }

    public void setPrimary_resources(List primary_resources) {
        this.primary_resources = primary_resources;
    }

    public List getSecondary_resources() {
        return secondary_resources;
    }

    public void setSecondary_resources(List secondary_resources) {
        this.secondary_resources = secondary_resources;
    }

    public PivotalProject getProject() {
        return project;
    }

    public void setProject(PivotalProject project) {
        this.project = project;
    }

    public Account getPerformed_by() {
        return performed_by;
    }

    public void setPerformed_by(Account performed_by) {
        this.performed_by = performed_by;
    }

    public String getOccurred_at() {
        return occurred_at;
    }

    public void setOccurred_at(String occurred_at) {
        this.occurred_at = occurred_at;
    }
}
