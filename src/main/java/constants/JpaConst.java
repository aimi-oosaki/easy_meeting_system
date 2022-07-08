package constants;

/**
 * DB関連の項目値を定義するインターフェース
 * ※インターフェイスに定義した変数は public static final 修飾子がついているとみなされる
 */
public interface JpaConst {

    //persistence-unit名
    String PERSISTENCE_UNIT_NAME = "easy_meeting_system";

    //データ取得件数の最大値
    int ROW_PER_PAGE = 15; //1ページに表示するレコードの数

    //従業員テーブル
    String TABLE_EMP = "employees"; //テーブル名
    //従業員テーブルカラム
    String EMP_COL_ID = "id"; //id
    String EMP_COL_CODE = "code"; //社員番号
    String EMP_COL_COM = "company_id"; //アイデア募集中の会議
    String EMP_COL_TEA = "team_id"; //アイデア募集中の会議
    String EMP_COL_NAME = "name"; //氏名
    String EMP_COL_PASS = "password"; //パスワード
    String EMP_COL_ADMIN_FLAG = "admin_flag"; //管理者権限
    String EMP_COL_DELETE_FLAG = "delete_flag"; //削除フラグ

    int ROLE_ADMIN = 1; //管理者権限ON(管理者)
    int ROLE_GENERAL = 0; //管理者権限OFF(一般)
    int EMP_DEL_TRUE = 1; //削除フラグON(削除済み)
    int EMP_DEL_FALSE = 0; //削除フラグOFF(現役)

    //会社テーブル
    String TABLE_COM = "companies"; //テーブル名
    //会社テーブルカラム
    String COM_COL_ID = "company_id"; //id
    String COM_COL_NAME = "name";

    //議事録テーブル
    String TABLE_MIN = "minutes"; //テーブル名
    //議事録テーブルカラム
    String MIN_COL_ID = "minute_id"; //id
    String MIN_COL_EMP = "employee_id"; //議事録を作成した従業員のid
    String MIN_COL_MET = "meeting_id"; //議事録を作成した会議のid
    String MIN_COL_ATTENDEES = "attendees"; //決定事項
    String MIN_COL_DECIDED = "decided"; //決定事項
    String MIN_COL_PENDING = "pending"; //保留事項

    //参加者テーブル
    String TABLE_ATT = "attendees"; //テーブル名
    //参加者テーブルカラム
    String ATT_COL_ID = "attendee_id"; //id
    String ATT_COL_EMP = "employee_id"; //議事録を作成した従業員のid
    String ATT_COL_MET = "meeting_id"; //議事録を作成した会議のid

    //議題テーブル
    String TABLE_AGE = "agendas"; //テーブル名
    //議題テーブルカラム
    String AGE_COL_ID = "agenda_id"; //id
    String AGE_COL_MET = "meeting_id"; //議事録を作成した会議のid
    String AGE_COL_NUM = "number"; //番号
    String AGE_COL_TITLE = "title"; //議事項目
    String AGE_COL_SUMMARY = "summary"; //結論

    //会議テーブル
    String TABLE_MEE = "meetings"; //テーブル名
    //会議テーブルカラム
    String MEE_COL_ID = "meeting_id"; //id
    String MEE_COL_TEA = "team_id"; //会議を開催したチームid
    String MEE_COL_NAME = "name"; //会議名
    String MEE_COL_DATE = "date"; //開催日
    String MEE_COL_DELETE_FLAG = "delete_flag"; //保留事項

    int MEE_DEL_TRUE = 1; //削除フラグON(削除済み)
    int MEE_DEL_FALSE = 0; //削除フラグOFF(現役)

    //チームテーブル
    String TABLE_TEA = "teams"; //テーブル名
    //チームテーブルカラム
    String TEA_COL_ID = "team_id"; //id
    String TEA_COL_COM = "company_id"; //id
    String TEA_COL_NAME = "name"; //チーム名
    String TEA_COL_DELETE_FLAG = "delete_flag"; //チーム名

    int TEA_DEL_TRUE = 1; //削除フラグON(削除済み)
    int TEA_DEL_FALSE = 0; //削除フラグOFF(現役)

    //TODOテーブル
    String TABLE_TOD = "todos"; //テーブル名
    //TODOテーブルカラム
    String TOD_COL_ID = "todo_id"; //id
    String TOD_COL_EMP = "employee_id"; //TODO担当者
    String TOD_COL_MET = "meeting_id"; //TODOを作成した会議
    String TOD_COL_TEA = "team_id"; //TODOを作成したチーム
    String TOD_COL_WHAT = "what"; //TODO内容
    String TOD_COL_DEADLINE = "deadline"; //TODO〆切
    String TOD_COL_STATUS = "status"; //TODO未・済
    String TOD_COL_CON = "consequence"; //TODO結果

    int TOD_STA_TRUE = 1; //TODO済
    int TOD_STA_FALSE = 0; //TODO未

    //募集テーブル
    String TABLE_WAN = "wants"; //テーブル名
    //募集テーブルカラム
    String WAN_COL_ID = "want_id"; //id
    String WAN_COL_MET = "meeting_id"; //アイデア募集中の会議
    String WAN_COL_TEA = "team_id"; //アイデア募集中の会議
    String WAN_COL_TITLE = "title"; //タイトル
    String WAN_COL_CONTENT = "content"; //内容
    String WAN_COL_DUE_DATE = "due_date"; //〆切

    //アイデアテーブル
    String TABLE_IDE = "ideas"; //テーブル名
    //アイデアテーブルカラム
    String IDE_COL_ID = "idea_id"; //id
    String IDE_COL_WAN = "want_id"; //募集
    String IDE_COL_EMP = "employee_id"; //アイデアを書いた従業員
    String IDE_COL_CONTENT = "content"; //内容

    //タスクテーブル
    String TABLE_TAS = "tasks"; //テーブル名
    //タスクテーブルカラム
    String TAS_COL_ID = "want_id"; //id
    String TAS_COL_EMP = "employee_id"; //担当者
    String TAS_COL_TEA = "team_id"; //チーム
    String TAS_COL_TITLE = "title"; //プロジェクト名
    String TAS_COL_PROGRESS = "progress"; //進捗状況
    String TAS_COL_PROBLEM = "problem"; //問題点
    String TAS_COL_SOLUTION = "solution"; //解決策
    String TAS_COL_DUE_DATE = "due_date"; //期日
    String TAS_COL_STATUS = "status"; //TODO未・済

    int TAS_STA_TRUE = 1; //TODO済
    int TAS_STA_FALSE = 0; //TODO未

    //Entity名
    String ENTITY_EMP = "employee"; //従業員
    String ENTITY_COM = "company"; //会社
    String ENTITY_MIN = "minute"; //議事録
    String ENTITY_ATT = "attendee"; //参加者
    String ENTITY_AGE = "agenda"; //議事
    String ENTITY_MEE = "meeting"; //会議
    String ENTITY_TEA = "team"; //チーム
    String ENTITY_TOD = "todo"; //TO DO
    String ENTITY_WAN = "want"; //募集
    String ENTITY_IDE = "idea"; //アイデア
    String ENTITY_TAS = "task"; //アイデア

    //JPQL内パラメータ
    String JPQL_PARM_CODE = "code"; //社員番号
    String JPQL_PARM_PASSWORD = "password"; //パスワード
    String JPQL_PARM_EMPLOYEE = "employee"; //従業員
    String JPQL_PARM_COMPANY = "company"; //従業員
    String JPQL_PARM_TEAM = "team"; //チーム
    String JPQL_PARM_DUEDATE = "dueDate"; //アイデア募集締切日
    String JPQL_PARM_DATE = "date"; //会議開催日
    String JPQL_PARM_MEETING = "meeting"; //会議
    String JPQL_PARM_WANT = "want"; //会議
    String JPQL_PARM_TODAY = "today";  //今日の日付
    String JPQL_PARM_STATUS = "status";  //今日の日付

    //NamedQueryの nameとquery
    //【従業員】自社の全ての従業員をidの降順に取得する
    String Q_EMP_GET_ALL = ENTITY_EMP + ".getAll"; //name
    String Q_EMP_GET_ALL_DEF = "SELECT e FROM Employee AS e WHERE e.company_id = :" + JPQL_PARM_COMPANY;

    //【従業員】全ての従業員の件数を取得する
    String Q_EMP_COUNT = ENTITY_EMP + ".count";
    String Q_EMP_COUNT_DEF = "SELECT COUNT(e) FROM Employee AS e";

    //【従業員】社員番号とハッシュ化済パスワードを条件に未削除の従業員を取得する
    String Q_EMP_GET_BY_CODE_AND_PASS = ENTITY_EMP + ".getByCodeAndPass";
    String Q_EMP_GET_BY_CODE_AND_PASS_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.code = :" + JPQL_PARM_CODE + " AND e.password = :" + JPQL_PARM_PASSWORD + " AND e.company_id = :" + JPQL_PARM_COMPANY;

    //【従業員】指定した社員番号・会社IDを保持する従業員の件数を取得する
    String Q_EMP_COUNT_RESISTERED_BY_CODE = ENTITY_EMP + ".countRegisteredByCode";
    String Q_EMP_COUNT_RESISTERED_BY_CODE_DEF = "SELECT COUNT(e) FROM Employee AS e WHERE e.code = :" + JPQL_PARM_CODE + " AND e.company_id = :" + JPQL_PARM_COMPANY;

    //【従業員】指定したチームの従業員を取得する
    String Q_EMP_GET_BY_TEAM = ENTITY_EMP + ".getByTeam";
    String Q_EMP_GET_BY_TEAM_DEF = "SELECT e FROM Employee AS e WHERE e.deleteFlag = 0 AND e.team = :" + JPQL_PARM_TEAM;

    //【募集】該当の会議の募集期限内募集をidの降順に取得する
    String Q_WAN_GET_BY_MEETING = ENTITY_WAN + ".getByMeeting"; //name
    String Q_WAN_GET_BY_MEETING_DEF = "SELECT w FROM Want AS w WHERE w.meeting = :" + JPQL_PARM_MEETING;

    //【募集】自分のチームかつ募集期限内募集をidの降順に取得する
    String Q_WAN_GET_ALL = ENTITY_WAN + ".getAll";
    String Q_WAN_GET_ALL_DEF = "SELECT w FROM Want AS w";

    //【会議】自分のチームの会議をidの降順に取得する
    String Q_MEE_GET_ALL = ENTITY_MEE + ".getAll";
    String Q_MEE_GET_ALL_DEF = "SELECT m FROM Meeting AS m ORDER BY m.date DESC";


    //【チーム】自分の会社のすべてのチームを取得する
    String Q_TEA_GET_ALL = ENTITY_TEA + ".getAll"; //name
    String Q_TEA_GET_ALL_DEF = "SELECT t FROM Team AS t WHERE t.company_id = :" + JPQL_PARM_COMPANY;

  //【会社】すべての会社を取得する
    String Q_COM_GET_ALL = ENTITY_COM + ".getAll"; //name
    String Q_COM_GET_ALL_DEF = "SELECT c FROM Company AS c ORDER BY c.id DESC"; //query

  //【アイデア】会議に集まったアイデアをidの降順に取得する
  String Q_IDE_GET_ALL = ENTITY_IDE + ".getAll";
  String Q_IDE_GET_ALL_DEF = "SELECT i FROM Idea AS i WHERE i.want = :" + JPQL_PARM_WANT;

  //【議題】会議の議題を取得する
  String Q_AGE_GET_ALL = ENTITY_AGE + ".getAll";
  String Q_AGE_GET_ALL_DEF = "SELECT a FROM Agenda AS a WHERE a.meeting = :" + JPQL_PARM_MEETING;

  //【TODO】自分のチームのTODOを取得する
  String Q_TODO_GET_TEAM = ENTITY_TOD + ".getTeam"; //name
  String Q_TODO_GET_TEAM_DEF = "SELECT t FROM Todo AS t WHERE t.team = :" + JPQL_PARM_TEAM + " ORDER BY t.deadline ASC";

 //【TODO】自分のTODOを取得する
  String Q_TODO_GET_MINE = ENTITY_TOD + ".getMine"; //name
  String Q_TODO_GET_MINE_DEF = "SELECT t FROM Todo AS t WHERE t.employee = :" + JPQL_PARM_EMPLOYEE;

  //【TODO】該当の会議のTODOを取得する
  String Q_TODO_GET_BY_MEETING = ENTITY_TOD + ".getByMeeting"; //name
  String Q_TODO_GET_BY_MEETING_DEF = "SELECT t FROM Todo AS t WHERE t.meeting = :" + JPQL_PARM_MEETING;

  //【議事録】該当の会議の議事録を取得する
  String Q_MINUTE_GET_BY_MEETING = ENTITY_MIN + ".getByMeeting"; //name
  String Q_MINUTE_GET_BY_MEETING_DEF = "SELECT m FROM Minute AS m WHERE m.meeting = :" + JPQL_PARM_MEETING;

  //【プロジェクト】該当のチーム・未完了のプロジェクトを取得する
  String Q_TASK_GET_BY_TEAM_AND_STATUS = ENTITY_TAS + ".getByTeamAndStatus"; //name
  String Q_TASK_GET_BY_TEAM_AND_STATUS_DEF = "SELECT t FROM Task AS t WHERE t.team = :" + JPQL_PARM_TEAM + " AND t.status = :" + JPQL_PARM_STATUS + " ORDER BY t.progress DESC";

  //【プロジェクト】自分のプロジェクトを取得する
  String Q_TASK_GET_BY_EMPLOYEE_AND_STATUS = ENTITY_TAS + ".getByEmployeeAndStatus"; //name
  String Q_TASK_GET_BY_EMPLOYEE_AND_STATUS_DEF = "SELECT t FROM Task AS t WHERE t.employee = :" + JPQL_PARM_EMPLOYEE + " AND t.status = :" + JPQL_PARM_STATUS;

}
