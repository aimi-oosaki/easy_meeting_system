package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Idea;

/**
 * アイデアデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class IdeaConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param iv IdeaViewのインスタンス
     * @return Ideaのインスタンス
     */
    public static Idea toModel(IdeaView iv) {
        return new Idea(
                iv.getId(),
                WantConverter.toModel(iv.getWant()),
                EmployeeConverter.toModel(iv.getEmployee()),
                iv.getContent());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param i Ideaのインスタンス
     * @return IdeaViewのインスタンス
     */
    public static IdeaView toView(Idea i) {
        if(i == null) {
            return null;
        }

        return new IdeaView(
                i.getId(),
                WantConverter.toView(i.getWant()),
                EmployeeConverter.toView(i.getEmployee()),
                i.getContent());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<IdeaView> toViewList(List<Idea> list){
        List<IdeaView> iv = new ArrayList<>();
         for(Idea i : list) {
             iv.add(toView(i));
         }

         return iv;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param i DTOモデル(コピー先)
     * @param iv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Idea i, IdeaView iv) {
        i.setId(iv.getId());
        i.setWant(WantConverter.toModel(iv.getWant()));
        i.setEmployee(EmployeeConverter.toModel(iv.getEmployee()));
        i.setContent(iv.getContent());
    }
}
