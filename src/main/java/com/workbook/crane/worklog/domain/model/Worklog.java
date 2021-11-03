package com.workbook.crane.worklog.domain.model;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.worklog.application.Dto.WorkLocationDto;
import com.workbook.crane.worklog.application.Dto.WorklogDto;
import java.time.LocalDateTime;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@Table(name = "worklog")
@Entity
public class Worklog extends BaseEntity<WorklogDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "equipment_id")
  private Long equipmentId;

//  @ManyToOne(targetEntity = Partner.class, fetch = FetchType.LAZY)
//  @JoinColumn(name = "partner_id")
//  private Partner partner;

//  @ManyToOne(targetEntity = Operator.class, fetch = FetchType.LAZY)
//  @JoinColumn(name = "operator_id")
//  private Operator operator;

  @Embedded
  private WorkLocation workLocation;

  @Embedded
  private WorkPeriod workPeriod;

  @Column(name = "is_performed")
  private boolean isPerformed;

  @Column(name = "is_payment_collected")
  private boolean isPaymentCollected;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Override
  public WorklogDto toDto() {
    WorklogDto worklogDto = new WorklogDto().builder()
                                .id(id)
                                .equipmentId(equipmentId)
                                .workLocationDto(new WorkLocationDto(workLocation.getCity(),
                                    workLocation.getGu(), workLocation.getDong()))
                                .startDate(workPeriod.getStartDate())
                                .endDate(workPeriod.getEndDate())
                                .isPerformed(isPerformed)
                                .isPaymentCollected(isPaymentCollected)
                                .deletedAt(deletedAt)
                                .build();

//    worklogDto.setCalculatedTotal(this.calculateTotal());

    return worklogDto;
  }

  @Builder
  public Worklog (Long id, Long equipmentId,
      WorkLocation workLocation, WorkPeriod workPeriod,
      boolean isPerformed, boolean isPaymentCollected, LocalDateTime deletedAt) {
    this.id = id;
    this.equipmentId = equipmentId;
    this.workLocation = workLocation;
    this.workPeriod = workPeriod;
    this.isPerformed = isPerformed;
    this.isPaymentCollected = isPaymentCollected;
    this.deletedAt = deletedAt;
  }

//  public Money calculateTotal(){
//    return heavyEquipment.calculateTotal(workPeriod.getWorkhours());
//  }

  public Worklog markWorklogIFPerformed(boolean isPerformed) {
    this.isPerformed = isPerformed;
    return this;
  }

  public Worklog markWorklogIFPaymentCollected(boolean isPaymentCollected) {
    this.isPaymentCollected = isPaymentCollected;
    return this;
  }

  public void markWorklogAsDeleted(){
    this.deletedAt = LocalDateTime.now();
  }
}
