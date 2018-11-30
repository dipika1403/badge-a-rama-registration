package com.galvanize.badgearamaregistration.entity;

import com.galvanize.badgearamaregistration.utility.VisitStatus;
import lombok.Builder;
import lombok.Data;

import java.sql.Date;

@Data
public class ExtendedPerson {
    private Long id;

    private Long phoneNumber;

    private String firstName;
    private String lastName;
    private String company;

    private String hostName;
    private Long hostPhoneNumber;
    private String purposeOfVisit;

    private String checkedInBy;        // populated by system
    private String checkedOutBy;       // populated by system
    private String reasonForDeletion;  // populated by guard
    private String badgeNumber;        // populated by guard

    private Date registerDate;         // populated by system
    private Date checkedInDate;        // populated by system
    private Date checkedOutDate;       // populated by system

    private Boolean active;            // populated by system/guard
    private VisitStatus status;        // populated by system

    @Builder
    public ExtendedPerson(Long id, Long phoneNumber, String firstName, String lastName, String company,
                          String hostName, Long hostPhoneNumber, String purposeOfVisit, String checkedInBy,
                          String checkedOutBy, String reasonForDeletion, String badgeNumber,
                          Date registerDate, Date checkedInDate, Date checkedOutDate, Boolean active, VisitStatus status) {
        this.id = id;
        this.phoneNumber = phoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.company = company;
        this.hostName = hostName;
        this.hostPhoneNumber = hostPhoneNumber;
        this.purposeOfVisit = purposeOfVisit;
        this.checkedInBy = checkedInBy;
        this.checkedOutBy = checkedOutBy;
        this.reasonForDeletion = reasonForDeletion;
        this.badgeNumber = badgeNumber;
        this.registerDate = registerDate;
        this.checkedInDate = checkedInDate;
        this.checkedOutDate = checkedOutDate;
        this.active = active;
        this.status = status;
    }
}
