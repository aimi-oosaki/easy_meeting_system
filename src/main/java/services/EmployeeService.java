package services;

import java.util.List;

import javax.persistence.NoResultException;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import constants.JpaConst;
import models.Employee;
import models.Team;
import models.validators.EmployeeValidator;
import models.validators.TeamValidator;
import utils.EncryptUtil;

/**
 * 従業員テーブルの操作に関わる処理を行うクラス
 */
public class EmployeeService extends ServiceBase {

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、EmployeeViewのリストで返却する
     * @param page ページ数
     * @param company_id 会社ID
     * @return 表示するデータのリスト
     */
    public List<EmployeeView> getPerPage(int page, int company_id) {
        List<Employee> employees = em.createNamedQuery(JpaConst.Q_EMP_GET_ALL, Employee.class)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, company_id)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return EmployeeConverter.toViewList(employees);
    }

    /**
     * 従業員テーブルのデータの件数を取得し、返却する
     * @return 従業員テーブルのデータの件数
     */
    public long countAll() {
        long empCount = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT, Long.class)
                .getSingleResult();

        return empCount;
    }

    /**
     * 社員番号、パスワード、会社IDを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param code 社員番号
     * @param plainPass パスワード文字列
     * @param pepper pepper文字列
     * @param company_id 会社ID
     * @return 取得データのインスタンス
     */
    public EmployeeView findOne(String code, String plainPass, String pepper, Integer company_id) {
        Employee e = null;
        try {
            //パスワードのハッシュ化
            String pass = EncryptUtil.getPasswordEncrypt(plainPass, pepper);

            //社員番号とハッシュ化済パスワードを条件に未削除の従業員を1件取得する
            e = em.createNamedQuery(JpaConst.Q_EMP_GET_BY_CODE_AND_PASS, Employee.class)
                    .setParameter(JpaConst.JPQL_PARM_CODE, code)
                    .setParameter(JpaConst.JPQL_PARM_PASSWORD, pass)
                    .setParameter(JpaConst.JPQL_PARM_COMPANY, company_id)
                    .getSingleResult();

        } catch (NoResultException ex) {
        }

        return EmployeeConverter.toView(e);

    }

    /**
     * idを条件に取得したデータをEmployeeViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public EmployeeView findOne(int id) {
        Employee e = findOneInternal(id);
        return EmployeeConverter.toView(e);
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
     * 社員番号、会社IDを条件に該当するデータの件数を取得し、返却する
     * @param code 社員番号
     * @param company_id 会社ID
     * @return 該当するデータの件数
     */
    public long countByCode(String code, Integer company_id) {

        //指定した社員番号を保持する従業員の件数を取得する
        long employees_count = (long) em.createNamedQuery(JpaConst.Q_EMP_COUNT_RESISTERED_BY_CODE, Long.class)
                .setParameter(JpaConst.JPQL_PARM_CODE, code)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, company_id)
                .getSingleResult();
        return employees_count;
    }

    /**
     * 画面から入力された従業員の登録内容を元にデータを1件作成し、従業員テーブルに登録する
     * @param ev 画面から入力された従業員の登録内容
     * @param pepper pepper文字列
     * @param company_id 会社ID
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(EmployeeView ev, String pepper, Integer company_id) {

        //パスワードをハッシュ化して設定
        String pass = EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper);
        ev.setPassword(pass);

        //登録内容のバリデーションを行う
        List<String> errors = EmployeeValidator.validate(this, ev, true, true, company_id);

        //バリデーションエラーがなければデータを登録する
        if (errors.size() == 0) {
            create(ev);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 画面から入力された募集の登録内容を元にデータを1件作成し、募集テーブルに登録する
     * @param tv 画面から入力されたチームの登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> validateTeam(TeamView tv){
        TeamService ts = new TeamService();

        //登録内容のバリデーションを行う
        List<String> errors = TeamValidator.validate(ts, tv);

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力されたチームの登録内容を元にデータを1件作成し、チームテーブルに登録する
     * @param tv 画面から入力されたチームの登録内容
     * @return 取得データのインスタンス
     */
    public TeamView createTeam(TeamView tv){
        //バリデーションエラーがなければデータを登録する
            em.getTransaction().begin();
            Team team = TeamConverter.toModel(tv);
            em.persist(team);
            em.getTransaction().commit();

            return TeamConverter.toView(team);
    }

    /**
     * 画面から入力された従業員の更新内容を元にデータを1件作成し、従業員テーブルを更新する
     * @param ev 画面から入力された従業員の登録内容
     * @param pepper pepper文字列
     * @param company_id 会社ID
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(EmployeeView ev, String pepper, Integer company_id) {

        //idを条件に登録済みの従業員情報を取得する
        EmployeeView savedEmp = findOne(ev.getId());

        boolean validateCode = false;
        if (!savedEmp.getCode().equals(ev.getCode())) {
            //社員番号を更新する場合

            //社員番号についてのバリデーションを行う
            validateCode = true;
            //変更後の社員番号を設定する
            savedEmp.setCode(ev.getCode());
        }

        boolean validatePass = false;
        if (ev.getPassword() != null && !ev.getPassword().equals("")) {
            //パスワードに入力がある場合

            //パスワードについてのバリデーションを行う
            validatePass = true;

            //変更後のパスワードをハッシュ化し設定する
            savedEmp.setPassword(
                    EncryptUtil.getPasswordEncrypt(ev.getPassword(), pepper));
        }

        savedEmp.setName(ev.getName()); //変更後の氏名を設定する
        savedEmp.setAdminFlag(ev.getAdminFlag()); //変更後の管理者フラグを設定する

        //更新内容についてバリデーションを行う
        List<String> errors = EmployeeValidator.validate(this, savedEmp, validateCode, validatePass, company_id);

        //バリデーションエラーがなければデータを更新する
        if (errors.size() == 0) {
            update(savedEmp);
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * idを条件に従業員データを論理削除する
     * @param id
     */
    public void destroy(Integer id) {

        //idを条件に登録済みの従業員情報を取得する
        EmployeeView savedEmp = findOne(id);

        //論理削除フラグをたてる
        savedEmp.setDeleteFlag(JpaConst.EMP_DEL_TRUE);

        //更新処理を行う
        update(savedEmp);

    }

    /**
     * 社員番号とパスワードを条件に検索し、データが取得できるかどうかで認証結果を返却する
     * @param code 社員番号
     * @param plainPass パスワード
     * @param pepper pepper文字列
     * @param company_id 会社ID
     * @return 認証結果を返却す(成功:true 失敗:false)
     */
    public Boolean validateLogin(String code, String plainPass, String pepper, Integer company_id) {

        boolean isValidEmployee = false;
        if (code != null && !code.equals("") && plainPass != null && !plainPass.equals("") && company_id != null) {
            EmployeeView ev = findOne(code, plainPass, pepper, company_id);

            if (ev != null && ev.getId() != null) {

                //データが取得できた場合、認証成功
                isValidEmployee = true;
            }
        }

        //認証結果を返却する
        return isValidEmployee;
    }

    /**
     * idを条件にデータを1件取得し、Employeeのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Employee findOneInternal(int id) {
        Employee e = em.find(Employee.class, id);

        return e;
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
     * 従業員データを1件登録する
     * @param ev 従業員データ
     */
    private void create(EmployeeView ev) {
        em.getTransaction().begin();
        em.persist(EmployeeConverter.toModel(ev));
        em.getTransaction().commit();
    }

    /**
     * 従業員データを更新する
     * @param ev 画面から入力された従業員の登録内容
     */
    private void update(EmployeeView ev) {
        em.getTransaction().begin();
        Employee e = findOneInternal(ev.getId());
        EmployeeConverter.copyViewToModel(e, ev);
        em.getTransaction().commit();
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TeamViewのリストで返却する
     * @param c 会社ID
     * @return 表示するデータのリスト
     */
    public List<TeamView> getTeam(Integer c){
        List<Team> teams = em.createNamedQuery(JpaConst.Q_TEA_GET_ALL, Team.class)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, c)
                .getResultList();

        return TeamConverter.toViewList(teams);
    }
}