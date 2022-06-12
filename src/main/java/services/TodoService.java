package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TodoConverter;
import actions.views.TodoView;
import constants.JpaConst;
import models.Employee;
import models.Meeting;
import models.Team;
import models.Todo;
import models.validators.TodoValidator;

/**
 * TODOテーブルの操作に関わる処理を行うクラス
 */
public class TodoService extends ServiceBase{

    /**
     * 一覧画面に表示する自分のチームのTODOデータを取得し、TodoViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TodoView> getTodos(Team t){
        List<Todo> todos = em.createNamedQuery(JpaConst.Q_TODO_GET_TEAM, Todo.class)
                .setParameter(JpaConst.JPQL_PARM_TEAM, t)
                .getResultList();

        return TodoConverter.toViewList(todos);
    }

    /**
     * 一覧画面に表示する自分のTODOデータを取得し、TodoViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TodoView> getTodosMine(Employee e){
        List<Todo> todos = em.createNamedQuery(JpaConst.Q_TODO_GET_MINE, Todo.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, e)
                .getResultList();

        return TodoConverter.toViewList(todos);
    }

    /**
     * 一覧画面に表示する自分のTODOデータを取得し、TodoViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<TodoView> getTodosMeeting(Meeting m){
        List<Todo> todos = em.createNamedQuery(JpaConst.Q_TODO_GET_BY_MEETING, Todo.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .getResultList();

        return TodoConverter.toViewList(todos);
    }

    /**
     * idを条件に取得したデータをTodoViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TodoView findOne(int id) {
        Todo t = findOneInternal(id);
        return TodoConverter.toView(t);
    }

    /**
     * idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public EmployeeView findOneEmployee(int id) {
        Employee e = em.find(Employee.class, id);
        return EmployeeConverter.toView(e);
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
     * team_idを条件に取得したデータをMeetingViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public Team findOneTeam(int id) {
        Team t = findOneInternalTeam(id);
        return t;
    }

    /**
     * 画面から入力された募集の登録内容を元にデータを1件作成し、募集テーブルに登録する
     * @param wv 画面から入力された募集の登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(TodoView tv){
        //登録内容のバリデーションを行う
        List<String> errors = TodoValidator.validate(this, tv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(TodoConverter.toModel(tv));
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
    public List<String> update(TodoView tv){
        //idを条件に登録済みの従業員情報を取得する
        TodoView savedTod = findOne(tv.getId());

        savedTod.setEmployee(tv.getEmployee());
        savedTod.setMeeting(tv.getMeeting());
        savedTod.setTeam(tv.getTeam());
        savedTod.setWhat(tv.getWhat());
        savedTod.setDeadline(tv.getDeadline());
        savedTod.setStatus(tv.getStatus());
        savedTod.setConsequence(tv.getConsequence());

        //更新内容についてバリデーションを行う
        List<String> errors = TodoValidator.validate(this, tv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Todo t = findOneInternal(tv.getId());
            TodoConverter.copyViewToModel(t, savedTod);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 募集データを削除する
     * @param w DBに登録された募集の登録内容
     */
    public void destroy(Todo t) {
        em.getTransaction().begin();
        t = em.merge(t);
        em.remove(t);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Projectのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Todo findOneInternal(int id) {
        Todo t = em.find(Todo.class, id);

        return t;
    }

    /**
     * idを条件にチームデータを1件取得し、Teamのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Team findOneInternalTeam(int id) {
        Team t = em.find(Team.class, id);

        return t;
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

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、MeetingViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<EmployeeView> getEmployee(Team team){
        List<Employee> employees = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_TEAM, Employee.class)
                .setParameter(JpaConst.JPQL_PARM_TEAM, team)
                .getResultList();

        return EmployeeConverter.toViewList(employees);
    }

}
