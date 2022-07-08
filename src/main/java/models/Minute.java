package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
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
 * 議事録データのDTOモデル
 *
 */
@Table(name = JpaConst.TABLE_MIN)
@NamedQueries({
    @NamedQuery(
            name = JpaConst.Q_MINUTE_GET_BY_MEETING,
            query = JpaConst.Q_MINUTE_GET_BY_MEETING_DEF
            )
})

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Minute {
    /**
     * id
     */
    @Id
    @Column(name = JpaConst.MIN_COL_ID)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * どの会議の議事録か
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.MIN_COL_MET, nullable = false)
    private Meeting meeting;

    /**
     * 書記
     */
    @ManyToOne
    @JoinColumn(name = JpaConst.MIN_COL_EMP, nullable = false)
    private Employee employee;

    /**
     * 参加者
     */
    @Lob
    @Column(name = JpaConst.MIN_COL_ATTENDEES, length = 50, nullable = false)
    private String attendees;

    /**
     * 決定事項
     */
    @Lob
    @Column(name = JpaConst.MIN_COL_DECIDED, length = 250, nullable = false)
    private String decided;

    /**
     * 保留事項
     */
    @Lob
    @Column(name = JpaConst.MIN_COL_PENDING, nullable = false)
    private String pending;
}
