package net.simplifiedlearning.myheroapp;

public class TableGeneral {
    int id;
    String first_name;
    String last_name;
    String login_token;
    String last_time_started;
    String last_time_ended;
    long todays_worktime;
    boolean checkas;

    public TableGeneral(int id, String first_name, String last_name, String login_token, String last_time_started, String last_time_ended, long todays_worktime, boolean checkas) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.login_token = login_token;
        this.last_time_started = last_time_started;
        this.last_time_ended = last_time_ended;
        this.todays_worktime = todays_worktime;
        this.checkas = checkas;
    }

    public int getId() {
        return id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getLogin_token() {
        return login_token;
    }

    public String getLast_time_started() {
        return last_time_started;
    }

    public String getLast_time_ended() {
        return last_time_ended;
    }

    public long getTodays_worktime() {
        return todays_worktime;
    }

    public boolean isCheckas() {
        return checkas;
    }
}
