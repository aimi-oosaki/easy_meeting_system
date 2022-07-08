package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Task;

/**
 * プロジェクトデータのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class TaskConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param tv TaskViewのインスタンス
     * @return Taskのインスタンス
     */
    public static Task toModel(TaskView tv) {
        return new Task(
                tv.getId(),
                TeamConverter.toModel(tv.getTeam()),
                EmployeeConverter.toModel(tv.getEmployee()),
                tv.getTitle(),
                tv.getProgress(),
                tv.getProblem(),
                tv.getSolution(),
                tv.getDue_date(),
                tv.getStatus());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param t Taskのインスタンス
     * @return TaskViewのインスタンス
     */
    public static TaskView toView(Task t) {
        if(t == null) {
            return null;
        }

        return new TaskView(
                t.getId(),
                TeamConverter.toView(t.getTeam()),
                EmployeeConverter.toView(t.getEmployee()),
                t.getTitle(),
                t.getProgress(),
                t.getProblem(),
                t.getSolution(),
                t.getDue_date(),
                t.getStatus());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<TaskView> toViewList(List<Task> list){
        List<TaskView> tvs = new ArrayList<>();
         for(Task t : list) {
             tvs.add(toView(t));
         }

         return tvs;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param t DTOモデル(コピー先)
     * @param tv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Task t, TaskView tv) {
        t.setId(tv.getId());
        t.setTeam(TeamConverter.toModel(tv.getTeam()));
        t.setEmployee(EmployeeConverter.toModel(tv.getEmployee()));
        t.setTitle(tv.getTitle());
        t.setProgress(tv.getProgress());
        t.setProblem(tv.getProblem());
        t.setSolution(tv.getSolution());
        t.setDue_date(tv.getDue_date());
        t.setStatus(tv.getStatus());
    }
}
