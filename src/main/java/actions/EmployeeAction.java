package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TeamView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import constants.PropertyConst;
import services.EmployeeService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class EmployeeAction extends ActionBase {

    private EmployeeService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new EmployeeService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 一覧画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException {
      //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記
            //セッションからログイン中の従業員の会社情報を取得
            EmployeeView empLog = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            Integer company_id = empLog.getCompany_id();

            //指定されたページ数の一覧画面に表示するデータを取得
            int page = getPage();
            List<EmployeeView> employees = service.getPerPage(page, company_id);

            putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員データ
            putRequestScope(AttributeConst.PAGE, page); //ページ数
            putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

            //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
            String flush = getSessionScope(AttributeConst.FLUSH);
            if (flush != null) {
                putRequestScope(AttributeConst.FLUSH, flush);
                removeSessionScope(AttributeConst.FLUSH);
            }

            //一覧画面を表示
            forward(ForwardConst.FW_EMP_INDEX);
        } //追記

    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void signup() throws ServletException, IOException {
        //CSRF対策用トークンを設定
        String token = getTokenId();
        putRequestScope(AttributeConst.TOKEN, getTokenId());

        //セッションにフラッシュメッセージが登録されている場合はリクエストスコープに設定する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH,flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

         //新規登録画面を表示
        forward(ForwardConst.FW_EMP_SIGNUP);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void createEmp() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //パラメータの値を元に従業員情報のインスタンスを作成する
            TeamView tv = new TeamView(
                    null,
                    toNumber(getRequestParam(AttributeConst.EMP_COM)),
                    getRequestParam(AttributeConst.TEA_NAME),
                    0);

            TeamView tvNew =  service.createTeam(tv);

            Integer company_id = toNumber(getRequestParam(AttributeConst.EMP_COM));

            EmployeeView ev = new EmployeeView(
                    null,
                    getRequestParam(AttributeConst.EMP_CODE),
                    toNumber(getRequestParam(AttributeConst.EMP_COM)),//会社
                    tvNew, //チーム
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報登録
            List<String> errors = service.create(ev, pepper, company_id);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, ev); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EMP_SIGNUP);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_HOME, ForwardConst.CMD_SHOW);
            }

       }
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
      //管理者かどうかのチェック //追記
        if (checkAdmin()) {
            //セッションからログイン中の従業員情報を取得
            EmployeeView empLog = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            Integer company_id = empLog.getCompany_id();

            //チームデータを取得する
            List<TeamView> teams = service.getTeam(company_id);

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.EMPLOYEE, new EmployeeView()); //空の従業員インスタンス
            putRequestScope(AttributeConst.TEAMS, teams); //全チーム
            putRequestScope(AttributeConst.EMP_COM, company_id);

            //新規登録画面を表示
            forward(ForwardConst.FW_EMP_NEW);
        }
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {
            //セッションからログイン中の従業員情報を取得
            EmployeeView empLog = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //ログイン中の社員の会社idを取得する
            Integer company_id = empLog.getCompany_id();

            //リクエストスコープからチーム情報を取得
            int team_id = toNumber(getRequestParam(AttributeConst.EMP_TEAM));
            TeamView tv = service.findOneTeam(team_id);

            //パラメータの値を元に従業員情報のインスタンスを作成する
            EmployeeView ev = new EmployeeView(
                    null,
                    getRequestParam(AttributeConst.EMP_CODE),
                    company_id, //会社
                    tv, //チーム
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報登録
            List<String> errors = service.create(ev, pepper, company_id);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, ev); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_EMP_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }

        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
        //管理者かどうかのチェック //追記
//        if (checkAdmin()) { //追記
            //idを条件に従業員データを取得する
            EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));

            if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員情報
            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

            //詳細画面を表示
            forward(ForwardConst.FW_EMP_SHOW);
//        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
//    public void profile() throws ServletException, IOException {
//        //セッションからログイン中の従業員情報を取得
//        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
//
//            if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {
//
//                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
//                forward(ForwardConst.FW_ERR_UNKNOWN);
//                return;
//            }
//
//            putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員情報
//            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記
//
//            //プロフィール画面を表示
//            forward(ForwardConst.FW_EMP_SHOW);
//    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException {
      //管理者かどうかのチェック //追記
        if (checkAdmin()) { //追記
            //idを条件に該当の従業員データを取得する
            EmployeeView ev = service.findOne(toNumber(getRequestParam(AttributeConst.EMP_ID)));
            Integer company_id = ev.getCompany_id();

            //チームデータを取得する
            List<TeamView> teams = service.getTeam(company_id);

            if (ev == null || ev.getDeleteFlag() == AttributeConst.DEL_FLAG_TRUE.getIntegerValue()) {

                //データが取得できなかった、または論理削除されている場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;
            }

            putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
            putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員情報
            putRequestScope(AttributeConst.TEAMS, teams); //全チーム

            //編集画面を表示する
            forward(ForwardConst.FW_EMP_EDIT);
        } //追記

    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //セッションからログイン中の従業員情報を取得
            EmployeeView empLog = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //ログイン中の社員の会社情報を取得する
            Integer company_id = empLog.getCompany_id();

            //リクエストスコープからチーム情報を取得
            int team_id = toNumber(getRequestParam(AttributeConst.EMP_TEAM));
            TeamView tv = service.findOneTeam(team_id);

            //パラメータの値を元に従業員情報のインスタンスを作成する
            EmployeeView ev = new EmployeeView(
                    toNumber(getRequestParam(AttributeConst.EMP_ID)),
                    getRequestParam(AttributeConst.EMP_CODE),
                    company_id,
                    tv,
                    getRequestParam(AttributeConst.EMP_NAME),
                    getRequestParam(AttributeConst.EMP_PASS),
                    toNumber(getRequestParam(AttributeConst.EMP_ADMIN_FLG)),
                    AttributeConst.DEL_FLAG_FALSE.getIntegerValue());

            //アプリケーションスコープからpepper文字列を取得
            String pepper = getContextScope(PropertyConst.PEPPER);

            //従業員情報更新
            List<String> errors = service.update(ev, pepper, company_id);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.EMPLOYEE, ev); //入力された従業員情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_EMP_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 論理削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {

        //CSRF対策 tokenのチェック
        if (checkToken()) {

            //idを条件に従業員データを論理削除する
            service.destroy(toNumber(getRequestParam(AttributeConst.EMP_ID)));

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_EMP, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * ログイン中の従業員が管理者かどうかチェックし、管理者でなければエラー画面を表示
     * true: 管理者 false: 管理者ではない
     * @throws ServletException
     * @throws IOException
     */
     private boolean checkAdmin() throws ServletException, IOException{
         //セッションからログイン中の従業員情報を取得
         EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

         //管理者でなければエラー画面を表示
         if (ev.getAdminFlag() != AttributeConst.ROLE_ADMIN.getIntegerValue()) {
             forward(ForwardConst.FW_ERR_UNKNOWN);
             return false;
         } else {
             return true;
         }
     }



}