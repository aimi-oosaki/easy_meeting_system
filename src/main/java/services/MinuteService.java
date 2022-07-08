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

/**
 * 議事録テーブルの操作に関わる処理を行うクラス
 */
public class MinuteService extends ServiceBase{

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、AgendaViewのリストで返却する
     * @param m 会議インスタンス
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
     * idを条件に取得したデータをMinuteViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public MinuteView findOne(int id) {
        Minute m = findOneInternal(id);
        return MinuteConverter.toView(m);
    }

    /**
     * idを条件に取得したデータをMinuteViewのインスタンスで返却する
     * @param m 会議インスタンス
     * @return 取得データのインスタンス
     */
    @SuppressWarnings("finally")
    public MinuteView findOneByMeeting(Meeting m) {
        Minute min = null;
            try {
            min = em.createNamedQuery(JpaConst.Q_MINUTE_GET_BY_MEETING, Minute.class)
                    .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                    .getSingleResult();
            } catch(Exception e) {

            } finally{
                return MinuteConverter.toView(min);
            }
    }

    /**
     * idを条件に取得したデータをAgendaViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public AgendaView findOneAgenda(int id) {
        Agenda a = em.find(Agenda.class, id);
        return AgendaConverter.toView(a);
    }

    /**
     * employee_idを条件に取得したデータをEmployeeViewのインスタンスで返却する
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
     * 画面から入力された議事録の登録内容を元にデータを1件作成し、議事録テーブルに登録する
     * @param mv 画面から入力された議事録の登録内容
     */
    public void create(MinuteView mv){
            em.getTransaction().begin();
            em.persist(MinuteConverter.toModel(mv));
            em.getTransaction().commit();
    }

    /**
     * 画面から入力された議事録の更新内容を元にデータを1件作成し、議事録テーブルを更新する
     * @param mv 画面から入力された議事録の登録内容
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
     * 画面から入力された議題の更新内容を元にデータを1件作成し、議題テーブルを更新する
     * @param av 画面から入力された議題の登録内容
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
     * 議事録データを削除する
     * @param m DBに登録された議事録の登録内容
     */
    public void destroy(Minute m) {
        em.getTransaction().begin();
        m = em.merge(m);
        em.remove(m);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Minuteのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Minute findOneInternal(int id) {
        Minute m = em.find(Minute.class, id);

        return m;
    }

    /**
     * idを条件に従業員データを1件取得し、Employeeのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Employee findOneInternalEmployee(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
    }

    /**
     * idを条件に会議データを1件取得し、Meetingのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Meeting findOneInternalMeeting(int id) {
        Meeting m = em.find(Meeting.class, id);

        return m;
    }

}
