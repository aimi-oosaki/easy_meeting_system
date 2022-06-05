package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Meeting;

/**
 * 会議データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MeetingConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Meeting toModel(MeetingView mv) {
        return new Meeting(
                mv.getId(),
                TeamConverter.toModel(mv.getTeam()),
                mv.getName(),
                mv.getDate(),
                mv.getDelete_flag());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static MeetingView toView(Meeting m) {
        if(m == null) {
            return null;
        }

        return new MeetingView(
                m.getId(),
                TeamConverter.toView(m.getTeam()),
                m.getName(),
                m.getDate(),
                m.getDelete_flag());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MeetingView> toViewList(List<Meeting> list){
        List<MeetingView> evs = new ArrayList<>();
         for(Meeting m : list) {
             evs.add(toView(m));
         }

         return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Meeting m, MeetingView mv) {
        m.setId(mv.getId());
        m.setTeam(TeamConverter.toModel(mv.getTeam()));
        m.setName(mv.getName());
        m.setDate(mv.getDate());
        m.setDelete_flag(mv.getDelete_flag());
    }
}
