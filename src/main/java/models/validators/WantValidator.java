package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.WantView;
import constants.MessageConst;
import services.WantService;

/**
 * 募集インスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class WantValidator {
    /**
     * 要望インスタンスの各項目についてバリデーションを行う
     * @param wantService 呼び出し元Serviceクラスのインスタンス
     * @param wv WantViewのインスタンス
     * @return エラーのリスト
     */
     public static List<String> validate(WantService wantService, WantView wv){
         List<String> errors = new ArrayList<String>();

         //タイトルのチェック
         String titleError = validateTitle(wv.getTitle());
         if(!titleError.equals("")) {
             errors.add(titleError);
         }

         //内容のチェック
         String contentError = validateContent(wv.getContent());
         if(!contentError.equals("")){
             errors.add(contentError);
         }

         //募集期限のチェック
         String dueDateError = validateDueDate(wv.getDue_date());
         if(!dueDateError.equals("")) {
             errors.add(dueDateError);
         }

         return errors;
     }

     /**
      * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param title タイトル
      * @return エラーメッセージ
      */
     private static String validateTitle(String title) {

         if(title == null || title.equals("")) {
             return MessageConst.E_NOTITLE.getMessage();
         }
         return "";
     }

     /**
      * 内容に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param content 内容
      * @return エラーメッセージ
      */
     private static String validateContent(String content) {
         if(content == null || content.equals("")){
             return MessageConst.E_NOCONTENT.getMessage();
         }

         return "";
     }

     /**
      * 日付に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param dueDate 募集期限
      * @return エラーメッセージ
      */
     private static String validateDueDate(LocalDate dueDate) {
         if(dueDate == null || dueDate.equals("")) {
             return MessageConst.E_NODUEDATE.getMessage();
         }
         return "";
     }

}
