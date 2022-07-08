package actions;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import javax.servlet.ServletException;

import actions.views.WantView;
import constants.AttributeConst;
import constants.ForwardConst;
import constants.JpaConst;
import services.WantService;

/**
 * ホーム画面に関わる処理を行うActionクラス
 *
 */
public class HomeAction extends ActionBase{
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
     * ホーム画面を表示する
     * @throws ServletException
     * @throws IOException
     */
    public void show() throws ServletException, IOException{
        //指定されたページ数の一覧画面に表示する募集データを取得
        int page = getPage();

        //今日の日付を取得
        LocalDate today = LocalDate.now();

        //募集情報を取得
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

        //ホーム画面を表示
        forward(ForwardConst.FW_TOP_SHOW);
    }

}
