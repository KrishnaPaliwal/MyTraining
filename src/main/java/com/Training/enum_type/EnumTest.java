package com.myTraining.enum_type;

import com.myTraining.enum_type.DayEnum;

public class EnumTest {
    DayEnum day;
    
    public EnumTest(DayEnum day) {
        this.day = day;
    }

	public EnumTest() {
		// TODO Auto-generated constructor stub
	}

	public void tellItLikeItIs() {
        switch (day) {
            case MONDAY:
                System.out.println("Mondays are bad.");
                break;
                    
            case FRIDAY:
                System.out.println("Fridays are better.");
                break;
                         
            case SATURDAY: case SUNDAY:
                System.out.println("Weekends are best.");
                break;
                        
            default:
                System.out.println("Midweek days are so-so.");
                break;
        }
    }
    
    public static void main(String[] args) {
        EnumTest firstDay = new EnumTest(DayEnum.MONDAY);
        firstDay.tellItLikeItIs();
        EnumTest thirdDay = new EnumTest(DayEnum.WEDNESDAY);
        thirdDay.tellItLikeItIs();
        EnumTest fifthDay = new EnumTest(DayEnum.FRIDAY);
        fifthDay.tellItLikeItIs();
        EnumTest sixthDay = new EnumTest(DayEnum.SATURDAY);
        sixthDay.tellItLikeItIs();
        EnumTest seventhDay = new EnumTest(DayEnum.SUNDAY);
        seventhDay.tellItLikeItIs();
    }
}

