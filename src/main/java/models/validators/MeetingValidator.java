package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.MeetingView;
import constants.MessageConst;
import services.MeetingService;

/**
 * 会議インスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class MeetingValidator {
    /**
     * 会議インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param mv MeetingViewのインスタンス
     * @return エラーのリスト
     */
     public static List<String> validate(MeetingService service, MeetingView mv){
         List<String> errors = new ArrayList<String>();

         String nameError = validateName(mv.getName());
         if(!nameError.equals("")){
             errors.add(nameError);
         }

         String dateError = validateDate(mv.getDate());
         if(!dateError.equals("")) {
             errors.add(dateError);
         }

         return errors;
     }


     /**
      * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param content 内容
      * @return エラーメッセージ
      */
     private static String validateName(String name) {
         if(name == null || name.equals("")){
             return MessageConst.E_NONAME.getMessage();
         }

         return "";
     }

     /**
      * 日付に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param dueDate 日付
      * @return エラーメッセージ
      */
     private static String validateDate(LocalDate date) {
         if(date == null || date.equals("")) {
             return MessageConst.E_NODATE.getMessage();
         }
         return "";
     }

}
