package entities;

import java.util.Date;

public class PivotalProject {
    public int project_id;
    public String name;
    public String status;
    public int iteration_length;
    public Enum<WeekDays> week_start_day;
    public String point_scale;
    public Boolean bugs_and_chores_are_estimatable;
    public Boolean automatic_planning;
    public Boolean enable_tasks;
    public Date start_date;
    public TimeZone time_zone;
    public int velocity_averaged_over;
    public int number_of_done_iterations_to_show;
    public String description;
    public String profile_content;
    public Boolean enable_incoming_emails;
    public int initial_velocity;
    public Enum<ProjectType> project_type;
    public Boolean public_project;
    public int account_id;
    public Enum<JoinAs> join_as;

    public int getProject_id() {
        return project_id;
    }

    public void setProject_id(int project_id) {
        this.project_id = project_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getIteration_length() {
        return iteration_length;
    }

    public void setIteration_length(int iteration_length) {
        this.iteration_length = iteration_length;
    }

    public Enum<WeekDays> getWeek_start_day() {
        return week_start_day;
    }

    public void setWeek_start_day(Enum<WeekDays> week_start_day) {
        this.week_start_day = week_start_day;
    }

    public String getPoint_scale() {
        return point_scale;
    }

    public void setPoint_scale(String point_scale) {
        this.point_scale = point_scale;
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

    public Date getStart_date() {
        return start_date;
    }

    public void setStart_date(Date start_date) {
        this.start_date = start_date;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProfile_content() {
        return profile_content;
    }

    public void setProfile_content(String profile_content) {
        this.profile_content = profile_content;
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

    public Enum<ProjectType> getProject_type() {
        return project_type;
    }

    public void setProject_type(Enum<ProjectType> project_type) {
        this.project_type = project_type;
    }

    public Boolean getPublic_project() {
        return public_project;
    }

    public void setPublic_project(Boolean public_project) {
        this.public_project = public_project;
    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int account_id) {
        this.account_id = account_id;
    }

    public Enum<JoinAs> getJoin_as() {
        return join_as;
    }

    public void setJoin_as(Enum<JoinAs> join_as) {
        this.join_as = join_as;
    }
}
