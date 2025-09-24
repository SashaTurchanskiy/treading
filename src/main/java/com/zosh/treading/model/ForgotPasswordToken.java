package com.zosh.treading.model;

import com.zosh.treading.domain.VerificationType;
import jakarta.persistence.*;
import lombok.Data;

import javax.swing.*;

@Entity
@Data
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    @OneToOne
    private User user;
    private String otp;
    private VerificationType verificationType;
    private String sendTo;
}
