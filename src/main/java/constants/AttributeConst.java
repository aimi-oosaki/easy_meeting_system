package constants;

/**
 * 画面の項目値等を定義するEnumクラス
 *
 */
public enum AttributeConst {

    //フラッシュメッセージ
    FLUSH("flush"),

    //一覧画面共通
    MAX_ROW("maxRow"),
    PAGE("page"),

    //入力フォーム共通
    TOKEN("_token"),
    ERR("errors"),

    //ログイン中の従業員
    LOGIN_EMP("login_employee"),

    //ログイン画面
    LOGIN_ERR("loginError"),

    //従業員管理
    EMPLOYEE("employee"),
    EMPLOYEES("employees"),
    EMP_COUNT("employees_count"),
    EMP_LOGIN_TEAM("employee_team"),
    EMP_TEAM("team"),
    EMP_COM("company"),
    EMP_COM_ID("company_ID"),
    EMP_ID("id"),
    EMP_CODE("code"),
    EMP_PASS("password"),
    EMP_NAME("name"),
    EMP_ADMIN_FLG("admin_flag"),
    EMP_DELETE_FLG("delete_flag"),
    EMP_POSITION_FLG("position_flag"),

    //管理者フラグ
    ROLE_ADMIN(1),
    ROLE_GENERAL(0),

    //削除フラグ
    DEL_FLAG_TRUE(1),
    DEL_FLAG_FALSE(0),

    //会社管理
    COMPANY("company"),
    COMPANIES("companies"),
    COM_ID("company_id"),
    COM_NAME("company_name"),

    //議事録管理
    MINUTE("minute"),
    MINUTES("minutes"),
    MIN_ID("minute_id"),
    MIN_EMP("employee_id"),
    MIN_RECORDER("minute_recorder"),
    MIN_ATTENDEES("minute_attendees"),
    MIN_DECIDED("minute_decided"),
    MIN_PENDING("minute_pending"),

    //参加者
    ATENDEE("atendee"),
    ATENDEES("atendees"),
    ATE_ID("attendee_id"),

    //議題
    AGENDA("agenda"),
    AGENDAS("agendas"),
    AGE_ID("agenda_id"),
    AGE_MEE("agenda_meeting"),
    AGE_COUNT("agendas_count"),
    AGE_TITLE("agenda_title"),
    AGE_SUMMARY("agenda_summary"),
    AGENDA_LIST_SIZE("list_size"),

    //会議
    MEETING("meeting"),
    MEETINGS("meetings"),
    MET_COUNT("meetings_count"),
    MET_ID("meeting_id"),
    MET_TEAM("meeting_team"),
    MET_NAME("meeting_name"),
    MET_DATE("meeting_date"),

    //削除フラグ
    MET_DELETE_TRUE(1),
    MET_DELETE_FALSE(0),

    //チーム
    TEAM("team"),
    TEAMS("teams"),
    TEA_ID("team_id"),
    TEA_NAME("team_name"),

    //TO DO
    TODO("todo"),
    TODOS("todos"),
    TODO_COUNT("todos_count"),
    TODO_ID("todo_id"),
    TODO_EMP("todo_employee"),
    TODO_WHAT("todo_what"),
    TODO_DEADLINE("todo_deadline"),
    TODO_STATUS("todo_status"),
    TODO_CON("todo_consequence"),

    //完了フラグ
    TODO_STATUS_TRUE(1),
    TODO_STATUS_FALSE(0),

    //アイデア募集
    WANT("want"),
    WANTS("wants"),
    WAN_MEETING("meeting"),
    WAN_COUNT("want_count"),
    WAN_ID("want_id"),
    WAN_TITLE("want_title"),
    WAN_CONTENT("want_content"),
    WAN_DUEDATE("want_duedate"),

    //プロジェクト
    TASK("task"),
    TASKS("tasks"),
    TAS_ID("task_id"),
    TAS_TITLE("want_title"),
    TAS_PROGRESS("task_progress"),
    TAS_PROBLEM("task_problem"),
    TAS_SOLUTION("task_solution"),
    TAS_DUEDATE("task_duedate"),
    TAS_STATUS("task_status"),

    //完了フラグ
    TAS_PROGRESS_SAFE(1),  //安全
    TAS_PROGRESS_CAUTIOUS(2),  //安全
    TAS_PROGRESS_DANGER(3),  //安全

    //完了フラグ
    TAS_STATUS_TRUE(1),  //完了
    TAS_STATUS_FALSE(0), //未完了

    //アイデア
    IDEA("idea"),
    IDEAS("ideas"),
    IDE_COUNT("ideas_count"),
    IDE_ID("idea_id"),
    IDE_EMP("idea_employee"),
    IDE_CONTENT("idea_content");

    private final String text;
    private final Integer i;

    private AttributeConst(final String text) {
        this.text = text;
        this.i = null;
    }

    private AttributeConst(final Integer i) {
        this.text = null;
        this.i = i;
    }

    public String getValue() {
        return this.text;
    }

    public Integer getIntegerValue() {
        return this.i;
    }

}