package com.sonderben.sdbvideo.data.model;

public class Country {
    Long id;
    String name;
    String iso2;

    public Country(Long id, String name, String iso2) {
        this.id = id;
        this.name = name;
        this.iso2 = iso2;
    }

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIso2() {
        return iso2;
    }

    public void setIso2(String iso2) {
        this.iso2 = iso2;
    }

    public class State {
        Long id;
        String name;
        String iso2;

        public State() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getIso2() {
            return iso2;
        }

        public void setIso2(String iso2) {
            this.iso2 = iso2;
        }

        public State(Long id, String name, String iso2) {
            this.id = id;
            this.name = name;
            this.iso2 = iso2;
        }
    }
    public class City{
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public City(String name) {
            this.name = name;
        }
    }
    public class Detail{
        public Detail(String phonecode, String currency, String currency_symbol, String emojiU) {
            this.phonecode = phonecode;
            this.currency = currency;
            this.currency_symbol = currency_symbol;
            this.emojiU = emojiU;
        }

        public String getPhonecode() {
            return phonecode;
        }

        public void setPhonecode(String phonecode) {
            this.phonecode = phonecode;
        }

        public String getCurrency() {
            return currency;
        }

        public void setCurrency(String currency) {
            this.currency = currency;
        }

        public String getCurrency_symbol() {
            return currency_symbol;
        }

        public void setCurrency_symbol(String currency_symbol) {
            this.currency_symbol = currency_symbol;
        }

        public String getEmojiU() {
            return emojiU;
        }

        public void setEmojiU(String emojiU) {
            this.emojiU = emojiU;
        }

        String phonecode;
        String currency;
        String currency_symbol;
        String emojiU;
    }
}
