package entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PivotalProject {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Long id;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String kind;
    public String name;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int version;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int iteration_length;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String week_start_day;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String point_scale;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean point_scale_is_custom;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean bugs_and_chores_are_estimatable;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean automatic_planning;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean enable_tasks;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public TimeZone time_zone;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int velocity_averaged_over;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int number_of_done_iterations_to_show;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean has_google_domain;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean enable_incoming_emails;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int initial_velocity;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public Boolean public_;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean atom_enabled;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String project_type;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public String start_date;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String created_at;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String updated_at;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    public int account_id;
    @JsonInclude(JsonInclude.Include.NON_DEFAULT)
    private int current_iteration_number;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Boolean enable_following;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getIteration_length() {
        return iteration_length;
    }

    public void setIteration_length(int iteration_length) {
        this.iteration_length = iteration_length;
    }

    public String getWeek_start_day() {
        return week_start_day;
    }

    public void setWeek_start_day(String week_start_day) {
        this.week_start_day = week_start_day;
    }

    public String getPoint_scale() {
        return point_scale;
    }

    public void setPoint_scale(String point_scale) {
        this.point_scale = point_scale;
    }

    public Boolean getPoint_scale_is_custom() {
        return point_scale_is_custom;
    }

    public void setPoint_scale_is_custom(Boolean point_scale_is_custom) {
        this.point_scale_is_custom = point_scale_is_custom;
    }

    public Boolean getBugs_and_chores_are_estimatable() {
        return bugs_and_chores_are_estimatable;
    }

    public void setBugs_and_chores_are_estimatable(Boolean bugs_and_chores_are_estimatable) {
        this.bugs_and_chores_are_estimatable = bugs_and_chores_are_estimatable;
    }

    public Boolean getAutomatic_planning() {
        return automatic_planning;
    }

    public void setAutomatic_planning(Boolean automatic_planning) {
        this.automatic_planning = automatic_planning;
    }

    public Boolean getEnable_tasks() {
        return enable_tasks;
    }

    public void setEnable_tasks(Boolean enable_tasks) {
        this.enable_tasks = enable_tasks;
    }

    public TimeZone getTime_zone() {
        return time_zone;
    }

    public void setTime_zone(TimeZone time_zone) {
        this.time_zone = time_zone;
    }

    public int getVelocity_averaged_over() {
        return velocity_averaged_over;
    }

    public void setVelocity_averaged_over(int velocity_averaged_over) {
        this.velocity_averaged_over = velocity_averaged_over;
    }

    public int getNumber_of_done_iterations_to_show() {
        return number_of_done_iterations_to_show;
    }

    public void setNumber_of_done_iterations_to_show(int number_of_done_iterations_to_show) {
        this.number_of_done_iterations_to_show = number_of_done_iterations_to_show;
    }

    public Boolean getHas_google_domain() {
        return has_google_domain;
    }

    public void setHas_google_domain(Boolean has_google_domain) {
        this.has_google_domain = has_google_domain;
    }

    public Boolean getEnable_incoming_emails() {
        return enable_incoming_emails;
    }

    public void setEnable_incoming_emails(Boolean enable_incoming_emails) {
        this.enable_incoming_emails = enable_incoming_emails;
    }

    public int getInitial_velocity() {
        return initial_velocity;
    }

    public void setInitial_velocity(int initial_velocity) {
        this.initial_velocity = initial_velocity;
    }

    public Boolean getPublic_() {
        return public_;
    }

    public void setPublic_(Boolean public_) {
        this.public_ = public_;
    }

    public Boolean getAtom_enabled() {
        return atom_enabled;
    }

    public void setAtom_enabled(Boolean atom_enabled) {
        this.atom_enabled = atom_enabled;
    }

    public String getProject_type() {
        return project_type;
    }

    public void setProject_type(String project_type) {
        this.project_type = project_type;
    }

    public String getStart_date() {
        return start_date;
    }

    public void setStart_date(String start_date) {
        this.start_date = start_date;
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

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public int getCurrent_iteration_number() {
        return current_iteration_number;
    }

    public void setCurrent_iteration_number(int current_iteration_number) {
        this.current_iteration_number = current_iteration_number;
    }

    public Boolean getEnable_following() {
        return enable_following;
    }

    public void setEnable_following(Boolean enable_following) {
        this.enable_following = enable_following;
    }
}
