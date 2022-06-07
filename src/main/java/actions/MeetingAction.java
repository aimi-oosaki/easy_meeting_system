package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AgendaView;
import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TeamView;
import actions.views.TodoView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Meeting;
import services.MeetingService;

/**
 * 会議に関わる処理を行うActionクラス
 *
 */
public class MeetingAction extends ActionBase{
    private MeetingService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new MeetingService();

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
        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<MeetingView> meetings = service.getPerPage(page);

        putRequestScope(AttributeConst.MEETINGS, meetings); //取得した募集データ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_MET_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Integer company_id = ev.getCompany_id();

        //DBから全チームを取得
        List<TeamView> teams = service.getTeam(company_id);

        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.MEETING, new MeetingView()); //空の募集インスタンス
        putRequestScope(AttributeConst.TEAMS, teams); //全チーム

        //新規登録画面を表示
        forward(ForwardConst.FW_MET_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if(checkToken()) {
            //リクエストスコープからチーム情報を取得
            int team_id = toNumber(getRequestParam(AttributeConst.MET_TEAM));
            TeamView tv = service.findOneTeam(team_id);

            MeetingView mv = new MeetingView(
                    null,
                    tv,
                    getRequestParam(AttributeConst.MET_NAME),
                    toLocalDate(getRequestParam(AttributeConst.MET_DATE)),
                    0);

            //募集情報登録
            List<String> errors = service.create(mv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.MEETING, mv);//入力された会議情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_WAN_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);
            }
        }
    }

    public void edit() throws ServletException, IOException{
        //idを条件に会議データを取得する
        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));

      //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        Integer company_id = ev.getCompany_id();

        //DBから全チームを取得
        List<TeamView> teams = service.getTeam(company_id);//DBから全チームを取得

        if (mv == null) {
            //該当の募集データが存在しない、または
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.TEAMS, teams);
            putRequestScope(AttributeConst.MEETING, mv);
        }

        //編集画面を表示
        forward(ForwardConst.FW_MET_EDIT);
    }

    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に募集データを取得する
            MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));

            //リクエストスコープからチーム情報を取得
            int team_id = toNumber(getRequestParam(AttributeConst.MET_TEAM));
            TeamView tv = service.findOneTeam(team_id);

            //入力された募集内容を設定する
            mv.setTeam(tv);
            mv.setName(getRequestParam(AttributeConst.MET_NAME));
            mv.setDate(toLocalDate(getRequestParam(AttributeConst.MET_DATE)));

            //募集データを更新する
            List<String> errors = service.update(mv);


            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.MEETING, mv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_MET_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 削除を行う
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に会議データを取得する
            MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            //会議に該当するTODOデータを取得する
            List<TodoView> tv = service.getTodos(MeetingConverter.toModel(mv));

//            if(tv != null) {
//                //データが取得できた場合のみTODOを削除
//                service.destroyTodo(tv);
//
//            }

            if (mv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //募集をModel型へ変換
                    Meeting m = MeetingConverter.toModel(mv);

                    service.destroy(m.getId());
                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_HOME, ForwardConst.CMD_SHOW);

            }
       }
    }

    /**
     * 問題解決会議画面1を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void prob_1() throws ServletException, IOException{
        //idを条件に会議データを取得する
        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));
        List<AgendaView> agendas = service.getAgendas(MeetingConverter.toModel(mv));

        if (mv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.AGENDAS, agendas); //取得した議題情報
        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン

        //詳細画面を表示
        forward(ForwardConst.FW_MET_PROB1);

    }

    /**
     * 問題解決会議画面2を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void prob_2() throws ServletException, IOException{
        //idを条件に会議データを取得する
        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));
        List<AgendaView> agendas = service.getAgendas(MeetingConverter.toModel(mv));

        if (mv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.AGENDAS, agendas); //取得した議題情報
        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_MET_PROB2);

    }

    /**
     * 問題解決会議画面3を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void prob_3() throws ServletException, IOException{
        //idを条件に会議データを取得する
        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));
        List<AgendaView> agendas = service.getAgendas(MeetingConverter.toModel(mv));

        if (mv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.AGENDAS, agendas); //取得した議題情報
        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_MET_PROB3);

    }

    /**
     * 進捗会議画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void select() throws ServletException, IOException{
        //idを条件に会議データを取得する
        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));

        if (mv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_MET_SELECT);

    }

    /**
     * 付箋画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void stickynote() throws ServletException, IOException{
        //idを条件に会議データを取得する
//        MeetingView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MET_ID)));
//
//        if (mv == null) {
//
//            //データが取得できなかった場合はエラー画面を表示
//            forward(ForwardConst.FW_ERR_UNKNOWN);
//            return;
//        }

//        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
//        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_STICKY_NOTE);

    }

}
