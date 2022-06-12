package services;

import java.util.List;

import actions.views.IdeaConverter;
import actions.views.IdeaView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import actions.views.WantConverter;
import actions.views.WantView;
import constants.JpaConst;
import models.Idea;
import models.Meeting;
import models.Team;
import models.Want;
import models.validators.WantValidator;


/**
 * 従業員テーブルの操作に関わる処理を行うクラス
 */
public class WantService extends ServiceBase{
    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<WantView> getPerPage(int page){
        List<Want> Wants = em.createNamedQuery(JpaConst.Q_WAN_GET_ALL, Want.class)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return WantConverter.toViewList(Wants);
    }

    /**
     * 指定されたページ数の一覧画面に表示するデータを取得し、WantViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<WantView> getPerPageByMeeting(int page, Meeting m){
        List<Want> Wants = em.createNamedQuery(JpaConst.Q_WAN_GET_BY_MEETING, Want.class)
                .setParameter(JpaConst.JPQL_PARM_MEETING, m)
                .setFirstResult(JpaConst.ROW_PER_PAGE * (page - 1))
                .setMaxResults(JpaConst.ROW_PER_PAGE)
                .getResultList();

        return WantConverter.toViewList(Wants);
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
     * 指定されたページ数の一覧画面に表示するデータを取得し、IdeaViewのリストで返却する
     * @param page ページ数
     * @return 表示するデータのリスト
     */
    public List<IdeaView> getIdeas(Want w){
        List<Idea> ideas = em.createNamedQuery(JpaConst.Q_IDE_GET_ALL, Idea.class)
                .setParameter(JpaConst.JPQL_PARM_WANT, w)
                .getResultList();

        return IdeaConverter.toViewList(ideas);
    }

    /**

    /**
     * idを条件に取得したデータをWantViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public WantView findOne(int id) {
        Want w = findOneInternal(id);
        return WantConverter.toView(w);
    }

    /**
     * team_idを条件に取得したデータをTeamViewのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    public TeamView findOneTeam(int id) {
        Team t = findOneInternalTeam(id);
        return TeamConverter.toView(t);
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
    public List<String> create(WantView wv){
        //登録内容のバリデーションを行う
        List<String> errors = WantValidator.validate(this, wv);

        //バリデーションエラーがなければデータを登録する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            em.persist(WantConverter.toModel(wv));
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
    public List<String> update(WantView wv){
        //idを条件に登録済みの従業員情報を取得する
        WantView savedWan = findOne(wv.getId());

        savedWan.setMeeting(wv.getMeeting());
        savedWan.setTeam(wv.getTeam());
        savedWan.setTitle(wv.getTitle());
        savedWan.setContent(wv.getContent());
        savedWan.setDue_date(wv.getDue_date());

        //更新内容についてバリデーションを行う
        List<String> errors = WantValidator.validate(this, wv);

        //バリデーションエラーがなければデータを更新する
        if(errors.size() == 0) {
            em.getTransaction().begin();
            Want w = findOneInternal(wv.getId());
            WantConverter.copyViewToModel(w, savedWan);
            em.getTransaction().commit();
        }

        //エラーを返却（エラーがなければ0件の空リスト）
        return errors;
    }

    /**
     * 募集データを削除する
     * @param w DBに登録された募集の登録内容
     */
    public void destroy(Want w) {
        em.getTransaction().begin();
        w = em.merge(w);
        em.remove(w);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * 募集データを削除する
     * @param w DBに登録された募集の登録内容
     */
    public void destroyIdea(Idea i) {
        em.getTransaction().begin();
//        i = em.merge(i);
        em.remove(i);       // データ削除
        em.getTransaction().commit();
        em.close();
    }

    /**
     * idを条件にデータを1件取得し、Projectのインスタンスで返却する
     * @param id
     * @return 取得データのインスタンス
     */
    private Want findOneInternal(int id) {
        Want w = em.find(Want.class, id);

        return w;
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

}
