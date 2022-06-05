package services;

import java.util.List;

import actions.views.AgendaConverter;
import actions.views.AgendaView;
import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.MinuteConverter;
import actions.views.MinuteView;
import constants.JpaConst;
import models.Agenda;
import models.Employee;
import models.Meeting;
import models.Minute;
import models.Team;

///**
// * 従業員テーブルの操作に関わる処理を行うクラス
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
 * 従業員テーブルの操作に関わる処理を行うクラス
 */
public class MinuteService extends ServiceBase{
//    /**
//     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
//     * @param page ページ数
//     * @return 表示するデータのリスト
//     */
//    public List<WantView> getPerPage(int page){
//        List<Project> Projects = em.createNamedQuery(JpaConst.Q_WAN_GET_ALL, Project.class)
//                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
//                .setMaxResults(JpaConst.ROW_PER_PAGE)
//                .getResultList();
//
//        return ProjectConverter.toViewList(Projects);
//    }

//    /**
//     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
//     * @param page ページ数
//     * @return 表示するデータのリスト
//     */
//    public List<WantView> getPerPageByMeeting(int page, Meeting m){
//        List<Project> Projects = em.createNamedQuery(JpaConst.Q_WAN_GET_BY_MEETING, Project.class)
//                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
//                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
//                .setMaxResults(JpaConst.ROW_PER_PAGE)
//                .getResultList();
//
//        return ProjectConverter.toViewList(Projects);
//    }

//    /**
//     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
//     * @param page ページ数
//     * @return 表示するデータのリスト
//     */
//    public List<MeetingView> getMeeting(){
//        List<Meeting> meetings = em.createNamedQuery(JpaConst.Q_MEE_GET_ALL, Meeting.class)
//                .getResultList();
//
//        return MeetingConverter.toViewList(meetings);
//    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、IdeaViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<AgendaView> getAgendas(Meeting m){
        List<Agenda> agendas = null;
        agendas = em.createNamedQuery(JpaConst.Q_AGE_GET_ALL, Agenda.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return AgendaConverter.toViewList(agendas);
    }

    /**

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MinuteView findOne(int id) {
        Minute m = findOneInternal(id);
        return MinuteConverter.toView(m);
    }

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MinuteView findOneByMeeting(Meeting m) {
        Minute min = null;
            min = em.createNamedQuery(JpaConst.Q_MINUTE_GET_BY_MEETING, Minute.class)
                    .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                    .getSingleResult();

            return MinuteConverter.toView(min);
    }

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public AgendaView findOneAgenda(int id) {
        Agenda a = em.find(Agenda.class, id);
        return AgendaConverter.toView(a);
    }

    /**
     * meeting_idを条件に取得したデータをMeetingViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public EmployeeView findOneEmployee(int id) {
        Employee e = findOneInternalEmployee(id);
        return EmployeeConverter.toView(e);
    }

    /**
     * meeting_idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public List<EmployeeView> findEmployees(int meeting_id) {
        Meeting m = findOneInternalMeeting(meeting_id);
        int team_id = m.getTeam().getId();
        Team t = em.find(Team.class, team_id);

        List<Employee> employees = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_TEAM, Employee.class)
                .setParameter(JpaConst.JPQL_PARM_TEAM, t)
                .getResultList();

        return EmployeeConverter.toViewList(employees);
    }

    /**
     * _idを条件に取得したデータをMeetingViewのインスタンスで返却する
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
    public void create(MinuteView mv){
            em.getTransaction().begin();
            em.persist(MinuteConverter.toModel(mv));
            em.getTransaction().commit();
    }

    /**
     * 画面から入力された募集の更新内容を元にデータを1件作成し、募集テーブルを更新する
     * @param wv 画面から入力された募集の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public void update(MinuteView mv){
        //idを条件に登録済みの従業員情報を取得する
        MinuteView savedMin = findOne(mv.getId());

        savedMin.setMeeting(mv.getMeeting());
        savedMin.setAttendees(mv.getAttendees());
        savedMin.setDecided(mv.getDecided());
        savedMin.setPending(mv.getPending());

            em.getTransaction().begin();
            Minute m = findOneInternal(mv.getId());
            MinuteConverter.copyViewToModel(m, savedMin);
            em.getTransaction().commit();
    }

    /**
     * 画面から入力された募集の更新内容を元にデータを1件作成し、募集テーブルを更新する
     * @param wv 画面から入力された募集の登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public void updateAgenda(AgendaView av){
        //idを条件に登録済みの従業員情報を取得する
        AgendaView savedAge = findOneAgenda(av.getId());

        savedAge.setSummary(av.getSummary());

        //更新内容についてバリデーションを行う
        em.getTransaction().begin();
        Agenda a = em.find(Agenda.class, av.getId());
        AgendaConverter.copyViewToModel(a, savedAge);
        em.getTransaction().commit();
    }

    /**
     * 募集データを削除する
     * @param w DBに登録された募集の登録内容
     */
    public void destroy(Minute m) {
        em.getTransaction().begin();
        m = em.merge(m);
        em.remove(m);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Projectのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Minute findOneInternal(int id) {
        Minute m = em.find(Minute.class, id);

        return m;
    }

    /**
     * idを条件にチームデータを1件取得し、Teamのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Employee findOneInternalEmployee(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
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

}
