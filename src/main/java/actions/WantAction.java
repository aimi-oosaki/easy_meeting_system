package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.IdeaConverter;
import actions.views.IdeaView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.TeamView;
import actions.views.WantConverter;
import actions.views.WantView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Idea;
import models.Meeting;
import models.Want;
import services.WantService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class WantAction extends ActionBase{
    private WantService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new WantService();

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
        List<WantView> wants = service.getPerPage(page);

        putRequestScope(AttributeConst.WANTS, wants); //取得した募集データ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_WAN_INDEX);
    }

    /**
     * 該当の会議の募集一覧を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void index_meeting() throws ServletException, IOException{
        //該当の会議のIDからModelを取得
        int meeting_id = toNumber(getRequestParam(AttributeConst.MET_ID));
        Meeting m = MeetingConverter.toModel(service.findOneMeeting(meeting_id));

        //指定されたページ数の一覧画面に表示するデータを取得
        int page = getPage();
        List<WantView> wants = service.getPerPageByMeeting(page, m);

        putRequestScope(AttributeConst.WANTS, wants); //取得した募集データ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_WAN_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.WANT, new WantView()); //空の募集インスタンス

        //会議データを取得する
        List<MeetingView> meetings = service.getMeeting();

        putRequestScope(AttributeConst.MEETINGS, meetings); //全会議データ

        //新規登録画面を表示
        forward(ForwardConst.FW_WAN_NEW);
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

            //DBからログイン中の従業員が所属するチーム情報を取得
            TeamView tv = service.findOneTeam(ev.getTeam().getId());

            //リクエストスコープからミーティング情報を取得
            int meeting_id = toNumber(getRequestParam(AttributeConst.WAN_MEETING));
            MeetingView mv = service.findOneMeeting(meeting_id);

            WantView wv = new WantView(
                    null,
                    mv,
                    tv,
                    getRequestParam(AttributeConst.WAN_TITLE),
                    getRequestParam(AttributeConst.WAN_CONTENT),
                    toLocalDate(getRequestParam(AttributeConst.WAN_DUEDATE))
                    );

            //募集情報登録
            List<String> errors = service.create(wv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.WANT, wv);//入力された日報情報
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

        //idを条件に募集データを取得する
        WantView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WAN_ID)));

        //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

        //DBから全チームを取得
        List<MeetingView> meetings = service.getMeeting();

        if (wv == null) {
            //該当の募集データが存在しない、または
            //ログインしている従業員が募集の作成者でない場合はエラー画面を表示 ★後で追加する必要ある
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {

            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.WANT, wv);
            putRequestScope(AttributeConst.EMPLOYEE, ev);
            putRequestScope(AttributeConst.MEETINGS, meetings);
        }

        //編集画面を表示
        forward(ForwardConst.FW_WAN_EDIT);
    }

    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に募集データを取得する
            WantView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WAN_ID)));

            //idを条件に会議データを取得する
            MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.WAN_MEETING)));

            //入力された募集内容を設定する
            wv.setDue_date(toLocalDate(getRequestParam(AttributeConst.WAN_DUEDATE)));
            wv.setMeeting(mv);
            wv.setTitle(getRequestParam(AttributeConst.WAN_TITLE));
            wv.setContent(getRequestParam(AttributeConst.WAN_CONTENT));

            //募集データを更新する
            List<String> errors = service.update(wv);


            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.WANT, wv); //入力された日報情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_WAN_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_WAN, ForwardConst.CMD_INDEX);

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
            //idを条件に募集データを取得する
            WantView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WAN_ID)));

            //募集に該当するアイデアデータを取得する
            List<IdeaView> iv = service.getIdeas(WantConverter.toModel(wv));

            if(iv != null) {
                //データが取得できた場合のみ処理を実行
                for(int j = 0; j < iv.size(); j++) {
                    Idea id = IdeaConverter.toModel(iv.get(j));
                    service.destroyIdea(id);
                }
            }

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (wv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //募集をModel型へ変換
                    Want w = WantConverter.toModel(wv);

                    service.destroy(w);
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
        WantView wv = service.findOne(toNumber(getRequestParam(AttributeConst.WAN_ID)));

        //募集idを条件に、回答を取得する
        List<IdeaView> ideas = service.getIdeas(WantConverter.toModel(wv));

        if (wv == null) {

            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
            return;
        }

        putRequestScope(AttributeConst.WANT, wv); //取得した募集情報
        putRequestScope(AttributeConst.IDEAS, ideas); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_WAN_SHOW);

    }

}
