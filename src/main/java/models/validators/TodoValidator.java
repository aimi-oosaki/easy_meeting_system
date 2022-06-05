package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import actions.views.TeamView;
import actions.views.TodoView;
import constants.MessageConst;
import services.TodoService;

/**
 * 要望インスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class TodoValidator {
    /**
     * 要望インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param wv WantViewのインスタンス
     * @param codeDuplicateCheckFlag 社員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
     public static List<String> validate(TodoService service, TodoView tv){
         List<String> errors = new ArrayList<String>();

         //チームのチェック
         String teamError = validateTeam(tv.getTeam());
         if(!teamError.equals("")) {
             errors.add(teamError);
         }

         //誰がのチェック
         String employeeError = validateEmployee(tv.getEmployee());
         if(!employeeError.equals("")) {
             errors.add(employeeError);
         }

         //タイトルのチェック
         String whatError = validateWhat(tv.getWhat());
         if(!whatError.equals("")) {
             errors.add(whatError);
         }

         String deadlineError = validateDeadline(tv.getDeadline());
         if(!deadlineError.equals("")){
             errors.add(deadlineError);
         }

         return errors;
     }

     /**
      * 誰がチームに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param ev 担当者
      * @return エラーメッセージ
      */
     private static String validateEmployee(EmployeeView ev) {

         if(ev == null || ev.equals("")) {
             return MessageConst.E_NOWHO.getMessage();
         }
         return "";
     }

     /**
      * チームに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param tv チーム
      * @return エラーメッセージ
      */
     private static String validateTeam(TeamView tv) {

         if(tv == null || tv.equals("")) {
             return MessageConst.E_NOTEAM.getMessage();
         }
         return "";
     }

     /**
      * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param what やること
      * @return エラーメッセージ
      */
     private static String validateWhat(String what) {

         if(what == null || what.equals("")) {
             return MessageConst.E_NOWHAT.getMessage();
         }
         return "";
     }

     /**
      * 日付に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param deadline 期限
      * @return エラーメッセージ
      */
     private static String validateDeadline(LocalDate deadline) {
         if(deadline == null || deadline.equals("")) {
             return MessageConst.E_NODEADLINE.getMessage();
         }
         return "";
     }

}
