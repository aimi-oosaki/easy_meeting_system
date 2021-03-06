package services;

import java.util.List;

import actions.views.EmployeeConverter;
import actions.views.EmployeeView;
import actions.views.IdeaConverter;
import actions.views.IdeaView;
import actions.views.WantConverter;
import actions.views.WantView;
import constants.JpaConst;
import models.Employee;
import models.Idea;
import models.Want;
import models.validators.IdeaValidator;

/**
 * アイデアテーブルの操作に関わる処理を行うクラス
 */
public class IdeaService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、IdeaViewのリストで返却する
     * @param page ページ数
     * @param want 募集インスタンス
     * @return 表示するデータのリスト
     */
    public List<IdeaView> getPerPage(int page, Want want){
        List<Idea> ideas = em.createNamedQuery(JpaConst.Q_IDE_GET_ALL, Idea.class)
                .setParameter(JpaConst.JPQL_PARM_WANT, want)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return IdeaConverter.toViewList(ideas);
    }

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public IdeaView findOne(int id) {
        Idea i = findOneInternal(id);
        return IdeaConverter.toView(i);
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
     * want_idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public WantView findOneProject(int id) {
        Want w = findOneInternalWant(id);
        return WantConverter.toView(w);
    }

    /**
     * 画面から入力されたアイデアの登録内容を元にデータを1件作成し、アイデアテーブルに登録する
     * @param iv 画面から入力されたアイデアの登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(IdeaView iv){
        //登録内容のバリデーションを行う
        List<String> errors = IdeaValidator.validate(this, iv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(IdeaConverter.toModel(iv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力されたアイデアの内容を元にデータを1件更新し、アイデアテーブルを更新する
     * @param iv 画面から入力されたアイデアの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(IdeaView iv){
        //idを条件に登録済みの従業員情報を取得する
        IdeaView savedIdea = findOne(iv.getId());

        savedIdea.setWant(iv.getWant());
        savedIdea.setEmployee(iv.getEmployee());
        savedIdea.setContent(iv.getContent());

        //更新内容についてバリデーションを行う
        List<String> errors = IdeaValidator.validate(this, iv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Idea i = findOneInternal(iv.getId());
            IdeaConverter.copyViewToModel(i, savedIdea);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * アイデアデータを削除する
     * @param i DBに登録されたアイデアの登録内容
     */
    public void destroy(Idea i) {
        em.getTransaction().begin();
        i = em.merge(i);
        em.remove(i);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Ideaのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Idea findOneInternal(int id) {
        Idea i = em.find(Idea.class, id);

        return i;
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
     * idを条件に募集データを1件取得し、Wantのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Want findOneInternalWant(int id) {
        Want w = em.find(Want.class, id);

        return w;
    }

}
