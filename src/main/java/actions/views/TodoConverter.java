package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Todo;

/**
 * 募集データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TodoConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param rv ReportViewのインスタンス
     * @return Reportのインスタンス
     */
    public static Todo toModel(TodoView tv) {
        return new Todo(
                tv.getId(),
                EmployeeConverter.toModel(tv.getEmployee()),
                MeetingConverter.toModel(tv.getMeeting()),
                TeamConverter.toModel(tv.getTeam()),
                tv.getWhat(),
                tv.getDeadline(),
                tv.getStatus(),
                tv.getConsequence());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param r Reportのインスタンス
     * @return ReportViewのインスタンス
     */
    public static TodoView toView(Todo t) {
        if(t == null) {
            return null;
        }

        return new TodoView(
                t.getId(),
                EmployeeConverter.toView(t.getEmployee()),
                MeetingConverter.toView(t.getMeeting()),
                TeamConverter.toView(t.getTeam()),
                t.getWhat(),
                t.getDeadline(),
                t.getStatus(),
                t.getConsequence());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TodoView> toViewList(List<Todo> list){
        List<TodoView> tvs = new ArrayList<>();
         for(Todo t : list) {
             tvs.add(toView(t));
         }

         return tvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param r DTOモデル(コピー先)
     * @param rv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Todo t, TodoView tv) {
        t.setId(tv.getId());
        t.setEmployee(EmployeeConverter.toModel(tv.getEmployee()));
        t.setMeeting(MeetingConverter.toModel(tv.getMeeting()));
        t.setTeam(TeamConverter.toModel(tv.getTeam()));
        t.setWhat(tv.getWhat());
        t.setDeadline(tv.getDeadline());
        t.setStatus(tv.getStatus());
        t.setConsequence(tv.getConsequence());
    }
}
