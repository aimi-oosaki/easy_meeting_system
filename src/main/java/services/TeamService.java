package services;

import java.util.List;

import actions.views.TeamConverter;
import actions.views.TeamView;
import constants.JpaConst;
import models.Team;
import models.validators.TeamValidator;

/**
 * チームテーブルの操作に関わる処理を行うクラス
 */
public class TeamService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、TeamViewのリストで返却する
     * @param page ページ数
     * @param company_id 会社ID
     * @return 表示するデータのリスト
     */
    public List<TeamView> getPerPage(int page, Integer company_id){
        List<Team> teams = em.createNamedQuery(JpaConst.Q_TEA_GET_ALL, Team.class)
                .setParameter(JpaConst.JPQL_PARM_COMPANY, company_id)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return TeamConverter.toViewList(teams);
    }

    /**
     * idを条件に取得したデータをTeamViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TeamView findOne(int id) {
        Team t = findOneInternal(id);
        return TeamConverter.toView(t);
    }

    /**
     * 画面から入力されたチームの登録内容を元にデータを1件作成し、チームテーブルに登録する
     * @param tv 画面から入力されたチームの登録内容
     * @return バリデーションや登録処理中に発生したエラーのリスト
     */
    public List<String> create(TeamView tv){
        //登録内容のバリデーションを行う
        List<String> errors = TeamValidator.validate(this, tv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(TeamConverter.toModel(tv));
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;

    }

    /**
     * 画面から入力されたチームの更新内容を元にデータを1件作成し、チームテーブルを更新する
     * @param tv 画面から入力されたチームの登録内容
     * @return バリデーションや更新処理中に発生したエラーのリスト
     */
    public List<String> update(TeamView tv){
        //idを条件に登録済みのチーム情報を取得する
        TeamView savedTea = findOne(tv.getId());

        savedTea.setName(tv.getName());
        savedTea.setDelete_flag(JpaConst.TEA_DEL_TRUE);

        //更新内容についてバリデーションを行う
        List<String> errors = TeamValidator.validate(this, tv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Team t = findOneInternal(tv.getId());
            TeamConverter.copyViewToModel(t, savedTea);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * チームデータを削除する
     * @param id チームID
     */
    public void destroy(Integer id) {
        //idを条件に登録済みのチーム情報を取得する
        TeamView savedTeam = findOne(id);

        //論理削除フラグをたてる
        savedTeam.setDelete_flag(JpaConst.TEA_DEL_TRUE);

        //更新処理を行う
        update(savedTeam);
    }

    /**
     * idを条件にデータを1件取得し、Teamのインスタンスで返却する
     * @param id Team
     * @return 取得データのインスタンス
     */
    private Team findOneInternal(int id) {
        Team t = em.find(Team.class, id);

        return t;
    }


}
