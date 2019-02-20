package com.it.zzg.model;

import lombok.Data;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
@Data
@Table(name = "sc_user")
public class User implements Serializable{
    private static final long serialVersionUID = -6356423424942543731L;

    @Id
    long id;
    String name;
    int sex;
}
