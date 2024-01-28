package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;

public class Student {

    private String name;
    private String surname;
    private String group;

    public Student(String name, String surname, String group) {
        this.name = name;
        this.surname = surname;
        this.group = group;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String getGroup() {
        return group;
    }
    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setGroup(String group) {
        this.group = group;
    }


    public void setDates(ArrayList<LocalDate> dates) {
        this.dates = dates;
    }

    private ArrayList<LocalDate> dates = new ArrayList<>();

    public void addDate(LocalDate date){ // insert new date and sort the list
        if(dates.size() == 0) {
            dates.add(date);
            setDatesString();
        }

        for (int i=0; i<dates.size(); ++i){
            if(dates.get(i).equals(date))
                return;
        }
        dates.add(date);
        Collections.sort(dates);
        setDatesString();
    }

    public void removeDate(LocalDate date){
        for (int i=0; i<dates.size(); ++i){
            if(dates.get(i).equals(date)){
                dates.remove(i);
                setDatesString();
                return;
            }
        }
    }
    public ArrayList<LocalDate> getDates(){
        return dates;
    }

    public String getDateStr() {
        return dateStr;
    }

    private String dateStr = "No attendance";

    public void setDatesString(){
        if(dates.isEmpty()){
            dateStr = "No attendance";
            return;
        }
        dateStr = dates.toString();
    }

}
