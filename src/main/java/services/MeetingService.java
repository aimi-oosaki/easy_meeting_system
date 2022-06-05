package services;

import java.util.List;

import actions.views.AgendaConverter;
import actions.views.AgendaView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import actions.views.TodoConverter;
import actions.views.TodoView;
import constants.JpaConst;
import models.Agenda;
import models.Meeting;
import models.Team;
import models.Todo;
import models.validators.MeetingValidator;

/**
 * 従業員テーブルの操作に関わる処理を行うクラス
 */
public class MeetingService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
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
     * idを条件に取得したデータをWantViewのインスタンスで返却する
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
     * @param meeting_id 会議ID
     * @return 表示するデータのリスト
     */
    public List<AgendaView> getAgendas(Meeting m){
        List<Agenda> agendas = em.createNamedQuery(JpaConst.Q_AGE_GET_ALL, Agenda.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return AgendaConverter.toViewList(agendas);
    }

    /**
     * 画面から入力された募集の登録内容を元にデータを1件作成し、募集テーブルに登録する
     * @param wv 画面から入力された募集の登録内容
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
     * 画面から入力された募集の更新内容を元にデータを1件作成し、募集テーブルを更新する
     * @param wv 画面から入力された募集の登録内容
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

//    /**
//     * 募集データを削除する
//     * @param w DBに登録された募集の登録内容
//     */
//    public void destroy(Meeting m) {
//        em.getTransaction().begin();
//        m = em.merge(m);
//        em.remove(m);       // データ削除
//        em.getTransaction().commit();
//        em.close();
//    }

    /**
     * idを条件に従業員データを論理削除する
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
     * idを条件にデータを1件取得し、Projectのインスタンスで返却する
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
     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TeamView> getTeam(Integer compnay_id){
        List<Team> teams = em.createNamedQuery(JpaConst.Q_TEA_GET_ALL, Team.class)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, compnay_id)
                .getResultList();

        return TeamConverter.toViewList(teams);
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、IdeaViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TodoView> getTodos(Meeting m){
        List<Todo> todos = em.createNamedQuery(JpaConst.Q_TODO_GET_BY_MEETING, Todo.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return TodoConverter.toViewList(todos);
    }

//    /**
//     * TODOデータを削除する
//     * @param w DBに登録された募集の登録内容
//     */
//    public void destroyTodo(Todo t) {
//        em.getTransaction().begin();
//        t = em.merge(t);
//        em.remove(t);       // データ削除
//        em.getTransaction().commit();
//        em.close();
//    }
//    /**
//     * TODOデータを削除する
//     * @param w DBに登録された募集の登録内容
//     */
//    public void destroyTodo(List<TodoView> tv) {
//        for(int j = 0; j < tv.size(); j++) {
//            Todo t = TodoConverter.toModel(tv.get(j));
//            em.getTransaction().begin();
//            t = em.merge(t);
//            em.remove(t);       // データ削除
//            em.getTransaction().commit();
//        }
////        em.close();
//    }
}
