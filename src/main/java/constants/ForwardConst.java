package constants;

/**
 * リクエストパラメーターの変数名、変数値、jspファイルの名前等画面遷移に関わる値を定義するEnumクラス
 *
 */
public enum ForwardConst {

    //action
    ACT("action"),
    ACT_TOP("Top"),
    ACT_HOME("Home"),
    ACT_EMP("Employee"),
    ACT_COM("Company"),
    ACT_MIN("Minute"),
    ACT_ATT("Attendee"),
    ACT_AGE("Agenda"),
    ACT_MEE("Meeting"),
    ACT_TEA("Team"),
    ACT_TOD("Todo"),
    ACT_WAN("Want"),
    ACT_IDE("Idea"),
    ACT_AUTH("Auth"),

    //command
    CMD("command"),
    CMD_NONE(""),
    CMD_INDEX("index"),
    CMD_INDEX_MEE("index_meeting"),
    CMD_SHOW("show"),
    CMD_SHOW_LOGIN("showLogin"),
    CMD_LOGIN("login"),
    CMD_LOGOUT("logout"),
    CMD_NEW("entryNew"),
    CMD_CREATE("create"),
    CMD_CREATE_EMP("createEmp"),
    CMD_EDIT("edit"),
    CMD_UPDATE("update"),
    CMD_DESTROY("destroy"),
    CMD_PROB1("prob_1"),
    CMD_PROB2("prob_2"),
    CMD_PROB3("prob_3"),
    CMD_SIGNUP("signup"),

    //jsp
    FW_ERR_UNKNOWN("error/unknown"),
    FW_ERR_NOMINUTE("error/noMinute"),
    FW_TOP_INDEX("top/index"),
    FW_LOGIN("login/login"),
    FW_TOP_SHOW("home/show"), //ホーム

    FW_EMP_INDEX("employee/index"),
    FW_EMP_SHOW("employee/show"),
    FW_EMP_NEW("employee/new"),
    FW_EMP_SIGNUP("employee/signup"),
    FW_EMP_EDIT("employee/edit"),

    FW_MET_INDEX("meeting/index"),
    FW_MET_NEW("meeting/new"),
    FW_MET_EDIT("meeting/edit"),
    FW_MET_PROB1("meeting/prob_1"),
    FW_MET_PROB2("meeting/prob_2"),
    FW_MET_PROB3("meeting/prob_3"),
    FW_STICKY_NOTE("meeting/stickyNote"),

    FW_WAN_INDEX("want/index"),
    FW_WAN_NEW("want/new"),
    FW_WAN_EDIT("want/edit"),
    FW_WAN_SHOW("want/show"),

    FW_IDE_NEW("idea/new"),
    FW_IDE_INDEX("idea/index"),
    FW_IDE_EDIT("idea/edit"),

    FW_AGE_NEW("agenda/new"),
    FW_AGE_SHOW("agenda/show"),
    FW_AGE_INDEX("agenda/index"),
    FW_AGE_EDIT("agenda/edit"),

    FW_MIN_NEW("minute/new"),
    FW_MIN_SHOW("minute/show"),
    FW_MIN_EDIT("minute/edit"),

    FW_TEA_NEW("team/new"),
    FW_TEA_SHOW("team/show"),
    FW_TEA_INDEX("team/index"),
    FW_TEA_EDIT("team/edit"),

    FW_TOD_NEW("todo/new"),
    FW_TOD_SHOW("todo/show"),
    FW_TOD_INDEX("todo/index"),
    FW_TOD_EDIT("todo/edit"),

    FW_COM_NEW("company/new"),
    FW_COM_SHOW("company/show"),
    FW_COM_INDEX("company/index"),
    FW_COM_EDIT("company/edit");

    /**
     * 文字列
     */
    private final String text;

    /**
     * コンストラクタ
     */
    private ForwardConst(final String text) {
        this.text = text;
    }

    /**
     * 値(文字列)取得
     */
    public String getValue() {
        return this.text;
    }

}