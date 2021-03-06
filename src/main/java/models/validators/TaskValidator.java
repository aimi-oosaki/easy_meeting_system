package models.validators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import actions.views.EmployeeView;
import actions.views.TaskView;
import constants.MessageConst;
import services.TaskService;

/**
 * プロジェクトインスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class TaskValidator {
    /**
     * プロジェクトインスタンスの各項目についてバリデーションを行う
     * @param taskService 呼び出し元Serviceクラスのインスタンス
     * @param tv TaskViewのインスタンス
     * @return エラーのリスト
     */
     public static List<String> validate(TaskService taskService, TaskView tv){
         List<String> errors = new ArrayList<String>();

         //プロジェクト名のチェック
         String titleError = validateTitle(tv.getTitle());
         if(!titleError.equals("")) {
             errors.add(titleError);
         }

         //担当者のチェック
         String employeeError = validateEmployee(tv.getEmployee());
         if(!employeeError.equals("")){
             errors.add(employeeError);
         }

         //期日のチェック
         String dueDateError = validateDueDate(tv.getDue_date());
         if(!dueDateError.equals("")) {
             errors.add(dueDateError);
         }

         return errors;
     }

     /**
      * タイトルに入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param title プロジェクト名
      * @return エラーメッセージ
      */
     private static String validateTitle(String title) {

         if(title == null || title.equals("")) {
             return MessageConst.E_NOTASK.getMessage();
         }
         return "";
     }

     /**
      * 担当者に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param employee 担当者
      * @return エラーメッセージ
      */
     private static String validateEmployee(EmployeeView employee) {
         if(employee == null || employee.equals("")){
             return MessageConst.E_NOREP.getMessage();
         }

         return "";
     }

     /**
      * 期日に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param dueDate 期日
      * @return エラーメッセージ
      */
     private static String validateDueDate(LocalDate dueDate) {
         if(dueDate == null || dueDate.equals("")) {
             return MessageConst.E_NODUEDATE.getMessage();
         }
         return "";
     }

}
