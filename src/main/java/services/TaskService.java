package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.TaskConverter;
import actions.views.TaskView;
import constants.JpaConst;
import models.Employee;
import models.Task;
import models.Team;
import models.validators.TaskValidator;


/**
 * タスクテーブルの操作に関わる処理を行うクラス
 */
public class TaskService extends ServiceBase{
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

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TaskViewのリストで返却する
     * @param page ページ数
     * @param e 従業員インスタンス
     * @return 表示するデータのリスト
     */
    public List<TaskView> getPerPageByMeeting(int page, Employee e){
        List<Task> Tasks = em.createNamedQuery(JpaConst.Q_TASK_GET_BY_EMPLOYEE_AND_STATUS, Task.class)
                .setParameter(JpaConst.JPQL_PARM_EMPLOYEE, e)
                .setParameter(JpaConst.JPQL_PARM_STATUS, 0)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return TaskConverter.toViewList(Tasks);
    }

    /**
     * EmployeeViewのリストで返却する
     * @param t ログイン中の従業員のチームインスタンス
     * @return 表示するデータのリスト
     */
    public List<EmployeeView> getTeamMember(Team t){
        List<Employee> emps = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_TEAM, Employee.class)
                .setParameter(JpaConst.JPQL_PARM_TEAM, t)
                .getResultList();

        return EmployeeConverter.toViewList(emps);
    }

    /**
     * idを条件に取得したデータをTaskViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TaskView findOne(int id) {
        Task t = findOneInternal(id);
        return TaskConverter.toView(t);
    }

    /**
     * team_idを条件に取得したデータをTeamのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public Team findOneTeam(int id) {
        Team t = findOneInternalTeam(id);
        return t;
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
     * 画面から入力されたタスクの登録内容を元にデータを1件作成し、タスクテーブルに登録する
     * @param tv 画面から入力されたプロジェクトの登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(TaskView tv){
        //登録内容のバリデーションを行う
        List<String> errors = TaskValidator.validate(this, tv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(TaskConverter.toModel(tv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力された募集の更新内容を元にデータを1件作成し、タスクテーブルを更新する
     * @param tv 画面から入力されたタスクの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(TaskView tv){
        //idを条件に登録済みのタスク情報を取得する
        TaskView savedTas = findOne(tv.getId());

        savedTas.setEmployee(tv.getEmployee());
        savedTas.setTeam(tv.getTeam());
        savedTas.setTitle(tv.getTitle());
        savedTas.setProgress(tv.getProgress());
        savedTas.setProblem(tv.getProblem());
        savedTas.setSolution(tv.getSolution());
        savedTas.setDue_date(tv.getDue_date());
        savedTas.setStatus(tv.getStatus());

        //更新内容についてバリデーションを行う
        List<String> errors = TaskValidator.validate(this, tv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Task t = findOneInternal(tv.getId());
            TaskConverter.copyViewToModel(t, savedTas);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * タスクデータを削除する
     * @param w DBに登録された募集の登録内容
     */
    public void destroy(Task t) {
        em.getTransaction().begin();
        t = em.merge(t);
        em.remove(t);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Taskのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Task findOneInternal(int id) {
        Task t = em.find(Task.class, id);

        return t;
    }

    /**
     * idを条件にチームデータを1件取得し、Teamのインスタンスで返却する
     * @param id チームID
     * @return 取得データのインスタンス
     */
    private Team findOneInternalTeam(int id) {
        Team t = em.find(Team.class, id);

        return t;
    }

    /**
     * idを条件にチームデータを1件取得し、Employeeのインスタンスで返却する
     * @param id 従業員ID
     * @return 取得データのインスタンス
     */
    private Employee findOneInternalEmployee(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
    }

}
