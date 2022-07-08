package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AgendaConverter;
import actions.views.AgendaView;
import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import constants.MessageConst;
import models.Agenda;
import models.Meeting;
import services.AgendaService;

/**
 * 議題に関わる処理を行うActionクラス
 *
 */
public class AgendaAction extends ActionBase{
    private AgendaService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new AgendaService();

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

        //リクエストスコープから会議情報を取得する
        MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));
        Meeting m = MeetingConverter.toModel(mv);

        List<AgendaView> agendas = service.getPerPage(page, m);

        putRequestScope(AttributeConst.MEETING, mv); //取得した会議データ
        putRequestScope(AttributeConst.AGENDAS, agendas); //取得した議題データ
        putRequestScope(AttributeConst.PAGE, page); //ページ数
        putRequestScope(AttributeConst.MAX_ROW, JpaConst.ROW_PER_PAGE); //1ページに表示するレコードの数

        //セッションにフラッシュメッセージが設定されている場合はリクエストスコープに移し替え、セッションからは削除する
        String flush = getSessionScope(AttributeConst.FLUSH);
        if (flush != null) {
            putRequestScope(AttributeConst.FLUSH, flush);
            removeSessionScope(AttributeConst.FLUSH);
        }

        //一覧画面を表示
        forward(ForwardConst.FW_AGE_INDEX);
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.AGENDA, new AgendaView()); //空の募集インスタンス

        MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));
        putRequestScope(AttributeConst.MEETING, mv); //取得した会議情報

        //新規登録画面を表示
        forward(ForwardConst.FW_AGE_NEW);
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

            //リクエストスコープからミーティング情報を取得
            int meeting_id = toNumber(getRequestParam(AttributeConst.MET_ID));
            MeetingView mv = service.findOneMeeting(meeting_id);

            //リクエストスコープからタイトルを取得
            String[] titles = request.getParameterValues("agenda_title[]");

            List<String> errors = null;
            List<AgendaView> avs = new ArrayList<>();

            //議題を保存する
            for(int i = 0; i < titles.length; i++) {
                if(titles[i] != null) {
                    AgendaView av = new AgendaView(
                            null,
                            mv,
                            titles[i],
                            "."
                            );
                  //募集情報登録
                  errors = service.create(av);
                  avs.add(av);
                }
            }

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.AGENDAS, avs);//入力された議題情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_AGE_NEW);

            } else {
                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException{

        //idを条件に議題データを取得する
        AgendaView av = service.findOne(toNumber(getRequestParam(AttributeConst.AGE_ID)));

        //リクエストスコープから会議情報を取得する
        int page = getPage();
        MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));
        Meeting m = MeetingConverter.toModel(mv);

        if (av == null) {
            //該当の議題データが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            String _token = getTokenId();
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.AGENDA, av); //取得した議題データ;
            putRequestScope(AttributeConst.MEETING, mv); //取得した会議データ
        }

        //編集画面を表示
        forward(ForwardConst.FW_AGE_EDIT);
    }

    /**
     * 編集を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に会議データを取得する
            MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));

            //リクエストスコープから議題データを取得
            String[] ids = request.getParameterValues("agenda_id[]");
            List<AgendaView> avs = new ArrayList<AgendaView>();
            for(int i = 0; i < ids.length; i++) {
                avs.add(service.findOne(toNumber(ids[i])));
            }

            //リクエストスコープからタイトルを取得
            String[] titles = request.getParameterValues("agenda_title[]");

            List<String> errors = null;

            //議題を保存する
            for(int i = 0; i < titles.length; i++) {
                if(titles[i] != null) {
                    //入力された議題内容を設定する
                    avs.get(i).setTitle(titles[i]);

                    //議題データを更新する
                    errors = service.update(avs.get(i));
                }
            }

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.AGENDAS, avs); //入力された議題情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_AGE_EDIT);
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
     * 話し合った内容の新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void updateSummary() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に会議データを取得する
            MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));

            //リクエストスコープから議題のIDを取得
            String[] ids = getRequestParams(AttributeConst.AGE_ID);

            //リクエストスコープから議題データを取得
            String[] sums = getRequestParams(AttributeConst.AGE_SUMMARY);

            List<AgendaView> avs = new ArrayList<AgendaView>();
            for(int i = 0; i < sums.length; i++) {
              //IDから議題データを取得
                avs.add(service.findOne(toNumber(ids[i])));
            }

            List<String> errors = null;

            //議題を保存する
            for(int i = 0; i < sums.length; i++) {
                //入力された募集内容を設定する★★★ここわからない
                avs.set(i, null);

                //募集データを更新する
                errors = service.update(avs.get(i));
            }

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.AGENDAS, avs); //入力された議題情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_AGE_EDIT);
            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //会議一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);

            }
        }
    }

    /**
     * 議題を削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に議題データを取得する
            int agenda_id = toNumber(getRequestParam(AttributeConst.AGE_ID));
            AgendaView av = service.findOne(toNumber(getRequestParam(AttributeConst.AGE_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (av == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //議題をModel型へ変換
                    Agenda a = AgendaConverter.toModel(av);

                    service.destroy(a);
                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_HOME, ForwardConst.CMD_SHOW);
            }
       }
    }

}
