package services;

import java.util.List;

import actions.views.AgendaConverter;
import actions.views.AgendaView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TaskConverter;
import actions.views.TaskView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import actions.views.TodoConverter;
import actions.views.TodoView;
import constants.JpaConst;
import models.Agenda;
import models.Meeting;
import models.Task;
import models.Team;
import models.Todo;
import models.validators.MeetingValidator;

/**
 * 会議テーブルの操作に関わる処理を行うクラス
 */
public class MeetingService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<MeetingView> getPerPage(int page){
        List<Meeting> meetings = em.createNamedQuery(JpaConst.Q_MEE_GET_ALL, Meeting.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return MeetingConverter.toViewList(meetings);
    }

    /**
     * idを条件に取得したデータをMeetingViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MeetingView findOne(int id) {
        Meeting m = findOneInternal(id);
        return MeetingConverter.toView(m);
    }

    /**
     * idを条件に取得したデータをTeamViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TeamView findOneTeam(int id) {
        Team t = findOneInternalTeam(id);
        return TeamConverter.toView(t);
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、AgendViewのリストで返却する
     * @param m 会議インスタンス
     * @return 表示するデータのリスト
     */
    public List<AgendaView> getAgendas(Meeting m){
        List<Agenda> agendas = em.createNamedQuery(JpaConst.Q_AGE_GET_ALL, Agenda.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return AgendaConverter.toViewList(agendas);
    }

    /**
     * 画面から入力された会議の登録内容を元にデータを1件作成し、会議テーブルに登録する
     * @param mv 画面から入力された会議の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(MeetingView mv){
        //登録内容のバリデーションを行う
        List<String> errors = MeetingValidator.validate(this, mv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(MeetingConverter.toModel(mv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力された会議の更新内容を元にデータを1件作成し、会議テーブルを更新する
     * @param mv 画面から入力された会議の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(MeetingView mv){
        //idを条件に登録済みの従業員情報を取得する
        MeetingView savedMee = findOne(mv.getId());

        savedMee.setTeam(mv.getTeam());
        savedMee.setName(mv.getName());
        savedMee.setDate(mv.getDate());
        savedMee.setDelete_flag(mv.getDelete_flag());

        //更新内容についてバリデーションを行う
        List<String> errors = MeetingValidator.validate(this, mv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Meeting m = findOneInternal(mv.getId());
            MeetingConverter.copyViewToModel(m, savedMee);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件に会議データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        MeetingView savedMee = findOne(id);

        //論理削除フラグをたてる
        savedMee.setDelete_flag(JpaConst.MEE_DEL_TRUE);

        //更新処理を行う
        update(savedMee);

    }

    /**
     * idを条件にデータを1件取得し、Meetingのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Meeting findOneInternal(int id) {
        Meeting m = em.find(Meeting.class, id);

        return m;
    }

    /**
     * idを条件にデータを1件取得し、Teamのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Team findOneInternalTeam(int id) {
        Team t = em.find(Team.class, id);

        return t;
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TeamViewのリストで返却する
     * @param compnay_id 会議ID
     * @return 表示するデータのリスト
     */
    public List<TeamView> getTeam(Integer compnay_id){
        List<Team> teams = em.createNamedQuery(JpaConst.Q_TEA_GET_ALL, Team.class)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, compnay_id)
                .getResultList();

        return TeamConverter.toViewList(teams);
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TodoViewのリストで返却する
     * @param m 会議インスタンス
     * @return 表示するデータのリスト
     */
    public List<TodoView> getTodos(Meeting m){
        List<Todo> todos = em.createNamedQuery(JpaConst.Q_TODO_GET_BY_MEETING, Todo.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return TodoConverter.toViewList(todos);
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TaskViewのリストで返却する
     * @param page ページ数
     * @param t チームインスタンス
     * @return 表示するデータのリスト
     */
    public List<TaskView> getPerPageByTeam(int page, Team t){
        List<Task> Tasks = em.createNamedQuery(JpaConst.Q_TASK_GET_BY_TEAM_AND_STATUS, Task.class)
                .setParameter(JpaConst.JPQL_PARM_TEAM, t)
                .setParameter(JpaConst.JPQL_PARM_STATUS, 0)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return TaskConverter.toViewList(Tasks);
    }

}
