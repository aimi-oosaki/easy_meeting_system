package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.TaskConverter;
import actions.views.TaskView;
import actions.views.TeamConverter;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Task;
import models.Team;
import services.TaskService;

/**
 * プロジェクトに関わる処理を行うActionクラス
 *
 */
public class TaskAction extends ActionBase{
    private TaskService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new TaskService();

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
      //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //DBからログイン中の従業員が所属するチーム情報を取得
        Team t = service.findOneTeam(ev.getTeam().getId());

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<TaskView> tasks = service.getPerPageByTeam(page, t);

        putRequestScope(AttributeConst.EMPLOYEE, ev); //取得した従業員データ
        putRequestScope(AttributeConst.TASKS, tasks); //取得したプロジェクトデータ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TAS_INDEX);
    }


    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.TASK, new TaskView()); //空のプロジェクトインスタンス

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //DBからログイン中の従業員が所属するチーム情報を取得
        Team t = service.findOneTeam(ev.getTeam().getId());

        //DBから同じチームのメンバー情報を取得
        List<EmployeeView> employees = service.getTeamMember(t);
        putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員（チームメンバー）データ

        //新規登録画面を表示
        forward(ForwardConst.FW_TAS_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if(checkToken()) {
            //入力内容から従業員データを取得
            EmployeeView ev = service.findOneEmployee(toNumber(getRequestParam(AttributeConst.EMPLOYEE)));

            //DBからログイン中の従業員が所属するチーム情報を取得
            Team t = service.findOneTeam(ev.getTeam().getId());

            TaskView tv = new TaskView(
                    null,
                    TeamConverter.toView(t),
                    ev,
                    getRequestParam(AttributeConst.TAS_TITLE), //タイトル
                    getRequestParam(AttributeConst.TAS_PROGRESS),//進捗
                    getRequestParam(AttributeConst.TAS_PROBLEM),//問題点
                    getRequestParam(AttributeConst.TAS_SOLUTION),//解決策
                    toLocalDate(getRequestParam(AttributeConst.TAS_DUEDATE)),//プロジェクト終了日
                    0);

            //プロジェクト情報登録
            List<String> errors = service.create(tv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TASK, tv);//入力されたプロジェクト情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_TAS_NEW);

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
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException{
        //idを条件にプロジェクトデータを取得する
        TaskView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TAS_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //DBからログイン中の従業員が所属するチーム情報を取得
        Team t = service.findOneTeam(ev.getTeam().getId());

        //DBから同じチームのメンバー情報を取得
        List<EmployeeView> employees = service.getTeamMember(t);
        putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員データ

        if (tv == null) {
            //該当のプロジェクトデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.TASK, tv);
            putRequestScope(AttributeConst.EMPLOYEE, ev);
        }

        //編集画面を表示
        forward(ForwardConst.FW_TAS_EDIT);
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件にプロジェクトデータを取得する
            TaskView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TAS_ID)));

            // 入力内容から従業員データを取得
            EmployeeView ev = service.findOneEmployee(toNumber(getRequestParam(AttributeConst.EMPLOYEE)));

            //入力されたプロジェクト内容を設定する
            tv.setEmployee(ev);
            tv.setDue_date(toLocalDate(getRequestParam(AttributeConst.TAS_DUEDATE)));
            tv.setTitle(getRequestParam(AttributeConst.TAS_TITLE));
            tv.setProgress(getRequestParam(AttributeConst.TAS_PROGRESS));
            tv.setProblem(getRequestParam(AttributeConst.TAS_PROBLEM));
            tv.setSolution(getRequestParam(AttributeConst.TAS_SOLUTION));
            tv.setStatus(toNumber(getRequestParam(AttributeConst.TAS_STATUS)));

            //プロジェクトデータを更新する
            List<String> errors = service.update(tv);


            if (errors.size() > 0) {
                //更新中にエラーが発生した場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TASK, tv); //入力されたプロジェクト情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト
                //編集画面を再表示
                forward(ForwardConst.FW_TAS_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TAS, ForwardConst.CMD_INDEX);

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

            //idを条件にプロジェクトデータを論理削除する
            Task t = TaskConverter.toModel(service.findOne(toNumber(getRequestParam(AttributeConst.TAS_ID))));
            service.destroy(t);

            //セッションに削除完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_DELETED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_TAS, ForwardConst.CMD_INDEX);
        }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
        //idを条件にプロジェクトデータを取得する
        TaskView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TAS_ID)));

        if (tv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.TASK, tv); //取得したプロジェクト情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //詳細画面を表示
        forward(ForwardConst.FW_TAS_SHOW);
    }
}
