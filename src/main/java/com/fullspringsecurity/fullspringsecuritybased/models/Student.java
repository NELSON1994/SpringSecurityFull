package com.fullspringsecurity.fullspringsecuritybased.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//import javax.persistence.Entity;
//
//@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    private Long studentId;
    private String studentNane;
}
