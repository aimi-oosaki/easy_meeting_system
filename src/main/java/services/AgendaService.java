package services;

import java.util.List;

import actions.views.AgendaConverter;
import actions.views.AgendaView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import constants.JpaConst;
import models.Agenda;
import models.Meeting;
import models.validators.AgendaValidator;

///**
// * 議題テーブルの操作に関わる処理を行うクラス
// */
//public class ProjectService extends ServiceBase{
//    /**
//     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
//     * @param page ページ数
//     * @return 表示するデータのリスト
//     */
//    public List<WantView> getPerPage(int page, int team, LocalDate today){
//        List<Project> Projects = em.createNamedQuery(JpaConst.Q_WAN_GET_ALL, Project.class)
//                .setParameter(JpaConst.JPQL_PARM_TEAM, team)
//                .setParameter(JpaConst.JPQL_PARM_TODAY, today)
//                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
//                .setMaxResults(JpaConst.ROW_PER_PAGE)
//                .getResultList();
//
//        return ProjectConverter.toViewList(Projects);
//    }

/**
 * 議題テーブルの操作に関わる処理を行うクラス
 */
public class AgendaService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<AgendaView> getPerPage(int page, Meeting meeting){

        List<Agenda> agendas = em.createNamedQuery(JpaConst.Q_AGE_GET_ALL, Agenda.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, meeting)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return AgendaConverter.toViewList(agendas);
    }

//    /**
//     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
//     * @param page ページ数
//     * @return 表示するデータのリスト
//     */
//    public List<AgendaView> getAgenda(){
//        List<Meeting> meetings = em.createNamedQuery(JpaConst.Q_MEE_GET_ALL, Meeting.class)
//                .getResultList();
//
//        return MeetingConverter.toViewList(meetings);
//    }

    /**

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public AgendaView findOne(int id) {
        Agenda a = findOneInternal(id);
        return AgendaConverter.toView(a);
    }

    /**
     * team_idを条件に取得したデータをMeetingViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MeetingView findOneMeeting(int id) {
        Meeting m = findOneInternalMeeting(id);
        return MeetingConverter.toView(m);
    }

    /**
     * 画面から入力された募集の登録内容を元にデータを1件作成し、募集テーブルに登録する
     * @param wv 画面から入力された募集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(AgendaView av){
        //登録内容のバリデーションを行う
        List<String> errors = AgendaValidator.validate(this, av);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(AgendaConverter.toModel(av));
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
    public List<String> update(AgendaView av){
        //idを条件に登録済みの従業員情報を取得する
        AgendaView savedAge = findOne(av.getId());

        savedAge.setMeeting(av.getMeeting());
        savedAge.setTitle(av.getTitle());
        savedAge.setSummary(av.getSummary());

        //更新内容についてバリデーションを行う
        List<String> errors = AgendaValidator.validate(this, av);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Agenda a = findOneInternal(av.getId());
            AgendaConverter.copyViewToModel(a, savedAge);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 議題データを削除する
     * @param a DBに登録された議題の登録内容
     */
    public void destroy(Agenda a) {
        em.getTransaction().begin();
        a = em.merge(a);
        em.remove(a);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Projectのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Agenda findOneInternal(int id) {
        Agenda a = em.find(Agenda.class, id);

        return a;
    }

    /**
     * idを条件にチームデータを1件取得し、Teamのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Meeting findOneInternalMeeting(int id) {
        Meeting m = em.find(Meeting.class, id);

        return m;
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<MeetingView> getMeeting(){
        List<Meeting> meetings = em.createNamedQuery(JpaConst.Q_MEE_GET_ALL, Meeting.class)
                .getResultList();

        return MeetingConverter.toViewList(meetings);
    }

}
