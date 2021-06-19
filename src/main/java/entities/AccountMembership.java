package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountMembership {
    private String kind;
    private Long id;
    private Person person;
    private Long account_id;
    private String created_at;
    private String updated_at;
    private Boolean owner;
    private Boolean admin;
    private Boolean project_creator;

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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Long getAccount_id() {
        return account_id;
    }

    public void setAccount_id(Long account_id) {
        this.account_id = account_id;
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

    public Boolean getOwner() {
        return owner;
    }

    public void setOwner(Boolean owner) {
        this.owner = owner;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Boolean getProject_creator() {
        return project_creator;
    }

    public void setProject_creator(Boolean project_creator) {
        this.project_creator = project_creator;
    }
}
