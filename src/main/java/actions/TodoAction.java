package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TeamConverter;
import actions.views.TeamView;
import actions.views.TodoConverter;
import actions.views.TodoView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Meeting;
import models.Team;
import models.Todo;
import services.TodoService;

/**
 * TODOに関わる処理を行うActionクラス
 *
 */
public class TodoAction extends ActionBase{
    private TodoService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new TodoService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 自分のチームのTodoを一覧画面に表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index() throws ServletException, IOException{
        //セッションからログイン中の従業員のチーム情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Team t = service.findOneTeam(ev.getTeam().getId());

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<TodoView> todos = service.getTodos(t);

        putRequestScope(AttributeConst.TODOS, todos); //取得したTODOデータ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_TOD_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        //idを条件に会議データを取得する
        MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));

        putRequestScope(AttributeConst.MEETING, mv); //取得した会議情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.TODO, new TodoView()); //空のTODOインスタンス

        //チームメンバーを取得する
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Team t = service.findOneTeam(ev.getTeam().getId());
        List<EmployeeView> employees = service.getEmployee(t);
        putRequestScope(AttributeConst.EMPLOYEES, employees); //取得した従業員データ


        //新規登録画面を表示
        forward(ForwardConst.FW_TOD_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if(checkToken()) {
            //idを条件に会議データを取得する
            MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));

            //リクエストスコープからTODO情報を取得
            String[] whos = request.getParameterValues("todo_employee[]");
            String[] whats = request.getParameterValues("todo_what[]");
            String[] deadlines = request.getParameterValues("todo_deadline[]");
            String[] cons = request.getParameterValues("todo_consequence[]");

            // ログインしている従業員からチーム情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
            TeamView tv = TeamConverter.toView(service.findOneTeam(ev.getTeam().getId()));

            //エラー用リスト
            List<String> errors = new ArrayList<String>();

            //リクエストスコープからTODOデータを取得
            String[] ids = getRequestParams(AttributeConst.TODO_ID);

            List<TodoView> todos = new ArrayList<TodoView>();
            for(int i = 0; i < whos.length; i++) {
                TodoView tdv = new TodoView(
                        null,
                        service.findOneEmployee(toNumber(whos[i])),
                        mv,
                        tv,
                        whats[i],
                        toLocalDate(deadlines[i]),
                        0,
                        "あ");

                //TODO情報登録
                errors = service.create(tdv);
                todos.add(tdv);
            }

            if (errors.size() > 0) {
                //登録中にエラーがあった場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TODOS, todos);//入力されたTODO情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_TOD_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOD, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException{

        //ログイン中の従業員のチームメンバーを取得する
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Team t = service.findOneTeam(ev.getTeam().getId());
        List<EmployeeView> employees = service.getEmployee(t);

        //該当の会議を取得する
        Meeting m = MeetingConverter.toModel(service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID))));

        //会議idを条件にTODOデータを取得する
        TodoView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TODO_ID)));

        if (tv == null) {
            //該当のTODOデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.TODO, tv);
            putRequestScope(AttributeConst.EMPLOYEES, employees);
        }

        //編集画面を表示
        forward(ForwardConst.FW_TOD_EDIT);
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //リクエストスコープからTODOデータを取得
            Integer id = toNumber(getRequestParam(AttributeConst.TODO_ID));
            TodoView tv = service.findOne(id);

            //リクエストスコープから従業員IDを取得し、該当するEmployeeViewを検索
            String emp_id = getRequestParam(AttributeConst.TODO_EMP);
            EmployeeView ev = service.findOneEmployee(toNumber(emp_id));

            //TODOを保存する
            tv.setEmployee(ev);
            tv.setWhat(getRequestParam(AttributeConst.TODO_WHAT));
            tv.setDeadline(toLocalDate(getRequestParam(AttributeConst.TODO_DEADLINE)));
            tv.setStatus(toNumber(getRequestParam(AttributeConst.TODO_STATUS)));
            tv.setConsequence(getRequestParam(AttributeConst.TODO_CON));

            //TODOデータを更新する
            List<String> errors = service.update(tv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合
                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.TODO, tv); //入力されたTODO情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_TOD_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_TOD, ForwardConst.CMD_INDEX);
            }
        }
    }



    /**
     * 募集を削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件にTODOデータを取得する
            TodoView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TODO_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (tv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //TODOをModel型へ変換
                    Todo t = TodoConverter.toModel(tv);

                    service.destroy(t);
                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_TOD, ForwardConst.CMD_INDEX);

            }
       }
    }

    /**
     * 詳細画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException {
        //idを条件にTODOデータを取得する
        TodoView tv = service.findOne(toNumber(getRequestParam(AttributeConst.TODO_ID)));

        if (tv == null) {
            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }
        putRequestScope(AttributeConst.TODO, tv); //取得したTODO情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //詳細画面を表示
        forward(ForwardConst.FW_TOD_SHOW);

    }

}
