package com.example.fastcampusmysql.domain.member.entity;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
public class Member {
    private final Long id;
    private String nickname;
    private final String email;
    private final LocalDate birthday;
    private final LocalDateTime createdAt;
    private static final int NAME_MAX_LENGTH = 10;

    @Builder
    public Member(Long id, String nickname, String email, LocalDate birthday, LocalDateTime createdAt) {
        this.id = id;
        this.email = Objects.requireNonNull(email);
        this.birthday = Objects.requireNonNull(birthday);
        validateNickname(nickname);
        this.nickname = Objects.requireNonNull(nickname);
        this.createdAt = createdAt == null ? LocalDateTime.now() : createdAt;
    }

    public void changeNickname(String newNickname) {
        validateNickname(newNickname);
        this.nickname = newNickname;
    }

    private void validateNickname(String nickname) {
        if (nickname == null || nickname.length() > NAME_MAX_LENGTH) {
            throw new IllegalArgumentException("닉네임은 최대 " + NAME_MAX_LENGTH + "글자입니다.");
        }
    }
}
