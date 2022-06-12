package models.validators;

import java.util.ArrayList;
import java.util.List;

import actions.views.AgendaView;
import actions.views.MeetingView;
import constants.MessageConst;
import services.AgendaService;

/**
 * 議題インスタンスに設定されている値のバリデーションを行うクラス
 *
 */

public class AgendaValidator {
    /**
     * 議題インスタンスの各項目についてバリデーションを行う
     * @param service 呼び出し元Serviceクラスのインスタンス
     * @param av AgendaViewのインスタンス
     * @return エラーのリスト
     */
     public static List<String> validate(AgendaService service, AgendaView av){
         List<String> errors = new ArrayList<String>();

         String meetingError = validateMeeting(av.getMeeting());
         if(!meetingError.equals("")) {
             errors.add(meetingError);
         }

         //タイトルのチェック
         String titleError = validateTitle(av.getTitle());
         if(!titleError.equals("")) {
             errors.add(titleError);
         }

         return errors;
     }

     /**
      * 会議に入力値があるかをチェックし、入力値がなければエラーメッセージを返却
      * @param meeting 会議
      * @return エラーメッセージ
      */
     private static String validateMeeting(MeetingView meeting) {

         if(meeting == null || meeting.equals("")) {
             return MessageConst.E_NOMEETING.getMessage();
         }
         return "";
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
}
