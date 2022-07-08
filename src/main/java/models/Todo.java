package models;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import constants.JpaConst;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TODOデータのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TOD)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TODO_GET_TEAM,
            query= JpaConst.Q_TODO_GET_TEAM_DEF),
    @NamedQuery(
            name = JpaConst.Q_TODO_GET_BY_MEETING,
            query= JpaConst.Q_TODO_GET_BY_MEETING_DEF),
    @NamedQuery(
            name = JpaConst.Q_TODO_GET_MINE,
            query= JpaConst.Q_TODO_GET_MINE_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Todo {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TOD_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * だれが
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TOD_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * どの会議のTODOか
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TOD_COL_MET, nullable = false)
    private Meeting meeting;

    /**
     * どのチームのTODOか
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TOD_COL_TEA, nullable = false)
    private Team team;

    /**
     * なにを
     */
    @Column(name = JpaConst.TOD_COL_WHAT, length = 200, nullable = false)
    private String what;

    /**
     * いつまでに
     */
    @Column(name = JpaConst.TOD_COL_DEADLINE, nullable = false)
    private LocalDate deadline;

    /**
     * 完了・未完了
     */
    @Column(name = JpaConst.TOD_COL_STATUS, nullable = false)
    private Integer status;

    /**
     * 結果
     */
    @Column(name = JpaConst.TOD_COL_CON, length = 400)
    private String consequence;
}
