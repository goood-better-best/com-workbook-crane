package com.workbook.crane.partner.domain.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.workbook.crane.common.BaseEntity;
import com.workbook.crane.partner.application.dto.PartnerDto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Table(name = "partner")
@Entity
public class Partner extends BaseEntity<PartnerDto> {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Long id;

  @Column(name = "partner_number")
  private String partnerNumber;

  @Column(name = "company_name")
  private String companyName;

  @Column(name = "ceo_name")
  private String ceoName;

  @Column(name = "phone_number")
  private String phoneNumber;

  @Column(name = "deleted_at")
  private LocalDateTime deletedAt;

  @Builder
  public Partner(Long id, String partnerNumber, String companyName, String ceoName,
      String phoneNumber, LocalDateTime deletedAt) {
    this.id = id;
    this.partnerNumber = partnerNumber;
    this.companyName = companyName;
    this.ceoName = ceoName;
    this.phoneNumber = phoneNumber;
    this.deletedAt = deletedAt;
  }

  public static Partner create(PartnerDto dto) {
  	Partner partner = new Partner();
  	partner.partnerNumber = dto.getPartnerNumber();
  	partner.companyName = dto.getCompanyName();
  	partner.ceoName = dto.getCeoName();
  	partner.phoneNumber = dto.getPhoneNumber();
  	return partner;
	}

  public Partner updatePartner(String companyName, String ceoName, String phoneNumber) {
    this.companyName = companyName;
    this.ceoName = ceoName;
    this.phoneNumber = phoneNumber;
    return this;
  }

  @Override
  public PartnerDto toDto() {
    PartnerDto partnerDto = new PartnerDto().builder()
        .id(id)
        .partnerNumber(partnerNumber)
        .companyName(companyName)
        .ceoName(ceoName)
        .phoneNumber(phoneNumber)
        .deletedAt(deletedAt)
        .build();

    return partnerDto;
  }

  public void deletePartner() {
    this.deletedAt = LocalDateTime.now();
  }

  public static String generatePartnerNumber(String partnerNumber) {
    int number = Integer.parseInt(partnerNumber.substring(1));
    String format = number < 10 ? "%03d" : number < 100 ? "%02d" : "%01d";
    return String.format(format, number++);
  }

}
