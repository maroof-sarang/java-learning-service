package com.maroof.selflearning.lld.builder;

public class User {

    private final String name;
    private final String email;

    private User(Builder builder) {
        this.name = builder.name;
        this.email = builder.email;
    }

    public static class Builder {

        private String name;
        private String email;

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setEmail(String email) {
            this.email = email;
            return this;
        }

        public User build() {
            return new User(this);
        }
    }
}
