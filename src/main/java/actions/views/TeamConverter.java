package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Team;
/**
 * チームデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TeamConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Team toModel(TeamView tv) {
        return new Team(
                tv.getId(),
                tv.getCompany_id(),
                tv.getName(),
                tv.getDelete_flag());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static TeamView toView(Team t) {
        if(t == null) {
            return null;
        }

        return new TeamView(
                t.getId(),
                t.getCompany_id(),
                t.getName(),
                t.getDelete_flag());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TeamView> toViewList(List<Team> list){
        List<TeamView> evs = new ArrayList<>();
         for(Team t : list) {
             evs.add(toView(t));
         }

         return evs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Team t, TeamView tv) {
        t.setId(tv.getId());
        t.setCompany_id(tv.getCompany_id());
        t.setName(tv.getName());
        t.setDelete_flag(tv.getDelete_flag());
    }
}
