package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Agenda;

/**
 * 議題データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class AgendaConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param av AgendaViewのインスタンス
     * @return Agendaのインスタンス
     */
    public static Agenda toModel(AgendaView av) {
        return new Agenda(
                av.getId(),
                MeetingConverter.toModel(av.getMeeting()),
                av.getTitle(),
                av.getSummary());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param a Agendaのインスタンス
     * @return AgendaViewのインスタンス
     */
    public static AgendaView toView(Agenda a) {
        if(a == null) {
            return null;
        }

        return new AgendaView(
                a.getId(),
                MeetingConverter.toView(a.getMeeting()),
                a.getTitle(),
                a.getSummary());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<AgendaView> toViewList(List<Agenda> list){
        List<AgendaView> avs = new ArrayList<>();
         for(Agenda a : list) {
             avs.add(toView(a));
         }

         return avs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param a DTOモデル(コピー先)
     * @param av Viewモデル(コピー元)
     */
    public static void copyViewToModel(Agenda a, AgendaView av) {
        a.setId(av.getId());
        a.setMeeting(MeetingConverter.toModel(av.getMeeting()));
        a.setTitle(av.getTitle());
        a.setSummary(av.getSummary());
    }
}
