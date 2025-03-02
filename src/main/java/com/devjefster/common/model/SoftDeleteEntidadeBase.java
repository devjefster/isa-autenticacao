package com.devjefster.common.model;


import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
public class SoftDeleteEntidadeBase extends EntidadeBase {

    private boolean deletado = false;

}
