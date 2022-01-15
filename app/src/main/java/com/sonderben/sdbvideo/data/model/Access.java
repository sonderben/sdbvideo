package com.sonderben.sdbvideo.data.model;

public class Access {
     public Long getId() {
          return id;
     }

     public void setId(Long id) {
          this.id = id;
     }

     Long id;
     String name;
     String code;
     int  numOfScreen;
     float price;

     public Access(long id){
          this.id=id;
     }
     public Access(Long id,String name, String code, int numOfScreen, float price) {
          this.id=id;
          this.name = name;
          this.code = code;
          this.numOfScreen = numOfScreen;
          this.price = price;
     }

     @Override
     public String toString() {
          return "Access{" +
                  "id=" + id +
                  '}';
     }

     public String getName() {
          return name;
     }

     public void setName(String name) {
          this.name = name;
     }

     public String getCode() {
          return code;
     }

     public void setCode(String code) {
          this.code = code;
     }

     public int getNumOfScreen() {
          return numOfScreen;
     }

     public void setNumOfScreen(int numOfScreen) {
          this.numOfScreen = numOfScreen;
     }

     public float getPrice() {
          return price;
     }

     public void setPrice(float price) {
          this.price = price;
     }
}
