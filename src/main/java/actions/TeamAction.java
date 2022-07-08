package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Team;
import services.TeamService;

/**
 * チームに関わる処理を行うActionクラス
 *
 */
public class TeamAction extends ActionBase{
    private TeamService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new TeamService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException{
        //セッションからログイン中の従業員の会社情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Integer company_id = ev.getCompany_id();

        //指定されたページ数の一覧画面に表示するチームデータを取得
        int page = getPage();
        List<TeamView> teams = service.getPerPage(page, company_id);

        putRequestScope(AttributeConst.TEAMS, teams); //取得したチームデータ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TEA_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.TEAM, new TeamView()); //空のチームインスタンス

        //新規登録画面を表示
        forward(ForwardConst.FW_TEA_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if(checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            Integer company_id = ev.getCompany_id();

            TeamView tv = new TeamView(
                    null,
                    company_id,
                    getRequestParam(AttributeConst.TEA_NAME),
                    0);

            //募集情報登録
            List<String> errors = service.create(tv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TEAM, tv);//入力されたチーム情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_TEA_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TEA, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException{

        //idを条件にチームデータを取得する
        TeamView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TEA_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        if (tv == null) {
            //該当のチームデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.TEAM, tv);
        }

        //編集画面を表示
        forward(ForwardConst.FW_TEA_EDIT);
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件にチームデータを取得する
            TeamView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TEA_ID)));

            //入力されたチーム内容を設定する
            tv.setName(getRequestParam(AttributeConst.TEA_NAME));

            //チームデータを更新する
            List<String> errors = service.update(tv);


            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TEAM, tv); //入力されたチーム情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_TEA_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TEA, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * チームを削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件にチームデータを取得する
            TeamView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TEA_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (tv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //チームをModel型へ変換
                    Team t = TeamConverter.toModel(tv);

                    service.destroy(t.getId());
                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_HOME, ForwardConst.CMD_SHOW);

            }
       }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
        //idを条件に募集データを取得する
        TeamView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TEA_ID)));

        if (tv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TEAM, tv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_TEA_SHOW);

    }

}
