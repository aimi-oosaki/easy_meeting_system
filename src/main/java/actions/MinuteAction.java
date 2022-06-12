package actions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.AgendaView;
import actions.views.EmployeeView;
import actions.views.MeetingConverter;
import actions.views.MeetingView;
import actions.views.MinuteConverter;
import actions.views.MinuteView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import models.Meeting;
import models.Minute;
import services.MinuteService;

/**
 * 従業員に関わる処理を行うActionクラス
 *
 */
public class MinuteAction extends ActionBase{
    private MinuteService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new MinuteService();

        //メソッドを実行
        invoke();

        service.close();
    }

    /**
     * 新規登録画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void entryNew() throws ServletException, IOException {
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
        putRequestScope(AttributeConst.MINUTE, new MinuteView()); //空の募集インスタンス

        //リクエストスコープからミーティング情報を取得し、その会議データを取得
        int meeting_id = toNumber(getRequestParam(AttributeConst.MET_ID));
        putRequestScope(AttributeConst.MET_ID, meeting_id); //該当の会議データ

       //セッションからログイン中の従業員情報を取得
        EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);
        putRequestScope(AttributeConst.EMPLOYEE, ev); //従業員データ

        //該当の会議の議題のリストを取得
        Meeting m = MeetingConverter.toModel(service.findOneMeeting(meeting_id));
        List<AgendaView> agendas= service.getAgendas(m);
        putRequestScope(AttributeConst.AGENDAS, agendas); //該当の会議データ
        int list_size = agendas.size();
        putRequestScope(AttributeConst.AGENDA_LIST_SIZE, list_size);

        //新規登録画面を表示
        forward(ForwardConst.FW_MIN_NEW);
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

            //リクエストスコープから議題IDを取得し、SUMMARYを保存
            List<String> agenda_ids = new ArrayList<String>();

            for(int i = 0; i < 10; i ++) {
                if(request.getParameter("agenda_id[" + i + "]") != null)
                agenda_ids.add(request.getParameter("agenda_id[" + i + "]"));
            }

            List<String> agenda_summaries = new ArrayList<String>();
            for(int i = 0; i < 10; i ++) {
                if(request.getParameter("agenda_summary[" + i + "]") != null)
                    agenda_summaries.add(request.getParameter("agenda_summary[" + i + "]"));
            }

            //議事へのSUMMARYを登録する
            for(int i = 0; i < agenda_ids.size(); i++) {
                AgendaView av = service.findOneAgenda(toNumber(agenda_ids.get(i)));
                av.setSummary(agenda_summaries.get(i));
                //議事データを更新する
                service.updateAgenda(av);
            }

            //議事録を作成
            MinuteView miv = new MinuteView(
                    null,
                    mv,
                    ev,
                    getRequestParam(AttributeConst.MIN_ATTENDEES),
                    getRequestParam(AttributeConst.MIN_DECIDED),
                    getRequestParam(AttributeConst.MIN_PENDING)
                    );

            //募集情報登録
            service.create(miv);

            //セッションに登録完了のフラッシュメッセージを設定
            putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

            //一覧画面にリダイレクト
            redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);

        }
    }

    public void edit() throws ServletException, IOException{

        //idを条件に議事録データを取得する
        MinuteView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MIN_ID)));

        //ミーティング
        int meeting_id = mv.getMeeting().getId();
        MeetingView mev = service.findOneMeeting(meeting_id);

        //該当の会議の議題のリストを取得
        List<AgendaView> agendas= service.getAgendas(MeetingConverter.toModel(mev));
        int list_size = agendas.size(); //議題の個数

        if (mv == null) {
            //該当の議事録データが存在しない
            forward(ForwardConst.FW_ERR_UNKNOWN);

        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.MINUTE, mv);
            putRequestScope(AttributeConst.MEETING, mev);
            putRequestScope(AttributeConst.AGENDAS, agendas); //該当の会議データ
            putRequestScope(AttributeConst.AGENDA_LIST_SIZE, list_size);

        }

        //編集画面を表示
        forward(ForwardConst.FW_MIN_EDIT);
    }

    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に議事録データを取得する
            MinuteView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MIN_ID)));

            //リクエストスコープから議題IDを取得し、SUMMARYを保存
            List<String> agenda_ids = new ArrayList<String>();

            for(int i = 0; i < 10; i ++) {
                if(request.getParameter("agenda_id[" + i + "]") != null)
                agenda_ids.add(request.getParameter("agenda_id[" + i + "]"));
            }

            List<String> agenda_summaries = new ArrayList<String>();
            for(int i = 0; i < 10; i ++) {
                if(request.getParameter("agenda_summary[" + i + "]") != null)
                    agenda_summaries.add(request.getParameter("agenda_summary[" + i + "]"));
            }

            //議事へのSUMMARYを登録する
            for(int i = 0; i < agenda_ids.size(); i++) {
                AgendaView av = service.findOneAgenda(toNumber(agenda_ids.get(i)));
                av.setSummary(agenda_summaries.get(i));
                //議事データを更新する
                service.updateAgenda(av);
            }

            //入力された募集内容を設定する
            mv.setAttendees(getRequestParam(AttributeConst.MIN_ATTENDEES));
            mv.setDecided(getRequestParam(AttributeConst.MIN_DECIDED));
            mv.setPending(getRequestParam(AttributeConst.MIN_PENDING));

            //募集データを更新する
            service.update(mv);


//            if (errors.size() > 0) {
//                //idを条件に会議データを取得する
//                int meeting_id = toNumber(getRequestParam(AttributeConst.MET_ID));
//                MeetingView mev = service.findOneMeeting(meeting_id);
//
//                //該当の会議の議題のリストを取得
//                List<AgendaView> agendas= service.getAgendas(MeetingConverter.toModel(mev));
//                int list_size = agendas.size(); //議題の個数
//
//                //更新中にエラーが発生した場合
//                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
//                putRequestScope(AttributeConst.MINUTE, mv); //入力された日報情報
//                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト
//                putRequestScope(AttributeConst.MEETING, mev);
//                putRequestScope(AttributeConst.AGENDAS, agendas); //該当の会議データ
//                putRequestScope(AttributeConst.AGENDA_LIST_SIZE, list_size);
//
//                //編集画面を再表示
//                forward(ForwardConst.FW_MIN_EDIT);
//            } else {
                //更新中にエラーがなかった場合

                //セッションに更新完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_UPDATED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_MEE, ForwardConst.CMD_INDEX);

//            }
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
            MinuteView mv = service.findOne(toNumber(getRequestParam(AttributeConst.MIN_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (mv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //募集をModel型へ変換
                    Minute m = MinuteConverter.toModel(mv);

                    service.destroy(m);
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
        //idを条件に会議データを取得する
        MeetingView mv = service.findOneMeeting(toNumber(getRequestParam(AttributeConst.MET_ID)));

        if (mv == null) {
            //データが取得できなかった場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_NOMINUTE);
            return;
        }

        //MeetingViewからMinuteViewを取得する
        MinuteView minv = service.findOneByMeeting(MeetingConverter.toModel(mv));

        //MeetingViewから議題を取得する
        List<AgendaView> agendas = service.getAgendas(MeetingConverter.toModel(mv));

        putRequestScope(AttributeConst.AGENDAS, agendas);
        putRequestScope(AttributeConst.MINUTE, minv);
        putRequestScope(AttributeConst.MEETING, mv); //取得した募集情報
        putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン★追記

        //詳細画面を表示
        forward(ForwardConst.FW_MIN_SHOW);

    }

}
