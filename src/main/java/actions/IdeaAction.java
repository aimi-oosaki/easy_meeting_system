package actions;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.EmployeeView;
import actions.views.IdeaConverter;
import actions.views.IdeaView;
import actions.views.WantView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.MessageConst;
import models.Idea;
import services.IdeaService;

/**
 * アイデアに関わる処理を行うActionクラス
 *
 */
public class IdeaAction extends ActionBase{
    private IdeaService service;

    /**
     * メソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException{
        service = new IdeaService();

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
        putRequestScope(AttributeConst.IDEA, new IdeaView()); //空の募集インスタンス

        //募集データを取得する
        WantView wv = service.findOneProject(toNumber(getRequestParam(AttributeConst.WAN_ID)));

        putRequestScope(AttributeConst.WANT, wv); //募集データ

        //新規登録画面を表示
        forward(ForwardConst.FW_IDE_NEW);
    }

    /**
     * 新規登録を行う
     * @throws ServletException
     * @throws IOException
     */
    public void create() throws ServletException, IOException {
        if(checkToken()) {
            //リクエストスコープから募集情報を取得
            WantView wv = service.findOneProject(toNumber(getRequestParam(AttributeConst.WAN_ID)));

           //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            IdeaView iv = new IdeaView(
                    null,
                    wv,
                    ev,
                    getRequestParam(AttributeConst.IDE_CONTENT));

            //アイデア情報登録
            List<String> errors = service.create(iv);

            if (errors.size() > 0) {
                //登録中にエラーがあった場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.IDEA, iv);//入力されたアイデア情報
                putRequestScope(AttributeConst.ERR, errors);//エラーのリスト

                //新規登録画面を再表示
                forward(ForwardConst.FW_IDE_NEW);

            } else {
                //登録中にエラーがなかった場合

                //セッションに登録完了のフラッシュメッセージを設定
                putSessionScope(AttributeConst.FLUSH, MessageConst.I_REGISTERED.getMessage());

                //一覧画面にリダイレクト
                redirect(ForwardConst.ACT_WAN, ForwardConst.CMD_INDEX);
            }
        }
    }

    /**
     * 編集画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void edit() throws ServletException, IOException{
        //idを条件にアイデアデータを取得する
        IdeaView iv = service.findOne(toNumber(getRequestParam(AttributeConst.IDE_ID)));

        if (iv == null) {
            //該当のアイデアデータが存在しない場合はエラー画面を表示
            forward(ForwardConst.FW_ERR_UNKNOWN);
        } else {
            putRequestScope(AttributeConst.TOKEN, getTokenId());
            putRequestScope(AttributeConst.IDEA, iv);
        }

        //編集画面を表示
        forward(ForwardConst.FW_IDE_EDIT);
    }

    /**
     * 更新を行う
     * @throws ServletException
     * @throws IOException
     */
    public void update() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件にアイデアデータを取得する
            IdeaView iv = service.findOne(toNumber(getRequestParam(AttributeConst.IDE_ID)));

            //入力されたアイデア内容を設定する
            iv.setContent(getRequestParam(AttributeConst.IDE_CONTENT));

            //アイデアデータを更新する
            List<String> errors = service.update(iv);

            if (errors.size() > 0) {
                //更新中にエラーが発生した場合

                putRequestScope(AttributeConst.TOKEN, getTokenId()); //CSRF対策用トークン
                putRequestScope(AttributeConst.IDEA, iv); //入力されたアイデア情報
                putRequestScope(AttributeConst.ERR, errors); //エラーのリスト

                //編集画面を再表示
                forward(ForwardConst.FW_IDE_EDIT);
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
     * アイデアを削除する
     * @throws ServletException
     * @throws IOException
     */
    public void destroy() throws ServletException, IOException {
        //CSRF対策 tokenのチェック
        if(checkToken()) {
            //idを条件に募集データを取得する
            IdeaView iv = service.findOne(toNumber(getRequestParam(AttributeConst.IDE_ID)));

            //セッションからログイン中の従業員情報を取得
            EmployeeView ev = (EmployeeView) getSessionScope(AttributeConst.LOGIN_EMP);

            if (iv == null && ev == null) {
                //データが取得できなかった場合はエラー画面を表示
                forward(ForwardConst.FW_ERR_UNKNOWN);
                return;

            } else {
                    //アイデアをModel型へ変換
                    Idea i = IdeaConverter.toModel(iv);

                    service.destroy(i);
                    //トップページにリダイレクト
                    redirect(ForwardConst.ACT_HOME, ForwardConst.CMD_SHOW);
            }
       }
    }
}
