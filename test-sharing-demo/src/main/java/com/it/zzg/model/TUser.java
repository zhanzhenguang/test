package com.it.zzg.model;

import java.io.Serializable;

import javax.persistence.Id;

import lombok.Data;

@Data
public class TUser implements Serializable{
    private static final long serialVersionUID = -6356423424942543731L;

    @Id
    Integer id;
    String name;
    Integer userId;
    Integer age;
}
