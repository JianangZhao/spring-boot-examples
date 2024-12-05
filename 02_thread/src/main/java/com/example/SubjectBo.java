package com.example;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SubjectBo {
    private Integer parentLableId;
    private String subjectName;
}
