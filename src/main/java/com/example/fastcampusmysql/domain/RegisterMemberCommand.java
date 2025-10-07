package com.example.fastcampusmysql.domain;

import java.time.LocalDate;

public record RegisterMemberCommand(String email, String nickname, LocalDate birthday) {
}