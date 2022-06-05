package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.IdeaView;
import constants.MessageConst;
import services.IdeaService;

/**
 * アイデアインスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class IdeaValidator {
    /**
     * 要望インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param iv IdeaViewのインスタンス
     * @return エラーのリスト
     */
     public static List<String> validate(IdeaService service, IdeaView iv){
         List<String> errors = new ArrayList<String>();

         //内容のチェック
         String contentError = validateContent(iv.getContent());
         if(!contentError.equals("")){
             errors.add(contentError);
         }

         return errors;
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

}
