package com.example.foodstuff;

public class setProfileData {
    static String ProfilePhoneNumber;
    static String ProfileCategory;
    static String ProfileUsername;
    static String ProfilePassword;
    static String ProfileAddress;

    public setProfileData(String profilePhoneNumber, String profileCategory, String profileUsername, String profilePassword,String profileAddress) {
        ProfilePhoneNumber = profilePhoneNumber.replace(" ","");
        ProfileCategory = profileCategory;
        ProfileUsername = profileUsername;
        ProfilePassword = profilePassword;
        ProfileAddress = profileAddress;
    }

    public static String getProfilePhoneNumber() {
        return (ProfilePhoneNumber);
    }

    public static void setProfilePhoneNumber(String profilePhoneNumber) {
        ProfilePhoneNumber = profilePhoneNumber;
    }

    public static String getProfileCategory() {
        return ProfileCategory;
    }

    public static void setProfileCategory(String profileCategory) {
        ProfileCategory = profileCategory;
    }

    public static String getProfileUsername() {
        return ProfileUsername;
    }

    public static void setProfileUsername(String profileUsername) {
        ProfileUsername = profileUsername;
    }

    public static String getProfilePassword() {
        return ProfilePassword;
    }

    public static void setProfilePassword(String profilePassword) {
        ProfilePassword = profilePassword;
    }

    public static String getProfileAddress() {
        return ProfileAddress;
    }

    public static void setProfileAddress(String profileAddress) {
        ProfileAddress = profileAddress;
    }
}
