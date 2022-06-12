package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Want;

/**
 * 募集データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class WantConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Want toModel(WantView wv) {
        return new Want(
                wv.getId(),
                MeetingConverter.toModel(wv.getMeeting()),
                TeamConverter.toModel(wv.getTeam()),
                wv.getTitle(),
                wv.getContent(),
                wv.getDue_date());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static WantView toView(Want w) {
        if(w == null) {
            return null;
        }

        return new WantView(
                w.getId(),
                MeetingConverter.toView(w.getMeeting()),
                TeamConverter.toView(w.getTeam()),
                w.getTitle(),
                w.getContent(),
                w.getDue_date());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<WantView> toViewList(List<Want> list){
        List<WantView> evs = new ArrayList<>();
         for(Want w : list) {
             evs.add(toView(w));
         }

         return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Want w, WantView wv) {
        w.setId(wv.getId());
        w.setMeeting(MeetingConverter.toModel(wv.getMeeting()));
        w.setTeam(TeamConverter.toModel(wv.getTeam()));
        w.setTitle(wv.getTitle());
        w.setContent(wv.getContent());
        w.setDue_date(wv.getDue_date());
    }
}
