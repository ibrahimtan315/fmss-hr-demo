package com.fmss.hr.entities;

import lombok.Data;

import javax.persistence.Table;

@Table(name="AdverCandidate")
@Data
public class AdvertCandidate {

    Candidate candidate;

    Advert Advert;
}
