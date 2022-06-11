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
 * 募集データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_TAS)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_TASK_GET_BY_TEAM_AND_STATUS,
            query= JpaConst.Q_TASK_GET_BY_TEAM_AND_STATUS_DEF),
    @NamedQuery(
            name = JpaConst.Q_TASK_GET_BY_EMPLOYEE_AND_STATUS,
            query= JpaConst.Q_TASK_GET_BY_EMPLOYEE_AND_STATUS_DEF)
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Task {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.TAS_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どのチームのプロジェクトか
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TAS_COL_TEA, nullable = false)
    private Team team;

    /**
     * 担当者
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.TAS_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * プロジェクト名
     */
    @Column(name = JpaConst.TAS_COL_TITLE, length = 50, nullable = false)
    private String title;

    /**
     * 進捗状況
     */
    @Column(name = JpaConst.TAS_COL_PROGRESS, length = 20, nullable = false)
    private String progress;

    /**
     * 問題点
     */
    @Column(name = JpaConst.TAS_COL_PROBLEM, length = 400, nullable = true)
    private String problem;

    /**
     * 解決策
     */
    @Column(name = JpaConst.TAS_COL_SOLUTION, length = 400, nullable = true)
    private String solution;

    /**
     * 期日
     */
    @Column(name = JpaConst.TAS_COL_DUE_DATE , nullable = false)
    private LocalDate due_date;

    /**
     * 完了・未完了
     */
    @Column(name = JpaConst.TAS_COL_STATUS , nullable = false)
    private Integer status;
}
