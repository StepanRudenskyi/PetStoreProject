package org.example.petstore.service;

import lombok.Getter;
import lombok.Setter;
import org.example.petstore.dto.ReceiptDto;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Setter
@Getter
//@Component
//@Scope("request")
public class ReceiptBean {
    private ReceiptDto receipt;
}
