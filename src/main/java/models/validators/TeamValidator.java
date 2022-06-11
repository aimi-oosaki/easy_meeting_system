package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.TeamView;
import constants.MessageConst;
import services.TeamService;

/**
 * 要望インスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class TeamValidator {
    /**
     * 要望インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param wv WantViewのインスタンス
     * @param codeDuplicateCheckFlag 社員番号の重複チェックを実施するかどうか(実施する:true 実施しない:false)
     * @param passwordCheckFlag パスワードの入力チェックを実施するかどうか(実施する:true 実施しない:false)
     * @return エラーのリスト
     */
     public static List<String> validate(TeamService service, TeamView tv){
         List<String> errors = new ArrayList<String>();

         //会社IDのチェック

         String companyError = validateCompany(tv.getCompany_id());
         if(!companyError.equals("")) {
             errors.add(companyError);
         }

         //タイトルのチェック
         String nameError = validateName(tv.getName());
         if(!nameError.equals("")) {
             errors.add(nameError);
         }

         return errors;
     }

     /**
      * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param name チーム名
      * @return エラーメッセージ
      */
     private static String validateCompany(Integer company_id) {

         if(company_id == null || company_id.equals("")) {
             return MessageConst.E_NOCOMNAME.getMessage();
         }
         return "";
     }

     /**
      * 名前に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param name チーム名
      * @return エラーメッセージ
      */
     private static String validateName(String name) {

         if(name == null || name.equals("")) {
             return MessageConst.E_NONAME.getMessage();
         }
         return "";
     }
}
