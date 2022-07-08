package actions.views;

import java.util.ArrayList;
import java.util.List;

import models.Minute;

/**
 * 議事録データのDTOモデル⇔Viewモデルの変換を行うクラス
 *
 */
public class MinuteConverter {
    /**
     * ViewモデルのインスタンスからDTOモデルのインスタンスを作成する
     * @param mv MinuteViewのインスタンス
     * @return Minuteのインスタンス
     */
    public static Minute toModel(MinuteView mv) {
        return new Minute(
                mv.getId(),
                MeetingConverter.toModel(mv.getMeeting()),
                EmployeeConverter.toModel(mv.getEmployee()),
                mv.getAttendees(),
                mv.getDecided(),
                mv.getPending());
    }

    /**
     * DTOモデルのインスタンスからViewモデルのインスタンスを作成する
     * @param m Minuteのインスタンス
     * @return MinuteViewのインスタンス
     */
    public static MinuteView toView(Minute m) {
        if(m == null) {
            return null;
        }

        return new MinuteView(
                m.getId(),
                MeetingConverter.toView(m.getMeeting()),
                EmployeeConverter.toView(m.getEmployee()),
                m.getAttendees(),
                m.getDecided(),
                m.getPending());
    }

    /**
     * DTOモデルのリストからViewモデルのリストを作成する
     * @param list DTOモデルのリスト
     * @return Viewモデルのリスト
     */
    public static List<MinuteView> toViewList(List<Minute> list){
        List<MinuteView> mv = new ArrayList<>();
         for(Minute m : list) {
             mv.add(toView(m));
         }

         return mv;
    }

    /**
     * Viewモデルの全フィールドの内容をDTOモデルのフィールドにコピーする
     * @param m DTOモデル(コピー先)
     * @param mv Viewモデル(コピー元)
     */
    public static void copyViewToModel(Minute m, MinuteView mv) {
        m.setId(mv.getId());
        m.setMeeting(MeetingConverter.toModel(mv.getMeeting()));
        m.setEmployee(EmployeeConverter.toModel(mv.getEmployee()));
        m.setAttendees(mv.getAttendees());
        m.setDecided(mv.getDecided());
        m.setPending(mv.getPending());
    }
}
