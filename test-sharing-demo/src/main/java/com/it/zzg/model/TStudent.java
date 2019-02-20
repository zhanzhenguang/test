package com.it.zzg.model;


import java.io.Serializable;

import lombok.Data;
 
@Data
public class TStudent implements Serializable {
 
	private static final long serialVersionUID = 8920597824668331209L;
 
	Integer id;
	Integer studentId;
	String name;
	Integer age;
  
}

