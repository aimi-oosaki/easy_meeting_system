package actions;

import java.io.IOException;

import javax.servlet.ServletException;

import constants.ForwardConst;
import services.WantService;

/**
 * トップページに関する処理を行うActionクラス
 *
 */
public class TopAction extends ActionBase {

    private WantService service; //追記

    /**
     * indexメソッドを実行する
     */
    @Override
    public void process() throws ServletException, IOException {

        service = new WantService(); //追記

        //メソッドを実行
        invoke();

        service.close(); //追記

    }

    /**
     * トップ画面を表示する
     */
    public void index() throws ServletException, IOException {

        //トップ画面を表示
        forward(ForwardConst.FW_TOP_INDEX);
    }

}